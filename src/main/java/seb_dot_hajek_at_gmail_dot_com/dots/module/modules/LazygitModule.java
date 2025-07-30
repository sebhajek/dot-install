package seb_dot_hajek_at_gmail_dot_com.dots.module.modules;

import java.io.IOException;
import java.util.List;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.command.Command;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.command.CommandExec;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractSingletonModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.modules.compilers.GoModule;

public class LazygitModule extends AbstractSingletonModule {

	@Override
	public List<RepoPM> getRepos() {
		return List.of();
	}

	@Override
	public List<String> getPackages() {
		return List.of();
	}

	@Override
	public void install() {
		try {
			new CommandExec(Command.builder()
			                  .command("sh", "-c")
			                  .arg(
			                    "go "
			                    + "install "
			                    + "github.com/jesseduffield/lazygit@latest"
			                  )
			                  .build())
			  .exec();
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Failed to install lazygit", e);
		}
	}

	@Override
	public List<Class<? extends AbstractModule>> getDependencyTypes() {
		return List.of(GoModule.class, ZSHModule.class, GoModule.class);
	}
}
