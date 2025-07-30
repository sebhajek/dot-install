package seb_dot_hajek_at_gmail_dot_com.dots.installer;

import java.io.IOException;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.CLI;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;
import seb_dot_hajek_at_gmail_dot_com.dots.module.DependencyGraph.CyclicDependencyException;
import seb_dot_hajek_at_gmail_dot_com.dots.module.ModuleLoader;
import seb_dot_hajek_at_gmail_dot_com.dots.module.modules.GhosttyModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.modules.GitModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.modules.NvimModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.modules.ZSHModule;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public class Main {
	public static void main(String[] args)
	  throws IOException, InterruptedException, CyclicDependencyException {
		ConfigManager.cfg().setConfig(new CLI(args).process());
		Logger.logger().info(
		  ConfigManager.cfg().getConfig().getTheme().toString()
		);
		ModuleLoader loader = new ModuleLoader(
		  GhosttyModule.class,
		  NvimModule.class,
		  GitModule.class,
		  ZSHModule.class
		);
		loader.installAll();
	}
}
