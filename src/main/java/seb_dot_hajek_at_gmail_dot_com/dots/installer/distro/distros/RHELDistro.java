package seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.PackageManagerEL;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public final class RHELDistro extends ELDistro {

	public static final String           RHEL_VERSION = getRHELVersion();
	public static final PackageManagerEL PACKAGE_MANAGER_EL =
	  new PackageManagerEL();

	private static String getRHELVersion() {
		try {
			if (Files.exists(Paths.get("/etc/redhat-release"))) {
				String content =
				  Files.readString(Paths.get("/etc/redhat-release")).trim();
				if (content.contains("release")) {
					String[] parts = content.split("release\\s+");
					if (parts.length > 1) { return parts[1].split("\\s+")[0]; }
				}
			}

			String content = Files.readString(Paths.get("/etc/os-release"));
			for (String line : content.split("\n")) {
				if (line.startsWith("VERSION_ID=")) {
					return line.substring(11).replaceAll("\"", "");
				}
			}
		} catch (IOException e) {
			Logger.logger().error(
			  "Failed to read RHEL version: " + e.getMessage()
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
		return RHEL_VERSION;
	}

	@Override
	public String getDistroName() {
		return "Red Hat Enterprise Linux";
	}
}
