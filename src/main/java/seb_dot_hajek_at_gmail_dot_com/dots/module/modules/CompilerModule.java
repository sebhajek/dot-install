package seb_dot_hajek_at_gmail_dot_com.dots.module.modules;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractSingletonModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.modules.compilers.GoModule;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.FileUtils;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public class CompilerModule extends AbstractSingletonModule {

	public static void CopyZSHEnv(Path pathToConfig)
	  throws IOException, URISyntaxException {
		FileUtils.copyResourceFile(
		  ZSHModule.ZSH_ENV_RESOURCES.resolve(pathToConfig),
		  ZSHModule.PATH_TO_ENV_FILES
		);
	}

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
		Logger.logger().info("Compilers installed");
	}

	@Override
	public List<Class<? extends AbstractModule>> getDependencyTypes() {
		return dependencies(ZSHModule.class, GoModule.class);
	}
}
