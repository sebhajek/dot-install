package seb_dot_hajek_at_gmail_dot_com.dots.installer.config;

public record Config(boolean dryRun, String colorschemeName) {
	public static Config.Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String  colorschemeName = null;
		private boolean dryRun          = false;

		Builder() {}

		public Config.Builder dryRun(boolean dryRun) {
			this.dryRun = dryRun;
			return this;
		}

		public Config.Builder colorschemeName(String colorschemeName) {
			this.colorschemeName = colorschemeName;
			return this;
		}

		public Config build() { return new Config(dryRun, colorschemeName); }
	}
}
