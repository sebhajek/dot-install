package seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.package_manager;

import java.io.IOException;

public sealed interface AbstractPackageManager permits PackageManagerDNF,
  PackageManagerAPT {

	AbstractPackageManager install(String... packages)
	  throws IOException, InterruptedException;

	AbstractPackageManager addRepo(RepoPM repo)
	  throws IOException, InterruptedException;

	AbstractPackageManager
	addRepo(String repoName, String repoURL, String repoId)
	  throws IOException, InterruptedException;

	AbstractPackageManager refresh() throws IOException, InterruptedException;

	AbstractPackageManager update() throws IOException, InterruptedException;

	public static record
	RepoPM(String repoName, String repoURL, String repoId) {}
}
