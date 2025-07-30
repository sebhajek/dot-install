# vim: set ft=sh :

HISTFILE=$HOME/.zsh_history

export ZSH=$HOME/.oh-my-zsh
ZSH_THEME="steeef"
zstyle ':omz:update' mode disabled

CASE_SENSITIVE="true"
HYPHEN_INSENSITIVE="true"

DISABLE_MAGIC_FUNCTIONS="true"
COMPLETION_WAITING_DOTS="true"
DISABLE_UNTRACKED_FILES_DIRTY="true"

plugins=(git)
source $ZSH/oh-my-zsh.sh

export LANG=en_US.UTF-8

if [[ -n $SSH_CONNECTION ]]; then
	export EDITOR='vim'
else
	export EDITOR='nvim'
fi
