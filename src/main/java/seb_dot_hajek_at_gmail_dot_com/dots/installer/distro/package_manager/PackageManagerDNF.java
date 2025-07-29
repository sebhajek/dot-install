package seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager;

import java.io.IOException;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.command.Command;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.command.CommandExec;
import seb_dot_hajek_at_gmail_dot_com.dots.shared.Logger;

abstract sealed class PackageManagerDNF
  implements AbstractPackageManager permits PackageManagerFedora,
             PackageManagerEL {

	public PackageManagerDNF() {}

	@Override
	public AbstractPackageManager install(final String... packages)
	  throws IOException, InterruptedException {
		Logger.logger().info(
		  String.format("Installing: %s", String.join(", ", packages))
		);
		new CommandExec(Command.builder()
		                  .sudo()
		                  .command("dnf", "install")
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

	public AbstractPackageManager
	addRepo(final String repoName, final String repoURL, final String repoId)
	  throws IOException, InterruptedException {

		Logger.logger().info(
		  String.format("Checking if repo exists: %s", repoName)
		);

		try {
			new CommandExec(Command.builder()
			                  .command("dnf", "repolist")
			                  .flag("quiet")
			                  .arg(repoId)
			                  .build())
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
		new CommandExec(Command.builder()
		                  .sudo()
		                  .command("dnf", "install")
		                  .flag("nogpgcheck")
		                  .flagWithSpace(
		                    "repofrompath",
		                    String.format("%s,%s", repoId, repoURL)
		                  )
		                  .arg(repoName)
		                  .build())
		  .exec();
		return this;
	}

	@Override
	public AbstractPackageManager refresh()
	  throws IOException, InterruptedException {
		Logger.logger().info("Refreshing package repository metadata...");
		new CommandExec(
		  Command.builder().sudo().command("dnf", "makecache").build()
		)
		  .exec();
		return this;
	}

	@Override
	public AbstractPackageManager update()
	  throws IOException, InterruptedException {
		Logger.logger().info("Updating system packages...");
		new CommandExec(
		  Command.builder().sudo().command("dnf", "upgrade").flag("y").build()
		)
		  .exec();
		return this;
	}
}
