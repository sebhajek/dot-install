-- [[ GENERAL SETTINGS ]] {{{

-- [[ User Interface ]] {{{
vim.o.number = true
vim.o.relativenumber = true
vim.o.signcolumn = "yes"
vim.o.mouse = "a"
vim.o.showmode = true
vim.o.cursorline = true
vim.o.scrolloff = 8
vim.o.splitright = true
vim.o.splitbelow = true
vim.o.list = true
-- }}}

-- [[ Search ]] {{{
vim.opt.hlsearch = true
vim.o.ignorecase = true
vim.o.smartcase = true
vim.o.inccommand = "split"
-- }}}

-- [[ Indentation & Tabs ]] {{{
vim.opt.expandtab = false
vim.opt.tabstop = 4
vim.opt.softtabstop = 4
vim.opt.shiftwidth = 4
vim.opt.formatoptions:append({ c = true, r = true, o = true, q = true })
vim.o.breakindent = true
-- }}}

-- [[ Performance & Behavior ]] {{{
vim.o.updatetime = 250
vim.o.timeoutlen = 300
vim.o.confirm = false
vim.o.undofile = true
-- }}}

-- [[ Clipboard (deferred) ]] {{{
vim.schedule(function ()
	vim.o.clipboard = "unnamedplus"
end)
-- }}}

-- }}}
-- [[ KEYMAPS ]] {{{

-- [[ Leader Keys ]] {{{
vim.g.mapleader = " "
vim.g.maplocalleader = " "
-- }}}

-- [[ Folding ]] {{{
vim.opt.foldmethod = "marker"
vim.opt.foldexpr = "v:lua.vim.treesitter.foldexpr()"
vim.opt.foldcolumn = "0"
vim.opt.foldlevel = 99
vim.opt.foldlevelstart = 0
vim.opt.foldnestmax = 4
vim.keymap.set("n", "<leader>z", "za", { desc = "Toggle fold" })
-- }}}

-- [[ Navigation & Utility ]] {{{
vim.keymap.set("n", "<Esc>", "<cmd>nohlsearch<CR>")

vim.keymap.set("n", "<leader>q", vim.diagnostic.setloclist, {
	desc = "Open diagnostic [Q]uickfix list",
})

vim.keymap.set("t", "<Esc><Esc>", "<C-\\><C-n>", {
	desc = "Exit terminal mode",
})
-- }}}

-- [[ Window Management ]] {{{
vim.keymap.set("n", "<C-h>", "<C-w><C-h>", { desc = "Move to left window" })
vim.keymap.set("n", "<C-l>", "<C-w><C-l>", { desc = "Move to right window" })
vim.keymap.set("n", "<C-j>", "<C-w><C-j>", { desc = "Move to lower window" })
vim.keymap.set("n", "<C-k>", "<C-w><C-k>", { desc = "Move to upper window" })

vim.keymap.set("n", "<C-S-h>", "<C-w>H", { desc = "Move window to left" })
vim.keymap.set("n", "<C-S-l>", "<C-w>L", { desc = "Move window to right" })
vim.keymap.set("n", "<C-S-j>", "<C-w>J", { desc = "Move window down" })
vim.keymap.set("n", "<C-S-k>", "<C-w>K", { desc = "Move window up" })
-- }}}

-- }}}
-- [[ COLORSCHEME ]] {{{
vim.g.have_nerd_font = true
vim.cmd.set("notermguicolors t_Co=16")
vim.cmd.colorscheme("noctu")
-- }}}
-- [[ BASIC AUTOCOMMANDS ]] {{{
vim.api.nvim_create_autocmd("TextYankPost", {
	desc = "Highlight when yanking (copying) text",
	group = vim.api.nvim_create_augroup(
		"kickstart-highlight-yank",
		{ clear = true }
	),
	callback = function ()
		vim.hl.on_yank()
	end,
})
-- }}}
-- [[ INSTALL LAZY.NVIM ]] {{{
local lazypath = vim.fn.stdpath("data") .. "/lazy/lazy.nvim"
if not (vim.uv or vim.loop).fs_stat(lazypath) then
	local lazyrepo = "https://github.com/folke/lazy.nvim.git"
	local out = vim.fn.system({
		"git",
		"clone",
		"--filter=blob:none",
		"--branch=stable",
		lazyrepo,
		lazypath,
	})
	if vim.v.shell_error ~= 0 then
		error("Error cloning lazy.nvim:\n" .. out)
	end
end
-- }}}
-- [[ UTILITY FUNCTIONS ]] {{{
local rtp = vim.opt.rtp
rtp:prepend(lazypath)
-- }}}
-- [[ PLUGIN LOADING ]] {{{
require("lazy").setup({
	spec = {
		{ import = "plugins.ui" },
		{ import = "plugins.telescope" },
		{ import = "plugins.lsp" },
		{ import = "plugins.fmt" },
		{ import = "plugins.cmp" },
		{ import = "plugins.tst" },
		{ import = "plugins.git" },
		{ import = "plugins.jvm" },
		{ import = "plugins.ed" },
	},
	ui = {
		icons = vim.g.have_nerd_font and {} or {
			cmd = "⌘",
			config = "🛠",
			event = "📅",
			ft = "📂",
			init = "⚙",
			keys = "🗝",
			plugin = "🔌",
			runtime = "💻",
			require = "🌙",
			source = "📄",
			start = "🚀",
			task = "📌",
			lazy = "💤 ",
		},
	},
})
-- }}}
