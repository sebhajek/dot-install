package seb_dot_hajek_at_gmail_dot_com.dots.module.modules;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.command.Command;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.command.CommandExec;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.FedoraDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractSingletonModule;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.FileUtils;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public class ZSHModule extends AbstractSingletonModule {

	@Override
	public List<RepoPM> getRepos() {
		return List.of();
	}

	@Override
	public List<String> getPackages() {
		return switch (ConfigManager.cfg().getConfig().distro()) {
			case FedoraDistro distro -> List.of("zsh");
			default ->                  throw PackageNotFoundException.defaultMsg();
		};
	}

	@Override
	public void install() {
		try {
			installOMZ();
			CopyZSHConfig();
		} catch (IOException | URISyntaxException | InterruptedException e) {
			throw new RuntimeException(
			  "Failed to copy zsh configuration files", e
			);
		}
	}

	private void installOMZ() throws IOException, InterruptedException {
		Logger.logger().info("installing OMZ via git clone");

		var omzPath = FileUtils.HOME_PATH.resolve(".oh-my-zsh");

		if (Files.exists(omzPath)) {
			Logger.logger().info("OMZ already installed, skipping");
			return;
		}

		try {
			new CommandExec(Command.builder()
			                  .command("git", "clone")
			                  .arg("https://github.com/ohmyzsh/ohmyzsh.git")
			                  .arg(omzPath.toString())
			                  .build())
			  .exec();
			Logger.logger().info("OMZ cloned successfully");
		} catch (CommandExec.CommandExecutionException e) {
			Logger.logger().error("OMZ installation failed, continuing anyway");
		}
	}

	private void CopyZSHConfig() throws IOException, URISyntaxException {
		Logger.logger().info("copying zsh config");
		FileUtils.copyResourceToDirectory("zsh", FileUtils.HOME_PATH);
	}
}
