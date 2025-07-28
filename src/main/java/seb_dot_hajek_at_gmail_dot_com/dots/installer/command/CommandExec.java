package seb_dot_hajek_at_gmail_dot_com.dots.installer.command;

import java.io.BufferedReader;
import java.io.IOException;
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

	private void exec(boolean dryRun)
	  throws     IOException, InterruptedException, CommandExecutionException {
        if (dryRun) {
            Logger.logger().dryRun(cmd.toString());
        } else {
            Logger.logger().run(cmd.toString());
            ProcessBuilder builder  = new ProcessBuilder(cmd.cmd());
            Process        process  = builder.start();
            int            exitCode = process.waitFor();

            if (exitCode != 0) {
                StringBuilder errorOutput = new StringBuilder();
                try (
                  BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream())
                  )
                ) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        errorOutput.append(line).append(System.lineSeparator());
                    }
                }

                Logger.logger().error(errorOutput.toString());
                throw new CommandExecutionException(String.format(
                  "Command failed: %s\nExit code: %d\n", cmd, exitCode
                ));
            }
        }
	}
}
