package seb_dot_hajek_at_gmail_dot_com.dots.module.modules.compilers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.command.Command;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.command.CommandExec;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.FedoraDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractSingletonModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.modules.CompilerModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.modules.ZSHModule;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.FileUtils;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public class JVMModule extends AbstractSingletonModule {

	static final Path PATH_TO_SDK_MAN = FileUtils.HOME_PATH.resolve(".sdkman");

	@Override
	public List<RepoPM> getRepos() {
		return List.of();
	}

	@Override
	public List<String> getPackages() {
		return switch (ConfigManager.cfg().getConfig().distro()) {
			case FedoraDistro distro ->
				List.of("java-21-openjdk", "curl", "zip", "unzip");
			default -> throw PackageNotFoundException.defaultMsg();
		};
	}

	@Override
	public void install() {
		try {
			installSDKMAN();
			installGradleAndMaven();
			CompilerModule.CopyZSHEnv(Path.of(".zshenv-jvm"));
		} catch (IOException | URISyntaxException | InterruptedException e) {
			throw new RuntimeException(
			  "Failed to install JVM tools and copy zsh environment files", e
			);
		}
	}

	@Override
	public List<Class<? extends AbstractModule>> getDependencyTypes() {
		return dependencies(ZSHModule.class);
	}

	private void installSDKMAN() throws IOException, InterruptedException {
		Logger.logger().info("installing SDKMAN");
		if (Files.exists(PATH_TO_SDK_MAN)) {
			Logger.logger().info("SDKMAN already installed, skipping");
			return;
		}

		var installCommand =
		  Command.builder()
		    .command("bash", "-c")
		    .arg(
		      "export SDKMAN_DIR=\"$HOME/.sdkman\" "
		      + "&& curl -s "
		      + "\"https://get.sdkman.io?ci=true&rcupdate=false\" "
		      + "| bash"
		    )
		    .build();

		try {
			new CommandExec(installCommand).exec();
			Logger.logger().info("SDKMAN installed successfully");
		} catch (CommandExec.CommandExecutionException e) {
			Logger.logger().error(
			  "SDKMAN installation failed: " + e.getMessage()
			);
			throw e;
		}
	}

	private void installGradleAndMaven()
	  throws IOException, InterruptedException {
		Logger.logger().info("installing Gradle and Maven via SDKMAN");

		var installGradleCommand =
		  Command.builder()
		    .command("bash", "-c")
		    .arg(
		      "export SDKMAN_DIR=\"$HOME/.sdkman\" && source "
		      + "\"$SDKMAN_DIR/bin/sdkman-init.sh\""
		      + " && echo 'Y' | sdk install gradle"
		    )
		    .build();

		try {
			new CommandExec(installGradleCommand).exec();
			Logger.logger().info("Gradle installed successfully via SDKMAN");
		} catch (CommandExec.CommandExecutionException e) {
			Logger.logger().error(
			  "Gradle installation failed: " + e.getMessage()
			);
		}

		var installMavenCommand =
		  Command.builder()
		    .command("bash", "-c")
		    .arg(
		      "export SDKMAN_DIR=\"$HOME/.sdkman\" && source "
		      + "\"$SDKMAN_DIR/bin/sdkman-init.sh\""
		      + " && echo 'Y' | sdk install maven"
		    )
		    .build();

		try {
			new CommandExec(installMavenCommand).exec();
			Logger.logger().info("Maven installed successfully via SDKMAN");
		} catch (CommandExec.CommandExecutionException e) {
			Logger.logger().error(
			  "Maven installation failed: " + e.getMessage()
			);
		}
	}
}
