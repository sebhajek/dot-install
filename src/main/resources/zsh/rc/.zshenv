# vim: set ft=sh :

PATH=$HOME/bin:$HOME/.local/bin:/usr/local/bin:$PATH

if [ -d "$HOME/.z/env/" ]; then
	for file in "$HOME/.z/env/"*; do
		[ -f "$file" ] && . "$file"
	done
fi

export PATH
