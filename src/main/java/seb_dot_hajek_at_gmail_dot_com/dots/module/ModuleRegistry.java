package seb_dot_hajek_at_gmail_dot_com.dots.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ModuleRegistry {
	private static ModuleRegistry instance;

	public static synchronized ModuleRegistry getInstance() {
		if (instance == null) { instance = new ModuleRegistry(); }
		return instance;
	}

	private final Map<Class<? extends AbstractModule>, AbstractModule>
	              moduleInstances;

	private ModuleRegistry() { this.moduleInstances = new HashMap<>(); }

	public synchronized void registerModule(
	  final Class<? extends AbstractModule> moduleClass,
	  final AbstractModule                  instance
	) {
		if (moduleInstances.containsKey(moduleClass)) {
			throw new IllegalStateException(
			  "Module " + moduleClass.getSimpleName() + " is already registered"
			);
		}
		moduleInstances.put(moduleClass, instance);
	}

	@SuppressWarnings("unchecked")
	public synchronized<T extends AbstractModule> T
	getModule(final Class<T> moduleClass) {
		T instance = (T) moduleInstances.get(moduleClass);
		if (instance == null) {
			try {
				instance = moduleClass.getDeclaredConstructor().newInstance();
				moduleInstances.put(moduleClass, instance);
			} catch (final Exception e) {
				throw new RuntimeException(
				  "Failed to create singleton instance of "
				    + moduleClass.getSimpleName(),
				  e
				);
			}
		}
		return instance;
	}

	public List<AbstractModule> resolveDependencies(
	  final List<Class<? extends AbstractModule>> dependencyTypes
	) {
		if (dependencyTypes == null) { return new ArrayList<>(); }

		final List<AbstractModule> dependencies = new ArrayList<>();
		for (final Class<? extends AbstractModule> depType : dependencyTypes) {
			dependencies.add(getModule(depType));
		}
		return dependencies;
	}

	public Collection<AbstractModule> getAllModules() {
		return new ArrayList<>(moduleInstances.values());
	}

	public synchronized void clear() { moduleInstances.clear(); }
}
