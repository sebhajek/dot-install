package seb_dot_hajek_at_gmail_dot_com.dots.installer.distro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.AbstractDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.AlmaDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.DebianDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.FedoraDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.RHELDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.UbuntuDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public final class DistroDetector {

	private DistroDetector() {}

	public static AbstractDistro detectDistro() {
		try {
			if (Files.exists(Paths.get("/etc/fedora-release"))) {
				return new FedoraDistro();
			}

			if (Files.exists(Paths.get("/etc/almalinux-release"))) {
				return new AlmaDistro();
			}

			if (Files.exists(Paths.get("/etc/redhat-release"))) {
				String content =
				  Files.readString(Paths.get("/etc/redhat-release"));
				if (content.contains("Red Hat Enterprise Linux")) {
					return new RHELDistro();
				}
			}

			if (Files.exists(Paths.get("/etc/debian_version"))) {
				if (Files.exists(Paths.get("/etc/os-release"))) {
					String osRelease =
					  Files.readString(Paths.get("/etc/os-release"));
					if (osRelease.contains("ID=ubuntu")) {
						return new UbuntuDistro();
					}
				}
				return new DebianDistro();
			}

			if (Files.exists(Paths.get("/etc/os-release"))) {
				String content = Files.readString(Paths.get("/etc/os-release"));
				for (String line : content.split("\n")) {
					if (line.startsWith("ID=")) {
						String distroId =
						  line.substring(3).replaceAll("\"", "").toLowerCase();
						return switch (distroId) {
							case "fedora" ->    new FedoraDistro();
							case "debian" ->    new DebianDistro();
							case "ubuntu" ->    new UbuntuDistro();
							case "almalinux" -> new AlmaDistro();
							case "rhel" ->      new RHELDistro();
							default ->            {
								Logger.logger().error(
								  "Unknown distribution: " + distroId
								);
								yield null;
							}
						};
					}
				}
			}

		} catch (IOException e) {
			Logger.logger().error(
			  "Failed to detect distribution: " + e.getMessage()
			);
		}

		Logger.logger().error("Could not detect distribution");
		return null;
	}
}
