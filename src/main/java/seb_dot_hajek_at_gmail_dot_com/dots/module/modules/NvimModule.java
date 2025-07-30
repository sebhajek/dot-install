package seb_dot_hajek_at_gmail_dot_com.dots.module.modules;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.FedoraDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractSingletonModule;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.FileUtils;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public class NvimModule extends AbstractSingletonModule {

	@Override
	public List<RepoPM> getRepos() {
		return List.of();
	}

	@Override
	public List<String> getPackages() {
		return switch (ConfigManager.cfg().getConfig().distro()) {
			case FedoraDistro distro -> List.of("neovim", "luajit");
			default ->                  throw PackageNotFoundException.defaultMsg();
		};
	}

	@Override
	public void install() {
		try {
			copyNvimConfig();
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(
			  "Failed to copy nvim configuration files", e
			);
		}
	}

	private void copyNvimConfig() throws IOException, URISyntaxException {
		Logger.logger().info("copying nvim config");
		FileUtils.copyResourceToDirectory(
		  "nvim", FileUtils.CONFIG_PATH.resolve("nvim")
		);
	}

	@Override
	public List<Class<? extends AbstractModule>> getDependencyTypes() {
		return dependencies(ZSHModule.class);
	}
}
