# vim: set ft=sh :

eval "$(ssh-agent -s)" >/dev/null 2>&1
eval "$(ssh-add ~/.ssh/git-key)" >/dev/null 2>&1
eval "$(ssh-add ~/.ssh/id_ed25519)" >/dev/null 2>&1
