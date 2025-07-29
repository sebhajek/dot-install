package seb_dot_hajek_at_gmail_dot_com.dots.installer;

import java.io.IOException;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.CLI;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.DistroDetector;

public class Main {
	public static void main(String[] args)
	  throws IOException, InterruptedException {
		ConfigManager.cfg().setConfig(new CLI(args).process());
		var pm = ConfigManager.cfg().getConfig().distro().getPackageManager();

		pm.addRepo(
		  "terra-release",
		  String.format(
		    "https://repos.fyralabs.com/terra%s",
		    DistroDetector.detectDistro().getVersion()
		  ),
		  "terra"
		);
		pm.install("neovim", "luajit");
	}
}
