package seb_dot_hajek_at_gmail_dot_com.dots.installer.distro.distros;

public abstract sealed class ELDistro
  implements AbstractDistro permits RHELDistro, AlmaDistro {}
