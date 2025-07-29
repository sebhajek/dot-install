return {
	{
		"hrsh7th/nvim-cmp",
		event = "InsertEnter",
		dependencies = {
			{
				"L3MON4D3/LuaSnip",
				version = "2.*",
				build = (function ()
					if
						vim.fn.has("win32") == 1
						or vim.fn.executable("make") == 0
					then
						return
					end
					return "make install_jsregexp"
				end)(),
				dependencies = {
					{
						"rafamadriz/friendly-snippets",
						config = function ()
							require("luasnip.loaders.from_vscode").lazy_load()
						end,
					},
				},
			},
			"saadparwaiz1/cmp_luasnip",
			"hrsh7th/cmp-nvim-lsp",
			"hrsh7th/cmp-path",
			"hrsh7th/cmp-buffer",
			"folke/lazydev.nvim",
		},
		config = function ()
			local cmp = require("cmp")
			local luasnip = require("luasnip")
			luasnip.config.setup({})

			cmp.setup({
				snippet = {
					expand = function (args)
						luasnip.lsp_expand(args.body)
					end,
				},
				completion = {
					completeopt = "menu,menuone,noinsert",
				},
				mapping = cmp.mapping.preset.insert({
					-- Select the [n]ext item
					["<C-n>"] = cmp.mapping.select_next_item(),
					-- Select the [p]revious item
					["<C-p>"] = cmp.mapping.select_prev_item(),

					-- Scroll the documentation window [b]ack / [f]orward
					["<C-b>"] = cmp.mapping.scroll_docs(-4),
					["<C-f>"] = cmp.mapping.scroll_docs(4),

					-- Accept ([y]es) the completion.
					["<C-y>"] = cmp.mapping.confirm({ select = true }),
					["<CR>"] = cmp.mapping.confirm({ select = true }),

					-- Manually trigger a completion from nvim-cmp.
					["<C-Space>"] = cmp.mapping.complete({}),

					-- Move through snippet placeholders
					["<C-l>"] = cmp.mapping(function ()
						if luasnip.expand_or_locally_jumpable() then
							luasnip.expand_or_jump()
						end
					end, { "i", "s" }),
					["<C-h>"] = cmp.mapping(function ()
						if luasnip.locally_jumpable(-1) then
							luasnip.jump(-1)
						end
					end, { "i", "s" }),
				}),
				sources = {
					{
						name = "lazydev",
						group_index = 0, -- set group index to 0 to skip loading LuaLS completions
					},
					{ name = "nvim_lsp" },
					{ name = "luasnip" },
					{ name = "path" },
					{ name = "buffer" },
				},
				window = {
					completion = cmp.config.window.bordered(),
					documentation = cmp.config.window.bordered(),
				},
				formatting = {
					format = function (entry, vim_item)
						-- Add source name
						vim_item.menu = ({
							nvim_lsp = "[LSP]",
							luasnip = "[LuaSnip]",
							buffer = "[Buffer]",
							path = "[Path]",
							lazydev = "[LazyDev]",
						})[entry.source.name]
						return vim_item
					end,
				},
			})
		end,
	},
	{
		"folke/todo-comments.nvim",
		event = "VimEnter",
		dependencies = { { "nvim-lua/plenary.nvim" } },
		opts = { signs = false },
	},
}
