package seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.PackageManagerDebian;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public final class DebianDistro implements AbstractDistro {

	public static final String DEBIAN_VERSION = getDebianVersion();
	public static final PackageManagerDebian PACKAGE_MANAGER_DEBIAN =
	  new PackageManagerDebian();

	private static String getDebianVersion() {
		try {
			if (Files.exists(Paths.get("/etc/debian_version"))) {
				return Files.readString(Paths.get("/etc/debian_version"))
				  .trim();
			}

			String content = Files.readString(Paths.get("/etc/os-release"));
			for (String line : content.split("\n")) {
				if (line.startsWith("VERSION_ID=")) {
					return line.substring(11).replaceAll("\"", "");
				}
			}
		} catch (IOException e) {
			Logger.logger().error(
			  "Failed to read Debian version: " + e.getMessage()
			);
		}
		return "Unknown";
	}

	@Override
	public AbstractPackageManager getPackageManager() {
		return PACKAGE_MANAGER_DEBIAN;
	}

	@Override
	public String getVersion() {
		return DEBIAN_VERSION;
	}

	@Override
	public String getDistroName() {
		return "Debian";
	}
}
