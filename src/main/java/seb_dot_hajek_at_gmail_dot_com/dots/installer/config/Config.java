package seb_dot_hajek_at_gmail_dot_com.dots.installer.config;

import java.util.StringJoiner;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.AbstractDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public record
Config(boolean dryRun, String colorschemeName, AbstractDistro distro) {
	public static Config.Builder builder() {
		return new Config.Builder();
	}

	public static class Builder {
		private String         colorschemeName = null;
		private boolean        dryRun          = false;
		private AbstractDistro distro          = null;

		Builder() {}

		public Config.Builder dryRun(boolean dryRun) {
			this.dryRun = dryRun;
			return this;
		}

		public Config.Builder colorschemeName(String colorschemeName) {
			this.colorschemeName = colorschemeName;
			return this;
		}

		public Config.Builder distro(AbstractDistro distro) {
			Logger.logger().info(
			  String.format("detected distro: %s", distro.getFormattedName())
			);
			this.distro = distro;
			return this;
		}

		public Config build() {
			return new Config(this.dryRun, this.colorschemeName, this.distro);
		}
	}

	@Override
	public String toString() {
		return new StringJoiner(",\n\t", "Config [\n\t", "\n]")
		  .add(String.format("dry-run: %b", dryRun))
		  .add(String.format("colorscheme: %s", colorschemeName))
		  .add(String.format("distro: %s", distro.getFormattedName()))
		  .toString();
	}
}
