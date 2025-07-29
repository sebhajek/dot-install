package seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros;

import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager;

public sealed interface AbstractDistro permits FedoraDistro, DebianDistro,
  UbuntuDistro, ELDistro {
	public abstract AbstractPackageManager getPackageManager();

	public abstract String getVersion();

	public abstract String getDistroName();

	public default String getFormattedName() {
		return String.format("%s(%s)", this.getDistroName(), this.getVersion());
	}
}
