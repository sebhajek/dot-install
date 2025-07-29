package seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.PackageManagerFedora;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public final class FedoraDistro implements AbstractDistro {

	public static final String FEDORA_VERSION = getFedoraVersion();
	public static final PackageManagerFedora PACKAGE_MANAGER_FEDORA =
	  new PackageManagerFedora();

	private static String getFedoraVersion() {
		try {
			if (Files.exists(Paths.get("/etc/fedora-release"))) {
				String content =
				  Files.readString(Paths.get("/etc/fedora-release")).trim();
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
			  "Failed to read Fedora version: " + e.getMessage()
			);
		}
		return "Unknown";
	}

	@Override
	public AbstractPackageManager getPackageManager() {
		return PACKAGE_MANAGER_FEDORA;
	}

	@Override
	public String getVersion() {
		return FEDORA_VERSION;
	}

	@Override
	public String getDistroName() {
		return "Fedora";
	}
}
