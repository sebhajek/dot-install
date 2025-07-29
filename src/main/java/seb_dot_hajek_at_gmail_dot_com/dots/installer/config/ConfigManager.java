package seb_dot_hajek_at_gmail_dot_com.dots.installer.config;

import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public class ConfigManager {

	private static final ConfigManager INSTANCE = new ConfigManager();

	public static ConfigManager getInstance() { return INSTANCE; }

	public static ConfigManager cfg() { return ConfigManager.getInstance(); }

	private Config config;

	public Config getConfig() { return config; }

	public ConfigManager setConfig(Config config) {
		Logger.logger().info(
		  String.format("config set: %s", config.toString())
		);
		this.config = config;
		return this;
	}
}
