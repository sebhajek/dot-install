package seb_dot_hajek_at_gmail_dot_com.dots.module.modules.compilers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.FedoraDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractSingletonModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.modules.CompilerModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.modules.ZSHModule;

public class GoModule extends AbstractSingletonModule {

	@Override
	public List<RepoPM> getRepos() {
		return List.of();
	}

	@Override
	public List<String> getPackages() {
		return switch (ConfigManager.cfg().getConfig().distro()) {
			case FedoraDistro distro -> List.of("golang");
			default ->                  throw PackageNotFoundException.defaultMsg();
		};
	}

	@Override
	public void install() {
		try {
			CompilerModule.CopyZSHEnv(Path.of(".zshenv-go"));
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(
			  "Failed to copy zsh environment files for go ", e
			);
		}
	}

	@Override
	public List<Class<? extends AbstractModule>> getDependencyTypes() {
		return dependencies(ZSHModule.class);
	}
}
