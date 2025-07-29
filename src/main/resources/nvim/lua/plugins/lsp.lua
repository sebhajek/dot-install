return {
	{
		"folke/lazydev.nvim",
		ft = "lua",
		opts = {
			library = {
				{ path = "${3rd}/luv/library", words = { "vim%.uv" } },
			},
		},
	},
	{
		"neovim/nvim-lspconfig",
		dependencies = {
			{ "mason-org/mason.nvim", opts = {} },
			{ "mason-org/mason-lspconfig.nvim" },
			{
				"WhoIsSethDaniel/mason-tool-installer.nvim",
			},
			{ "j-hui/fidget.nvim", opts = {} },
			{
				"saghen/blink.cmp",
			},
		},
		config = function ()
			local on_attach = require("utils.lsp").on_attach

			vim.api.nvim_create_autocmd("LspAttach", {
				group = vim.api.nvim_create_augroup(
					"kickstart-lsp-attach",
					{ clear = true }
				),
				callback = function (event)
					local client =
						vim.lsp.get_client_by_id(event.data.client_id)
					on_attach(client, event.buf)
				end,
			})

			vim.diagnostic.config({
				severity_sort = true,
				float = { border = "rounded", source = "if_many" },
				underline = { severity = vim.diagnostic.severity.ERROR },
				signs = vim.g.have_nerd_font and {
					text = {
						[vim.diagnostic.severity.ERROR] = "󰅚 ",
						[vim.diagnostic.severity.WARN] = "󰀪 ",
						[vim.diagnostic.severity.INFO] = "󰋽 ",
						[vim.diagnostic.severity.HINT] = "󰌶 ",
					},
				} or {},
				virtual_text = {
					source = "if_many",
					spacing = 2,
					format = function (diagnostic)
						local diagnostic_message = {
							[vim.diagnostic.severity.ERROR] = diagnostic.message,
							[vim.diagnostic.severity.WARN] = diagnostic.message,
							[vim.diagnostic.severity.INFO] = diagnostic.message,
							[vim.diagnostic.severity.HINT] = diagnostic.message,
						}
						return diagnostic_message[diagnostic.severity]
					end,
				},
			})

			local capabilities = require("blink.cmp").get_lsp_capabilities()

			local servers = {
				cssls = {},
				ts_ls = {
					filetypes = {
						"javascript",
						"typescript",
						"javascriptreact",
						"typescriptreact",
					},
				},
				lua_ls = {},
				cmake = {},
				gopls = {},
				bashls = {
					filetypes = { "sh", "bash" },
					settings = {
						bashIde = {
							globPattern = "*@(.sh|.inc|.bash|.command|.fish)",
						},
					},
				},
				yamlls = {},
				rust_analyzer = {},
				clangd = {
					filetypes = {
						"c",
						"cpp",
						"objc",
						"objcpp",
						"cuda",
						"proto",
						"hpp",
						"h",
						"inc",
						"inl",
					},
					cmd = {
						"clangd",
						"--background-index",
						"--clang-tidy",
						"--header-insertion=iwyu",
						"--completion-style=detailed",
						"--function-arg-placeholders",
						"--fallback-style=llvm",
						"--offset-encoding=utf-8",
					},
					init_options = {
						usePlaceholders = true,
						completeUnimported = true,
						clangdFileStatus = true,
					},
					capabilities = vim.tbl_deep_extend("force", capabilities, {
						offsetEncoding = { "utf-8" },
					}),
					root_dir = function (fname)
						local lspconfig = require("lspconfig")
						return lspconfig.util.root_pattern(
							".clangd",
							".clang-tidy",
							".clang-format",
							"compile_commands.json",
							"compile_flags.txt",
							"configure.ac",
							"Makefile"
						)(fname) or lspconfig.util.find_git_ancestor(
							fname
						) or vim.fn.getcwd()
					end,
					on_attach = function (client, bufnr)
						on_attach(client, bufnr)
						vim.keymap.set(
							"n",
							"<leader>ch",
							"<cmd>LspClangdSwitchSourceHeader<CR>",
							{
								buffer = bufnr,
								desc = "LSP: Switch between source/header",
							}
						)
					end,
				},
				rescriptls = {},
				lemminx = {},
				html = {},
				vimls = {},
				typos_lsp = {},
				texlab = {},
				autotools_ls = {
					root_dir = function (fname)
						local lspconfig = require("lspconfig")
						return lspconfig.util.root_pattern(
							"configure.ac",
							"Makefile",
							"Makefile.am",
							"*.mk",
							"makefile"
						)(fname) or lspconfig.util.find_git_ancestor(
							fname
						) or vim.fn.getcwd()
					end,
				},
				basedpyright = {
					settings = {
						basedpyright = {
							disableOrganizeImports = true,
							typeCheckingMode = "standard",
							analysis = {
								typeCheckingMode = "standard",
								useLibraryCodeForTypes = false,
							},
						},
					},
				},
				jedi_language_server = {},
				harper_ls = {
					settings = {
						["harper-ls"] = {
							linters = {
								long_sentences = false,
								repeated_words = false,
							},
							codeActions = {
								forceStable = true,
							},
						},
					},
					filetypes = {
						"bib",
						"gitcommit",
						"markdown",
						"org",
						"plaintex",
						"rst",
						"rnoweb",
						"tex",
						"pandoc",
						"quarto",
						"rmd",
						"context",
						"html",
						"xhtml",
						"mail",
						"text",
						"latex",
						"texinfo",
					},
				},
			}

			local ensure_installed = vim.tbl_keys(servers or {})
			vim.list_extend(ensure_installed, {
				"checkstyle",
				"stylua",
				"shfmt",
				"beautysh",
				"markdown-toc",
				"mdslw",
				"mdformat",
				"autopep8",
				"autoflake",
				"isort",
				"docformatter",
				"black",
				"clang-format",
				"gersemi",
				"prettier",
				"latexindent",
				"tex-fmt",
				"xmlformatter",
				"sql-formatter",
			})
			require("mason-tool-installer").setup({
				ensure_installed = ensure_installed,
			})

			require("mason-lspconfig").setup({
				ensure_installed = {},
				automatic_installation = false,
				handlers = {
					function (server_name)
						local server = servers[server_name] or {}
						server.capabilities = vim.tbl_deep_extend(
							"force",
							{},
							capabilities,
							server.capabilities or {}
						)
						require("lspconfig")[server_name].setup(server)
					end,
				},
			})
		end,
	},
}
