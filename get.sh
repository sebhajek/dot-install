#!/usr/bin/env sh
# vim: set ft=sh :
# Default values {{{
DRY_RUN=""
THEME=""
REPO_URL="https://github.com/sebhajek/dot-install.git"
INSTALL_DIR="${DOT_INSTALL_DIR:-$HOME/.dot-install}"
# }}}
# Function to show usage {{{
show_help() {
	cat <<EOF
Usage: $0 [options]
Options:
    -d, --dry-run           Run in dry mode (no changes made)
    -t, --theme <name>      Set the colorscheme/theme name
    -h, --help              Show this help message

Environment Variables:
    DOT_INSTALL_DIR         Directory to clone the repo (default: ~/.dot-install)
EOF
}
# }}}
# Parse command line arguments {{{
while [ $# -gt 0 ]; do
	case $1 in
	-d | --dry-run)
		DRY_RUN="--dry-run"
		shift
		;;
	-t | --theme)
		if [ -n "$2" ]; then
			THEME="--theme $2"
			shift 2
		else
			echo "Error: --theme requires a theme name"
			exit 1
		fi
		;;
	-h | --help)
		show_help
		exit 0
		;;
	*)
		echo "Unknown option: $1"
		show_help
		exit 1
		;;
	esac
done
# }}}
# Function to run command with dry-run support {{{
run_cmd() {
	if [ -n "$DRY_RUN" ]; then
		echo "[DRY RUN] Would run: $*"
	else
		echo "Running: $*"
		"$@"
	fi
}
# }}}
# Installing dependencies {{{
echo "Installing dependencies..."
run_cmd sudo dnf install -y git curl java-21-openjdk java-21-openjdk-devel
# }}}
# Cloning repository {{{
echo "Cloning repository to $INSTALL_DIR..."
if [ -d "$INSTALL_DIR" ]; then
	echo "Directory $INSTALL_DIR already exists. Updating..."
	if [ -z "$DRY_RUN" ]; then
		cd "$INSTALL_DIR" && git pull
	else
		echo "[DRY RUN] Would update existing repository"
	fi
else
	run_cmd git clone "$REPO_URL" "$INSTALL_DIR"
fi
if [ -z "$DRY_RUN" ]; then
	cd "$INSTALL_DIR" || {
		echo "Error: Failed to change to $INSTALL_DIR"
		exit 1
	}
fi
# }}}
# Building executables {{{
echo "Building executables..."
run_cmd ./gradlew buildExecutables
# }}}
# Running dots installer {{{
echo "Running dots installer..."
if [ -n "$DRY_RUN" ] || [ -n "$THEME" ]; then
	run_cmd ./build/libs/dots $DRY_RUN $THEME
else
	run_cmd ./build/libs/dots
fi
echo "Installation complete!"
# }}}
