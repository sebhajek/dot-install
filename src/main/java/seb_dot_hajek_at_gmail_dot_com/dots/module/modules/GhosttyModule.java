package seb_dot_hajek_at_gmail_dot_com.dots.module.modules;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros.FedoraDistro;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager.AbstractPackageManager.RepoPM;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractModule;
import seb_dot_hajek_at_gmail_dot_com.dots.module.AbstractSingletonModule;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.FileUtils;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.colors.RGB;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.colors.Theme;

public class GhosttyModule extends AbstractSingletonModule {

	public record GhosttyColorSchemeGenerator(Theme theme) {

		private String line(final String key, final RGB value) {
			return String.format("%s=%s\n", key, value.toHex("#"));
		}

		private StringBuilder appendAnsiPalette(
		  final StringBuilder sb, final Map<Short, RGB> palette
		) {
			IntStream.range(0, 16).mapToObj(i -> (short) i).forEach(i -> {
				final var col = (i < palette.size() && palette.get(i) != null)
				  ? palette.get(i)
				  : new RGB((short) 0, (short) 0, (short) 0);
				sb.append(line(String.format("palette=%d", i), col));
			});
			return sb;
		}

		public String generateColorScheme() {

			final StringBuilder sb =
			  new StringBuilder()
			    .append(String.format(
			      "window-theme=%s\n", theme.isLight() ? "light" : "dark"
			    ))
			    .append(line("background", theme.background()))
			    .append(line("foreground", theme.foreground()))
			    .append(line("cursor-color", theme.cursorBackground()))
			    .append(line("cursor-text", theme.cursorForeground()))
			    .append(
			      line("selection-background", theme.highlightBackground())
			    )
			    .append(
			      line("selection-foreground", theme.highlightForeground())
			    );
			return appendAnsiPalette(sb, theme.ansi()).toString();
		}
	}

	@Override
	public List<RepoPM> getRepos() {
		return switch (ConfigManager.cfg().getConfig().distro()) {
			case final FedoraDistro distro ->
				List.of(new RepoPM(
				  "terra-release",
				  String.format(
				    "https://repos.fyralabs.com/terra%s",
				    ConfigManager.cfg().getConfig().distro().getVersion()
				  ),
				  "terra"
				));
			default -> throw PackageNotFoundException.defaultMsg();
		};
	}

	@Override
	public List<String> getPackages() {
		return List.of("ghostty");
	}

	@Override
	public void install() {
		try {
			copyGhosttyConfig();
			saveColorscheme();
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(
			  "Failed to copy ghostty configuration files", e
			);
		}
	}

	@Override
	public List<Class<? extends AbstractModule>> getDependencyTypes() {
		return dependencies(GitModule.class);
	}

	private void copyGhosttyConfig() throws IOException, URISyntaxException {
		Logger.logger().info("copying ghostty config");
		FileUtils.copyResourceToDirectory(
		  "ghostty", FileUtils.CONFIG_PATH.resolve("ghostty")
		);
	}

	private void saveColorscheme() throws IOException {
		Logger.logger().info("generating ghostty colorscheme config");

		FileUtils.writeStringToFile(
		  FileUtils.CONFIG_PATH.resolve("ghostty")
		    .resolve("config.d")
		    .resolve("color"),
		  new GhosttyColorSchemeGenerator(
		    ConfigManager.cfg().getConfig().getTheme()
		  )
		    .generateColorScheme()
		);
	}
}
