package seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.PackageManagerUbuntu;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public final class UbuntuDistro implements AbstractDistro {

	public static final String UBUNTU_VERSION = getUbuntuVersion();
	public static final PackageManagerUbuntu PACKAGE_MANAGER_UBUNTU =
	  new PackageManagerUbuntu();

	private static String getUbuntuVersion() {
		try {
			String content = Files.readString(Paths.get("/etc/os-release"));
			for (String line : content.split("\n")) {
				if (line.startsWith("VERSION_ID=")) {
					return line.substring(11).replaceAll("\"", "");
				}
			}
		} catch (IOException e) {
			Logger.logger().error(
			  "Failed to read Ubuntu version: " + e.getMessage()
			);
		}
		return "Unknown";
	}

	@Override
	public AbstractPackageManager getPackageManager() {
		return PACKAGE_MANAGER_UBUNTU;
	}

	@Override
	public String getVersion() {
		return UBUNTU_VERSION;
	}

	@Override
	public String getDistroName() {
		return "Ubuntu";
	}
}
