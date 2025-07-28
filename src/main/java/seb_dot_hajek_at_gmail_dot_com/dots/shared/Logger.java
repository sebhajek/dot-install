package seb_dot_hajek_at_gmail_dot_com.dots.shared;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Logger {
	private enum LoggerPrefix {
		RUN("RUN"),
		ERROR("ERROR"),
		INFO("INFO"),
		DRYRUN("DRY RUN");

		private static final int PAD_WIDTH =
		  Arrays.stream(values())
		    .mapToInt((prefix) -> { return prefix.text.length(); })
		    .max()
		    .orElse(0);

		private final String text;

		LoggerPrefix(String text) { this.text = text; }

		@Override
		public String toString() {
			return String.format(
			  "[%s]%s", text, " ".repeat(PAD_WIDTH - text.length())
			);
		}
	}

	private static final Logger INSTANCE =
	  new Logger().addOut(System.err).enableTimestamp(true);

	public static Logger getInstance() { return INSTANCE; }

	public static Logger logger() { return getInstance(); }

	private final List<PrintStream> outs = new CopyOnWriteArrayList<>();

	private boolean showTimestamp = false;

	public Logger enableTimestamp(boolean enable) {
		this.showTimestamp = enable;
		return this;
	}

	public Logger addOut(PrintStream stream) {
		Objects.requireNonNull(stream);
		outs.add(stream);
		return this;
	}

	public Logger addOuts(PrintStream... streams) {
		Objects.requireNonNull(streams);
		for (PrintStream stream : streams) { addOut(stream); }
		return this;
	}

	public Logger addOuts(Iterable<PrintStream> streams) {
		Objects.requireNonNull(streams);
		for (PrintStream stream : streams) { addOut(stream); }
		return this;
	}

	public Logger info(String message) {
		return log(LoggerPrefix.INFO, message);
	}

	public Logger error(String message) {
		return log(LoggerPrefix.ERROR, message);
	}

	public Logger run(String message) { return log(LoggerPrefix.RUN, message); }

	public Logger dryRun(String message) {
		return log(LoggerPrefix.DRYRUN, message);
	}

	private Logger log(LoggerPrefix prefix, String message) {
		final String timestamp = showTimestamp
		  ? String.format(
		      "[%s] ",
		      LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)
		    )
		  : "";

		final String formatted =
		  String.format("%s%s\t=>\t%s", timestamp, prefix, message);

		for (PrintStream out : outs) { out.println(formatted); }

		return this;
	}
}
