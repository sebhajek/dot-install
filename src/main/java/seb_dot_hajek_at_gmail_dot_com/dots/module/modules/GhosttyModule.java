package seb_dot_hajek_at_gmail_dot_com.dots.module.modules;

import java.util.List;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractSingletonModule;

public class GhosttyModule extends AbstractSingletonModule {

	@Override
	public List<RepoPM> getRepos() {
		return List.of(new RepoPM(
		  "terra-release",
		  String.format(
		    "https://repos.fyralabs.com/terra%s",
		    ConfigManager.cfg().getConfig().distro().getVersion()
		  ),
		  "terra"
		));
	}

	@Override
	public List<String> getPackages() {
		return List.of("ghostty");
	}

	@Override
	public void install() {}

	@Override
	public List<Class<? extends AbstractModule>> getDependencyTypes() {
		return dependencies(GitModule.class);
	}
}
