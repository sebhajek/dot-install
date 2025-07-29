// PackageManagerAPT.java - Complete implementation
package seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager;

import java.io.IOException;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.command.Command;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.command.CommandExec;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

public abstract sealed class PackageManagerAPT
  implements AbstractPackageManager permits PackageManagerDebian,
             PackageManagerUbuntu {

	public PackageManagerAPT() {}

	@Override
	public AbstractPackageManager install(final String... packages)
	  throws IOException, InterruptedException {
		Logger.logger().info(
		  String.format("Installing: %s", String.join(", ", packages))
		);
		new CommandExec(Command.builder()
		                  .sudo()
		                  .command("apt-get", "install")
		                  .flag("y")
		                  .arg(packages)
		                  .build())
		  .exec();
		return this;
	}

	@Override
	public AbstractPackageManager addRepo(final RepoPM repo)
	  throws IOException, InterruptedException {
		return this.addRepo(repo.repoName(), repo.repoURL(), repo.repoId());
	}

	@Override
	public AbstractPackageManager
	addRepo(final String repoName, final String repoURL, final String repoId)
	  throws IOException, InterruptedException {

		Logger.logger().info(
		  String.format("Checking if repo exists: %s", repoName)
		);

		final String sourcesListPath =
		  "/etc/apt/sources.list.d/" + repoId + ".list";

		try {
			// Check if sources list file already exists
			new CommandExec(
			  Command.builder().command("test", "-f", sourcesListPath).build()
			)
			  .exec();

			Logger.logger().info(
			  String.format("Repo %s already exists, skipping...", repoId)
			);
			return this;
		} catch (final CommandExec.CommandExecutionException e) {
			Logger.logger().info(
			  String.format("Repo %s not found, adding...", repoId)
			);
		}

		Logger.logger().info(String.format(
		  "Adding repo: %s (%s) from %s", repoName, repoId, repoURL
		));

		// Add the repository to sources list
		new CommandExec(
		  Command.builder()
		    .sudo()
		    .command("sh", "-c")
		    .arg(String.format("echo 'deb %s' > %s", repoURL, sourcesListPath))
		    .build()
		)
		  .exec();

		return this;
	}

	@Override
	public AbstractPackageManager refresh()
	  throws IOException, InterruptedException {
		Logger.logger().info("Refreshing package repository metadata...");
		new CommandExec(
		  Command.builder().sudo().command("apt-get", "update").build()
		)
		  .exec();
		return this;
	}

	@Override
	public AbstractPackageManager update()
	  throws IOException, InterruptedException {
		Logger.logger().info("Updating system packages...");
		new CommandExec(Command.builder()
		                  .sudo()
		                  .command("apt-get", "upgrade")
		                  .flag("y")
		                  .build())
		  .exec();
		return this;
	}
}
