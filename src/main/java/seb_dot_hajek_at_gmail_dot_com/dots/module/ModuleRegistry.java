package seb_dot_hajek_at_gmail_dot_com.dots.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ModuleRegistry {
	private static ModuleRegistry instance;
	private final Map<Class<? extends AbstractModule>, AbstractModule>
	              moduleInstances;

	private ModuleRegistry() { this.moduleInstances = new HashMap<>(); }

	public static synchronized ModuleRegistry getInstance() {
		if (instance == null) { instance = new ModuleRegistry(); }
		return instance;
	}

	public synchronized void registerModule(
	  Class<? extends AbstractModule> moduleClass,
	  AbstractModule                  instance
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
	getModule(Class<T> moduleClass) {
		T instance = (T) moduleInstances.get(moduleClass);
		if (instance == null) {
			try {
				instance = moduleClass.getDeclaredConstructor().newInstance();
				moduleInstances.put(moduleClass, instance);
			} catch (Exception e) {
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
	  List<Class<? extends AbstractModule>> dependencyTypes
	) {
		if (dependencyTypes == null) { return new ArrayList<>(); }

		List<AbstractModule> dependencies = new ArrayList<>();
		for (Class<? extends AbstractModule> depType : dependencyTypes) {
			dependencies.add(getModule(depType));
		}
		return dependencies;
	}

	public Collection<AbstractModule> getAllModules() {
		return new ArrayList<>(moduleInstances.values());
	}

	public synchronized void clear() { moduleInstances.clear(); }
}
