package seb_dot_hajek_at_gmail_dot_com.dots.shared.colors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public class ThemeLoader {

	private static final ThemeLoader INSTANCE = new ThemeLoader();

	private static final String THEMES_CSV_PATH = "/themes/themes.csv";

	public static ThemeLoader getInstance() { return INSTANCE; }

	public static ThemeLoader loader() { return getInstance(); }

	private Map<String, Theme> themesCache = null;

	private ThemeLoader() {}

	public Map<String, Theme> getThemes() {
		loadThemes();
		return new HashMap<>(themesCache);
	}

	public List<Theme> getAllThemes() {
		loadThemes();
		return new ArrayList<>(themesCache.values());
	}

	public Theme getTheme(String name) {
		if (name == null) return null;
		loadThemes();
		return themesCache.get(name.toLowerCase());
	}

	public boolean hasTheme(String name) {
		if (name == null) return false;
		loadThemes();
		return themesCache.containsKey(name.toLowerCase());
	}

	public int getThemeCount() {
		loadThemes();
		return themesCache.size();
	}

	private void loadThemes() {
		if (themesCache != null) { return; }

		themesCache = new HashMap<>();

		try (InputStream is =
		       ThemeLoader.class.getResourceAsStream(THEMES_CSV_PATH);
		     BufferedReader reader =
		       new BufferedReader(new InputStreamReader(is))) {

			if (is == null) {
				throw new RuntimeException(
				  "Could not find themes.csv at " + THEMES_CSV_PATH
				);
			}

			String  line;
			boolean isFirstLine = true;

			while ((line = reader.readLine()) != null) {
				line = line.trim();

				if (line.isEmpty() || (isFirstLine && line.startsWith("#"))
				    || (isFirstLine && line.startsWith("\"#"))) {
					isFirstLine = false;
					continue;
				}

				try {
					var theme = Theme.fromCSV(line);
					themesCache.put(theme.schemeName().toLowerCase(), theme);
				} catch (Exception e) {
					Logger.logger().error(String.format(
					  "Error parsing theme line: %s (%s)", line, e.getMessage()
					));
				}

				isFirstLine = false;
			}

		} catch (IOException e) {
			throw new RuntimeException("Error reading themes.csv", e);
		}
	}
}
