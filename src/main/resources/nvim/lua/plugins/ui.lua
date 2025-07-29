return {
	"NMAC427/guess-indent.nvim",
	{
		"lukas-reineke/indent-blankline.nvim",
		main = "ibl",
		opts = {},
		config = function ()
			local ibl = require("ibl")
			ibl.setup({})
			ibl.overwrite({
				debounce = 256,
				indent = {
					char = "┆",
					tab_char = "┆",
				},
				whitespace = {
					highlight = { "Whitespace", "NonText" },
				},
			})
		end,
	},
	{
		"brenoprata10/nvim-highlight-colors",
	},
	{
		"folke/which-key.nvim",
		event = "VimEnter",
		opts = {
			preset = "classic",
			delay = 100,
			icons = {
				mappings = vim.g.have_nerd_font,
				keys = vim.g.have_nerd_font and {} or {
					Up = "<Up> ",
					Down = "<Down> ",
					Left = "<Left> ",
					Right = "<Right> ",
					C = "<C-…> ",
					M = "<M-…> ",
					D = "<D-…> ",
					S = "<S-…> ",
					CR = "<CR> ",
					Esc = "<Esc> ",
					ScrollWheelDown = "<ScrollWheelDown> ",
					ScrollWheelUp = "<ScrollWheelUp> ",
					NL = "<NL> ",
					BS = "<BS> ",
					Space = "<Space> ",
					Tab = "<Tab> ",
					F1 = "<F1>",
					F2 = "<F2>",
					F3 = "<F3>",
					F4 = "<F4>",
					F5 = "<F5>",
					F6 = "<F6>",
					F7 = "<F7>",
					F8 = "<F8>",
					F9 = "<F9>",
					F10 = "<F10>",
					F11 = "<F11>",
					F12 = "<F12>",
				},
			},
			spec = {
				{ "<leader>s", group = "[S]earch" },
				{ "<leader>t", group = "[T]oggle" },
				{ "<leader>h", group = "Git [H]unk", mode = { "n", "v" } },
			},
		},
	},
	{
		"nvim-neo-tree/neo-tree.nvim",
		version = "*",
		dependencies = {
			{ "nvim-lua/plenary.nvim" },
			{ "nvim-tree/nvim-web-devicons" },
			{ "MunifTanjim/nui.nvim" },
		},
		lazy = false,
		keys = {
			{
				"\\",
				":Neotree reveal<CR>",
				desc = "NeoTree reveal",
				silent = true,
			},
		},
		opts = {
			filesystem = {
				filtered_items = {
					visible = false,
					hide_dotfiles = false,
					hide_gitignored = true,
				},
				window = {
					position = "right",
					mappings = {
						["\\"] = "close_window",
					},
				},
			},
		},
	},
}
