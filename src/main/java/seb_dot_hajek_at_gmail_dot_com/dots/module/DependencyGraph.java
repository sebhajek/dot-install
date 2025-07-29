package seb_dot_hajek_at_gmail_dot_com.dots.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class DependencyGraph {
	private final Map<AbstractModule, Set<AbstractModule>> adjacencyList;
	private final Set<AbstractModule> allModules;

	public DependencyGraph() {
		this.adjacencyList = new HashMap<>();
		this.allModules    = new HashSet<>();
	}

	public void addModule(AbstractModule module) {
		allModules.add(module);
		adjacencyList.putIfAbsent(module, new HashSet<>());

		List<AbstractModule> deps = module.getDependencies();
		if (deps != null) {
			for (AbstractModule dep : deps) { addDependency(module, dep); }
		}
	}

	public void
	addDependency(AbstractModule dependent, AbstractModule dependency) {
		allModules.add(dependent);
		allModules.add(dependency);

		adjacencyList.putIfAbsent(dependent, new HashSet<>());
		adjacencyList.putIfAbsent(dependency, new HashSet<>());

		adjacencyList.get(dependency).add(dependent);
	}

	public List<AbstractModule> getInstallationOrder()
	  throws CyclicDependencyException {
		Map<AbstractModule, Integer> inDegree = new HashMap<>();
		for (AbstractModule module : allModules) { inDegree.put(module, 0); }

		for (AbstractModule module : adjacencyList.keySet()) {
			for (AbstractModule neighbor : adjacencyList.get(module)) {
				inDegree.put(neighbor, inDegree.get(neighbor) + 1);
			}
		}

		Queue<AbstractModule> queue = new LinkedList<>();
		for (AbstractModule module : allModules) {
			if (inDegree.get(module) == 0) { queue.offer(module); }
		}

		List<AbstractModule> result = new ArrayList<>();

		while (!queue.isEmpty()) {
			AbstractModule current = queue.poll();
			result.add(current);

			Set<AbstractModule> neighbors = adjacencyList.get(current);
			if (neighbors != null) {
				for (AbstractModule neighbor : neighbors) {
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

	public static DependencyGraph fromModules(List<AbstractModule> modules) {
		DependencyGraph graph = new DependencyGraph();

		for (AbstractModule module : modules) { graph.addModule(module); }

		for (AbstractModule module : modules) {
			List<AbstractModule> deps = module.getDependencies();
			if (deps != null) {
				for (AbstractModule dep : deps) { graph.addModule(dep); }
			}
		}

		return graph;
	}

	public Set<AbstractModule> getDependents(AbstractModule module) {
		return adjacencyList.getOrDefault(module, new HashSet<>());
	}

	public Set<AbstractModule> getDependencies(AbstractModule module) {
		Set<AbstractModule> dependencies = new HashSet<>();
		for (Map.Entry<AbstractModule, Set<AbstractModule>> entry :
		     adjacencyList.entrySet()) {
			if (entry.getValue().contains(module)) {
				dependencies.add(entry.getKey());
			}
		}
		return dependencies;
	}

	public boolean hasPath(AbstractModule source, AbstractModule target) {
		if (source.equals(target)) return true;

		Set<AbstractModule>   visited = new HashSet<>();
		Queue<AbstractModule> queue   = new LinkedList<>();
		queue.offer(source);
		visited.add(source);

		while (!queue.isEmpty()) {
			AbstractModule      current   = queue.poll();
			Set<AbstractModule> neighbors = adjacencyList.get(current);

			if (neighbors != null) {
				for (AbstractModule neighbor : neighbors) {
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
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<AbstractModule, Set<AbstractModule>> entry :
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

	public static class CyclicDependencyException extends Exception {
		public CyclicDependencyException(String message) { super(message); }
	}
}
