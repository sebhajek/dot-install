package seb_dot_hajek_at_gmail_dot_com.dots.module;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractSingletonModule implements AbstractModule {

	@SafeVarargs
	protected final List<Class<? extends AbstractModule>> dependencies(
	  final Class<? extends AbstractModule>... deps
	) {
		return Arrays.asList(deps);
	}

	protected <T extends AbstractModule> T
	getDependency(final Class<T> dependencyClass) {
		return ModuleRegistry.getInstance().getModule(dependencyClass);
	}
}
