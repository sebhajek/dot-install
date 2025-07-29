package seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.PackageManagerEL;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public final class AlmaDistro extends ELDistro {

	public static final String           ALMA_VERSION = getAlmaVersion();
	public static final PackageManagerEL PACKAGE_MANAGER_EL =
	  new PackageManagerEL();

	private static String getAlmaVersion() {
		try {
			if (Files.exists(Paths.get("/etc/almalinux-release"))) {
				String content =
				  Files.readString(Paths.get("/etc/almalinux-release")).trim();
				String[] parts = content.split(" ");
				if (parts.length >= 3) { return parts[2]; }
			}

			String content = Files.readString(Paths.get("/etc/os-release"));
			for (String line : content.split("\n")) {
				if (line.startsWith("VERSION_ID=")) {
					return line.substring(11).replaceAll("\"", "");
				}
			}
		} catch (IOException e) {
			Logger.logger().error(
			  "Failed to read AlmaLinux version: " + e.getMessage()
			);
		}
		return "Unknown";
	}

	@Override
	public AbstractPackageManager getPackageManager() {
		return PACKAGE_MANAGER_EL;
	}

	@Override
	public String getVersion() {
		return ALMA_VERSION;
	}

	@Override
	public String getDistroName() {
		return "AlmaLinux";
	}
}
