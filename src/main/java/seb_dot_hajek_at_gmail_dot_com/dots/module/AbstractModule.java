package seb_dot_hajek_at_gmail_dot_com.dots.module;

import java.util.List;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;

public interface AbstractModule {

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
