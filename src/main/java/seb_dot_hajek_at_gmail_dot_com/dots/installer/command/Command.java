package seb_dot_hajek_at_gmail_dot_com.dots.installer.command;

import java.util.ArrayList;
import java.util.List;

public final class Command {

	// BUILDER STAGES {{{

	public static final class CommandBuilderSudoStage {
		public CommandBuilderCommandStage sudo() { return this.sudo(true); }

		public CommandBuilderCommandStage sudo(final boolean isSudo) {
			return new CommandBuilderCommandStage(isSudo);
		}

		public CommandBuilderArgsStage command(final String command) {
			return this.sudo(false).command(command);
		}

		public CommandBuilderArgsStage command(final String... commands) {
			return this.sudo(false).command(commands);
		}
	}

	public static final class CommandBuilderCommandStage {
		private final boolean isSudo;

		public CommandBuilderCommandStage(boolean isSudo) {
			this.isSudo = isSudo;
		}

		public CommandBuilderArgsStage command(final String command) {
			return new CommandBuilderArgsStage(isSudo, List.of(command));
		}

		public CommandBuilderArgsStage command(final String... commands) {
			return new CommandBuilderArgsStage(isSudo, List.of(commands));
		}
	}

	public static final class CommandBuilderArgsStage {
		private final boolean isSudo;
		private final List<String> commands;
		private final List<String> args = new ArrayList<>();

		public CommandBuilderArgsStage(
		  final boolean isSudo,
		  final List<String> commands
		) {
			this.isSudo   = isSudo;
			this.commands = new ArrayList<>(commands);
		}

		public CommandBuilderArgsStage
		flag(final String flag, final String value) {
			args.add(String.format(
			  "%s%s=%s", flag.length() > 1 ? "--" : "-", flag, value
			));
			return this;
		}

		public CommandBuilderArgsStage arg(final String value) {
			args.add(value);
			return this;
		}

		public CommandBuilderArgsStage arg(final Iterable<String> values) {
			values.forEach((value) -> { args.add(value); });
			return this;
		}

		public CommandBuilderArgsStage arg(final String... values) {
			return this.arg(List.of(values));
		}

		public CommandBuilderArgsStage
		flagWithSpace(final String flag, final String value) {
			args.add(String.format(
			  "%s%s %s", flag.length() > 1 ? "--" : "-", flag, value
			));
			return this;
		}

		public CommandBuilderArgsStage flag(final String flag) {
			args.add(
			  String.format("%s%s", flag.length() > 1 ? "--" : "-", flag)
			);
			return this;
		}

		public Command build() { return new Command(isSudo, commands, args); }
	}

	// }}}

	public static CommandBuilderSudoStage builder() {
		return new CommandBuilderSudoStage();
	}

	private final boolean isSudo;

	private final List<String> commands;

	private final List<String> args;

	private Command(boolean isSudo, List<String> commands, List<String> args) {
		this.isSudo   = isSudo;
		this.commands = List.copyOf(commands);
		this.args     = List.copyOf(args);
	}

	@Override
	public String toString() {
		return String.join(" ", this.cmd());
	}

	public List<String> cmd() {
		var cmd = new ArrayList<String>();
		if (isSudo) { cmd.add("sudo"); }
		commands.stream().forEachOrdered((command) -> { cmd.add(command); });
		args.stream().forEachOrdered((arg) -> { cmd.add(arg); });
		return cmd;
	}

	public boolean isSudo() { return isSudo; }

	public List<String> getCommands() { return commands; }

	public List<String> getArgs() { return args; }
}
