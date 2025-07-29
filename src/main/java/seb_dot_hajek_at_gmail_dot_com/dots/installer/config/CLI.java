package seb_dot_hajek_at_gmail_dot_com.dots.installer.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.DistroDetector;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.colors.ThemeLoader;

public record CLI(String[] args) {

	public Config process() throws CLIException {
		final var cfgb             = Config.builder();
		final List<String> argList = Arrays.asList(this.args);
		for (int i = 0; i < argList.size(); i++) {
			final String arg = argList.get(i);

			switch (arg) {
				case "-d", "--dry-run" -> cfgb.dryRun(true);
				case "-t", "--theme" ->   {
					final String theme =
					  getNextArg(argList, ++i, "--theme requires a value");
					cfgb.colorschemeName(theme);
				}
				case "--list-themes" -> showAllThemesAndExit();
				case "-h", "--help" ->  showHelpAndExit();
				default ->                {
					if (arg.startsWith("--theme=")) {
						cfgb.colorschemeName(
						  arg.substring("--theme=".length())
						);
					} else {
						showUnrecognizedOptionAndExit(arg);
					}
				}
			}
		}
		return cfgb.distro(DistroDetector.detectDistro()).build();
	}

	private String getNextArg(
	  final List<String> args, final int index, final String errorMessage
	) throws CLIException {
		if (index < args.size()) return args.get(index);
		throw new CLIException("Error: " + errorMessage);
	}

	private void showAllThemesAndExit() {
		ThemeLoader.loader()
		  .getAllThemes()
		  .stream()
		  .map((theme) -> { return theme.schemeName(); })
		  .sorted()
		  .forEachOrdered((theme) -> { System.out.println(theme); });
		System.exit(0);
	}

	private void showHelpAndExit() {
		System.err.println(HELP);
		System.exit(0);
	}

	private void showUnrecognizedOptionAndExit(final String arg)
	  throws     CLIException {
        throw new CLIException("Unrecognized option: " + arg);
	}

	private static String[][] OPTIONS = {
	  {     "-d, --dry-run", "Run in dry mode (no changes made)"},
	  {"-t, --theme <name>",    "Set the colorscheme/theme name"},
	  {     "--list-themes",    "List all available theme names"},
	  {	    "-h, --help",            "Show this help message"}
	};

	private static int PAD_WIDTH = Arrays.stream(CLI.OPTIONS)
	                                 .mapToInt(opt -> opt[0].length())
	                                 .max()
	                                 .orElse(0);

	public static String HELP = String.join(
	  "\n",
	  new String[] {
	    "Usage: dots [options]",
	    "Options:",
	    Arrays.stream(CLI.OPTIONS)
	      .map(
	        opt
	        -> String.format(
	          "\t%s%s\t%s",
	          opt[0],
	          " ".repeat(Math.max(1, CLI.PAD_WIDTH - opt[0].length())),
	          opt[1]
	        )
	      )
	      .collect(Collectors.joining("\n"))
	  }
	);

	public static class CLIException extends RuntimeException {
		public CLIException(final String message) { super(message); }
	}
}
