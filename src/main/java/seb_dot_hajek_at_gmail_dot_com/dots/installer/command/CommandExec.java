package seb_dot_hajek_at_gmail_dot_com.dots.installer.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public record CommandExec(Command cmd) {

	public static class CommandExecutionException extends RuntimeException {
		public CommandExecutionException(final String message) {
			super(message);
		}
	}

	public void exec()
	  throws    IOException, InterruptedException, CommandExecutionException {
        exec(ConfigManager.cfg().getConfig().dryRun());
	}

	private static String readOutputStream(InputStream inputStream)
	  throws              IOException {
        StringBuilder output = new StringBuilder();
        try (
          BufferedReader reader =
            new BufferedReader(new InputStreamReader(inputStream))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }
        return output.toString();
	}

	private void exec(boolean dryRun)
	  throws     IOException, InterruptedException, CommandExecutionException {
        if (dryRun) {
            Logger.logger().dryRun(cmd.toString());
        } else {
            Logger.logger().run(cmd.toString());
            var builder  = new ProcessBuilder(cmd.cmd());
            var process  = builder.start();
            var exitCode = process.waitFor();

            var stdOutput =
              CommandExec.readOutputStream(process.getInputStream());
            if (exitCode != 0) {
                var errorOutput =
                  CommandExec.readOutputStream(process.getErrorStream());
                Logger.logger().error(errorOutput);
                throw new CommandExecutionException(String.format(
                  "Command failed: %s\nExit code: %d\n",
                  cmd.toString(),
                  exitCode
                ));
            }
        }
	}
}
