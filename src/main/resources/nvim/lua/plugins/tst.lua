return {
	{
		"nvim-treesitter/nvim-treesitter",
		dependencies = {
			{ "nvim-treesitter/nvim-treesitter-context" },
			{ "nvim-treesitter/playground" },
		},
		build = ":TSUpdate",
		main = "nvim-treesitter.configs",
		opts = {
			ensure_installed = {
				"c",
				"lua",
				"vim",
				"html",
				"markdown",
				"bash",
				"cmake",
				"json",
				"latex",
				"luadoc",
				"rescript",
				"markdown_inline",
				"mermaid",
				"sql",
				"toml",
				"tsv",
				"csv",
				"xml",
				"css",
				"comment",
				"asm",
				"javascript",
				"python",
				"go",
				"scala",
				"rust",
				"java",
				"pascal",
				"jsdoc",
				"doxygen",
			},
			auto_install = true,
			highlight = {
				enable = true,
				additional_vim_regex_highlighting = true, -- Enable globally instead of just ruby
			},
			indent = { enable = true, disable = { "ruby" } },
			sync_install = true,
			incremental_selection = {
				enable = true,
				keymaps = {
					init_selection = "<leader>ts",
					node_incremental = "<leader>te",
					scope_incremental = "<leader>tg",
					node_decremental = "<leader>tb",
				},
			},
		},
		config = function (_, opts)
			-- Configure treesitter with the opts
			require("nvim-treesitter.configs").setup(opts)

			-- Configure treesitter-context
			require("treesitter-context").setup({
				enable = true,
				multiwindow = false,
				max_lines = 6,
				min_window_height = 24,
				line_numbers = true,
				multiline_threshold = 3,
				trim_scope = "outer",
				mode = "cursor",
				separator = nil,
				zindex = 20,
				on_attach = nil, -- (fun(buf: integer): boolean) return false to disable attaching
			})
		end,
	},
}
