package seb_dot_hajek_at_gmail_dot_com.dots.installer;

import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.CLI;

public class Main {
	public static void main(String[] args) {
		var cfg = new CLI(args).process();
	}
}
