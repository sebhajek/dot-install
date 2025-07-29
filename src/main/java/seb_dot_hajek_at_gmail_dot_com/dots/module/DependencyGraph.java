package seb_dot_hajek_at_gmail_dot_com.dots.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class DependencyGraph {
	public static class CyclicDependencyException extends Exception {
		public CyclicDependencyException(final String message) {
			super(message);
		}
	}

	public static DependencyGraph fromModules(
	  final List<AbstractModule> modules
	) {
		final DependencyGraph graph = new DependencyGraph();

		for (final AbstractModule module : modules) { graph.addModule(module); }

		for (final AbstractModule module : modules) {
			final List<AbstractModule> deps = module.getDependencies();
			if (deps != null) {
				for (final AbstractModule dep : deps) { graph.addModule(dep); }
			}
		}

		return graph;
	}

	private final Map<AbstractModule, Set<AbstractModule>> adjacencyList;

	private final Set<AbstractModule> allModules;

	public DependencyGraph() {
		this.adjacencyList = new HashMap<>();
		this.allModules    = new HashSet<>();
	}

	public void addModule(final AbstractModule module) {
		allModules.add(module);
		adjacencyList.putIfAbsent(module, new HashSet<>());

		final List<AbstractModule> deps = module.getDependencies();
		if (deps != null) {
			for (final AbstractModule dep : deps) {
				addDependency(module, dep);
			}
		}
	}

	public void addDependency(
	  final AbstractModule dependent,
	  final AbstractModule dependency
	) {
		allModules.add(dependent);
		allModules.add(dependency);

		adjacencyList.putIfAbsent(dependent, new HashSet<>());
		adjacencyList.putIfAbsent(dependency, new HashSet<>());

		adjacencyList.get(dependency).add(dependent);
	}

	public List<AbstractModule> getInstallationOrder()
	  throws CyclicDependencyException {
		final Map<AbstractModule, Integer> inDegree = new HashMap<>();
		for (final AbstractModule module : allModules) {
			inDegree.put(module, 0);
		}

		for (final AbstractModule module : adjacencyList.keySet()) {
			for (final AbstractModule neighbor : adjacencyList.get(module)) {
				inDegree.put(neighbor, inDegree.get(neighbor) + 1);
			}
		}

		final Queue<AbstractModule> queue = new LinkedList<>();
		for (final AbstractModule module : allModules) {
			if (inDegree.get(module) == 0) { queue.offer(module); }
		}

		final List<AbstractModule> result = new ArrayList<>();

		while (!queue.isEmpty()) {
			final AbstractModule current = queue.poll();
			result.add(current);

			final Set<AbstractModule> neighbors = adjacencyList.get(current);
			if (neighbors != null) {
				for (final AbstractModule neighbor : neighbors) {
					inDegree.put(neighbor, inDegree.get(neighbor) - 1);
					if (inDegree.get(neighbor) == 0) { queue.offer(neighbor); }
				}
			}
		}

		if (result.size() != allModules.size()) {
			throw new CyclicDependencyException(
			  "Cyclic dependency detected in module graph"
			);
		}

		return result;
	}

	public Set<AbstractModule> getDependents(final AbstractModule module) {
		return adjacencyList.getOrDefault(module, new HashSet<>());
	}

	public Set<AbstractModule> getDependencies(final AbstractModule module) {
		final Set<AbstractModule> dependencies = new HashSet<>();
		for (final Map.Entry<AbstractModule, Set<AbstractModule>> entry :
		     adjacencyList.entrySet()) {
			if (entry.getValue().contains(module)) {
				dependencies.add(entry.getKey());
			}
		}
		return dependencies;
	}

	public boolean
	hasPath(final AbstractModule source, final AbstractModule target) {
		if (source.equals(target)) return true;

		final Set<AbstractModule> visited = new HashSet<>();
		final Queue<AbstractModule> queue = new LinkedList<>();
		queue.offer(source);
		visited.add(source);

		while (!queue.isEmpty()) {
			final AbstractModule current        = queue.poll();
			final Set<AbstractModule> neighbors = adjacencyList.get(current);

			if (neighbors != null) {
				for (final AbstractModule neighbor : neighbors) {
					if (neighbor.equals(target)) { return true; }
					if (!visited.contains(neighbor)) {
						visited.add(neighbor);
						queue.offer(neighbor);
					}
				}
			}
		}

		return false;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final Map.Entry<AbstractModule, Set<AbstractModule>> entry :
		     adjacencyList.entrySet()) {
			sb.append(entry.getKey().getClass().getSimpleName())
			  .append(" -> ")
			  .append(entry.getValue()
			            .stream()
			            .map(m -> m.getClass().getSimpleName())
			            .reduce((a, b) -> a + ", " + b)
			            .orElse("none"))
			  .append("; ");
		}
		return sb.toString();
	}
}
