package seb_dot_hajek_at_gmail_dot_com.dots.module.modules;

import java.util.List;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractSingletonModule;

public class GitModule extends AbstractSingletonModule {

	@Override
	public List<RepoPM> getRepos() {
		return List.of();
	}

	@Override
	public List<String> getPackages() {
		return List.of("git");
	}

	@Override
	public void install() {}

	@Override
	public List<Class<? extends AbstractModule>> getDependencyTypes() {
		return List.of();
	}
}
