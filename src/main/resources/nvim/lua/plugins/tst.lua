return {
	{
		"nvim-treesitter/nvim-treesitter",
		dependencies = {
			{ "nvim-treesitter/nvim-treesitter-context" },
		},
		build = ":TSUpdate",
		main = "nvim-treesitter.configs", -- Sets main module to use for opts
		-- [[ Configure Treesitter ]] See `:help nvim-treesitter`
		config = function ()
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
				additional_vim_regex_highlighting = { "ruby" },
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
		-- There are additional nvim-treesitter modules that you can use to interact
		-- with nvim-treesitter.
		--    - Treesitter + textobjects: https://github.com/nvim-treesitter/nvim-treesitter-textobjects
	},
}
