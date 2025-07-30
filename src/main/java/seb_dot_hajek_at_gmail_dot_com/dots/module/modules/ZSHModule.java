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
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractModule;
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
			changeDefaultShell();
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
		var command = Command.builder()
		                .command("git", "clone")
		                .arg("https://github.com/ohmyzsh/ohmyzsh.git")
		                .arg(omzPath.toString())
		                .build();
		try {
			new CommandExec(command).exec();
			Logger.logger().info("OMZ cloned successfully");
		} catch (CommandExec.CommandExecutionException e) {
			Logger.logger().error("OMZ installation failed, continuing anyway");
		}
	}

	@Override
	public List<Class<? extends AbstractModule>> getDependencyTypes() {
		return dependencies(GitModule.class);
	}

	private void CopyZSHConfig() throws IOException, URISyntaxException {
		Logger.logger().info("copying zsh config");
		FileUtils.copyResourceToDirectory("zsh", FileUtils.HOME_PATH);
	}

	private void changeDefaultShell() throws IOException, InterruptedException {
		Logger.logger().info("changing default shell to zsh");
		String zshPath = getZshPath();
		if (zshPath == null) {
			Logger.logger().error(
			  "Could not find zsh path, skipping shell change"
			);
			return;
		}
		Logger.logger().info("Found zsh at: " + zshPath);
		if (!isShellInEtcShells(zshPath)) {
			addShellToEtcShells(zshPath);
		} else {
			Logger.logger().info("zsh already in /etc/shells");
		}
		changeUserShell(zshPath);
	}

	private String getZshPath() throws IOException, InterruptedException {
		var whichZshCommand =
		  Command.builder().command("command", "-v", "zsh").build();

		try {
			var process  = new ProcessBuilder(whichZshCommand.cmd()).start();
			var exitCode = process.waitFor();

			if (exitCode != 0) { return null; }

			return CommandExec.readOutputStream(process.getInputStream())
			  .trim();
		} catch (Exception e) {
			Logger.logger().error("Failed to locate zsh: " + e.getMessage());
			return null;
		}
	}

	private boolean isShellInEtcShells(String shellPath)
	  throws IOException, InterruptedException {
		var checkCommand = Command.builder()
		                     .command("grep", "-qxF", shellPath, "/etc/shells")
		                     .build();
		try {
			new CommandExec(checkCommand).exec();
			return true;
		} catch (CommandExec.CommandExecutionException e) { return false; }
	}

	private void addShellToEtcShells(String shellPath)
	  throws IOException, InterruptedException {
		Logger.logger().info("Adding zsh to /etc/shells");
		var addCommand =
		  Command.builder()
		    .sudo()
		    .command("sh", "-c")
		    .arg(String.format("echo \"%s\" >> /etc/shells", shellPath))
		    .build();

		try {
			new CommandExec(addCommand).exec();
			Logger.logger().info("Successfully added zsh to /etc/shells");
		} catch (CommandExec.CommandExecutionException e) {
			Logger.logger().error(
			  "Failed to add zsh to /etc/shells: " + e.getMessage()
			);
			throw e;
		}
	}

	private void changeUserShell(String shellPath)
	  throws IOException, InterruptedException {
		Logger.logger().info("Changing user shell to zsh");
		var changeShellCommand = Command.builder()
		                           .sudo()
		                           .command("chsh")
		                           .flagWithSpace("s", shellPath)
		                           .arg(System.getProperty("user.name"))
		                           .build();
		try {
			new CommandExec(changeShellCommand).exec();
			Logger.logger().info("Successfully changed default shell to zsh");
		} catch (CommandExec.CommandExecutionException e) {
			Logger.logger().error(
			  "Failed to change default shell: " + e.getMessage()
			);
		}
	}
}
