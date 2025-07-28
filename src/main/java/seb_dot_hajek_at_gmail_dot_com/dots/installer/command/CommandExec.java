package seb_dot_hajek_at_gmail_dot_com.dots.installer.command;

import java.io.IOException;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public record CommandExec(Command cmd) {

	public void exec() throws IOException, InterruptedException {
		exec(ConfigManager.cfg().getConfig().dryRun());
	}

	public void exec(boolean dryRun) throws IOException, InterruptedException {
		if (dryRun) {
			Logger.logger().dryRun(cmd.toString());
		} else {
			Logger.logger().run(cmd.toString());
			ProcessBuilder builder = new ProcessBuilder(cmd.cmd());
			Process        process = builder.start();

			int exitCode = process.waitFor();
			System.out.println("Exit code: " + exitCode);
		}
	}
}
