package seb_dot_hajek_at_gmail_dot_com.dots.module;

import java.util.List;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;

public interface AbstractModule {

	public static class PackageNotFoundException
	  extends UnsupportedOperationException {

		public static PackageNotFoundException defaultMsg() {
			return new PackageNotFoundException("package not found");
		}

		public PackageNotFoundException(final String message) {
			super(message);
		}
	}

	public List<RepoPM> getRepos();

	public List<String> getPackages();

	public void install();

	public List<Class<? extends AbstractModule>> getDependencyTypes();

	public default List<AbstractModule> getDependencies() {
		return ModuleRegistry.getInstance().resolveDependencies(
		  getDependencyTypes()
		);
	}
}
