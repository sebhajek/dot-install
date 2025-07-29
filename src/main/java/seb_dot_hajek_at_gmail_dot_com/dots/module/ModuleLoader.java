package seb_dot_hajek_at_gmail_dot_com.dots.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;
import seb_dot_hajek_at_gmail_dot_com.dots.module.DependencyGraph.CyclicDependencyException;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public class ModuleLoader {
	private final ModuleRegistry  registry;
	private final DependencyGraph dependencyGraph;

	@SafeVarargs
	public ModuleLoader(Class<? extends AbstractModule>... moduleClasses) {
		this.registry = ModuleRegistry.getInstance();

		List<AbstractModule> modules = new ArrayList<>();
		for (Class<? extends AbstractModule> moduleClass : moduleClasses) {
			AbstractModule module = registry.getModule(moduleClass);
			modules.add(module);
		}

		this.dependencyGraph = DependencyGraph.fromModules(modules);
	}

	public ModuleLoader(List<Class<? extends AbstractModule>> moduleClasses) {
		this.registry = ModuleRegistry.getInstance();

		List<AbstractModule> modules = new ArrayList<>();
		for (Class<? extends AbstractModule> moduleClass : moduleClasses) {
			AbstractModule module = registry.getModule(moduleClass);
			modules.add(module);
		}

		this.dependencyGraph = DependencyGraph.fromModules(modules);
	}

	public ModuleLoader(AbstractModule... modules) {
		this.registry = ModuleRegistry.getInstance();

		for (AbstractModule module : modules) {
			registry.registerModule(module.getClass(), module);
		}

		this.dependencyGraph = DependencyGraph.fromModules(List.of(modules));
	}

	public List<AbstractModule> getInstallationOrder()
	  throws DependencyGraph.CyclicDependencyException {
		return dependencyGraph.getInstallationOrder();
	}

	private List<RepoPM> getReposInOrder() throws CyclicDependencyException {
		return getInstallationOrder()
		  .stream()
		  .peek((module) -> {
			  Logger.logger().info(String.format(
			    "Finding repos for %s ...", module.getClass().getSimpleName()
			  ));
		  })
		  .flatMap((module) -> { return module.getRepos().stream(); })
		  .toList();
	}

	private List<String> getPackagesInOrder() throws CyclicDependencyException {
		return getInstallationOrder()
		  .stream()
		  .peek((module) -> {
			  Logger.logger().info(String.format(
			    "Finding packages for %s ...", module.getClass().getSimpleName()
			  ));
		  })
		  .flatMap((module) -> { return module.getPackages().stream(); })
		  .toList();
	}

	public void installAll() throws DependencyGraph.CyclicDependencyException {
		Logger.logger().info(String.format(
		  "dependencies: %s", this.getDependencyGraph().toString()
		));
		final var pm =
		  ConfigManager.cfg().getConfig().distro().getPackageManager();
		getReposInOrder().forEach((repo) -> {
			try {
				Logger.logger().info(
				  String.format("Installing repo %s ...", repo)
				);
				pm.addRepo(repo);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) { e.printStackTrace(); }
		});
		try {
			pm.install(getPackagesInOrder().toArray(String[] ::new));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) { e.printStackTrace(); }
	}

	public DependencyGraph getDependencyGraph() { return dependencyGraph; }

	public Collection<AbstractModule> getModules() {
		return registry.getAllModules();
	}

	public <T extends AbstractModule> T getModule(Class<T> moduleClass) {
		return registry.getModule(moduleClass);
	}
}
