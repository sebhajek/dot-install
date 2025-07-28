package seb_dot_hajek_at_gmail_dot_com.dots.installer.command.package_manager;

import java.io.IOException;

public sealed interface AbstractPackageManager permits PackageManagerDNF {

	AbstractPackageManager install(String... packages)
	  throws IOException, InterruptedException;

	AbstractPackageManager addRepo(String repoName, String repoURL)
	  throws IOException, InterruptedException;

	AbstractPackageManager refresh() throws IOException, InterruptedException;

	AbstractPackageManager update() throws IOException, InterruptedException;
}
