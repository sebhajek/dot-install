return {
	{
		"stevearc/conform.nvim",
		event = { "BufWritePre" },
		cmd = { "ConformInfo" },
		keys = {
			{
				"<leader>f",
				function ()
					require("conform").format({
						async = true,
						lsp_format = "fallback",
					})
				end,
				mode = "",
				desc = "[F]ormat buffer",
			},
		},
		opts = {
			notify_on_error = false,
			format_on_save = function (bufnr)
				local disable_filetypes = {}
				if disable_filetypes[vim.bo[bufnr].filetype] then
					return nil
				else
					return {
						timeout_ms = 5000,
						lsp_format = "fallback",
					}
				end
			end,
			formatters_by_ft = {
				latex = { "tex-fmt", "latexindent", stop_after_first = false },
				tex = { "tex-fmt", "latexindent", stop_after_first = true },
				lua = { "stylua" },
				bash = { "beautysh", "shfmt" },
				sh = { "beautysh", "shfmt" },
				makefile = { "injected" },
				markdown = {
					"injected",
					"markdown-toc",
					"mdslw",
					"mdformat",
				},
				mmd = {
					"injected",
					"markdown-toc",
					"mdslw",
					"mdformat",
				},
				python = {
					"autopep8",
					"docformatter",
					"isort",
					"autoflake",
					"black",
				},
				c = {
					"file-clang-format",
					"basic-clang-format",
					"clang-format",
					stop_after_first = true,
				},
				cpp = {
					"file-clang-format",
					"basic-clang-format",
					"clang-format",
					stop_after_first = true,
				},
				html = { "prettier", stop_after_first = true },
				javascript = { "prettier" },
				typescript = { "prettier" },
				["typescript.tsx"] = { "prettier" },
				typescriptreact = { "prettier" },
				css = { "prettier", stop_after_first = true },
				json = { "prettier", stop_after_first = true },
				less = { "prettier", stop_after_first = true },
				xml = { "xmlformat" },
				haskell = { "fourmolu" },
				sql = { "custom-sql-formatter" },
				kotlin = { "ktlint" },
				java = { "google-java-format", "java-clang-format" },
				php = { "pretty-php", "php_cs_fixer" },
				twig = { "djlint", "twig_cs_fixer" },
				nix = { "nixpkgs_fmt" },
				cmake = { "gersemi" },
				rust = { "rustfmt" },
			},
			formatters = {
				["injected"] = {
					options = {
						ignore_errors = false,
						lang_to_ext = {
							bash = "sh",
							c_sharp = "cs",
							elixir = "exs",
							javascript = "js",
							julia = "jl",
							latex = "tex",
							markdown = "md",
							python = "py",
							ruby = "rb",
							rust = "rs",
							teal = "tl",
							typescript = "ts",
							html = "html",
						},
						lang_to_formatters = {},
					},
				},
				["black"] = {
					prepend_args = { "--fast" },
				},
				["xmlformat"] = {
					prepend_args = { "--indent-char=\t", "--indent=1" },
				},
				["file-clang-format"] = {
					command = "clang-format",
					args = "-style=file",
				},
				["basic-clang-format"] = {
					command = "clang-format",
					args = "-style=\"{BasedOnStyle: mozilla, IndentWidth: 4, UseTab: Always, TabWidth: 4 }\"",
				},
				["java-clang-format"] = {
					command = "clang-format",
					args = "-style=file -assume-filename=.java",
				},
				["basic-java-clang-format"] = {
					command = "clang-format",
					args = "-style=\"{BasedOnStyle: mozilla, IndentWidth: 4, UseTab: Always, TabWidth: 4 }\" -assume-filename=.java",
				},
				["clang-format"] = {
					prepend_args = { "-style=gnu" },
				},
				["prettier"] = {
					prepend_args = {
						"--use-tabs",
						"--tab-width=4",
						"--quote-props=consistent",
						"--single-attribute-per-line",
					},
					args = function (self, ctx)
						if string.match(ctx.filename, "%.html$") then
							return {
								"--stdin-filepath",
								"$FILENAME",
								"--parser",
								"html",
							}
						end
						if string.match(ctx.filename, "%.twig$") then
							return {
								"--stdin-filepath",
								"$FILENAME",
								"--parser",
								"html",
							}
						end
						return { "--stdin-filepath", "$FILENAME" }
					end,
					ft_parsers = {
						javascript = "babel",
						css = "css",
						less = "less",
						html = "html",
						json = "json",
						jsonc = "json",
						yaml = "yaml",
						markdown = "markdown",
					},
					ext_parsers = {
						twig = "html",
					},
				},
				["custom-sql-formatter"] = {
					command = "sql-formatter",
					args = {
						"",
						"-c{\"tabWidth\":8,\"useTabs\":\"true\",\"keywordCase\":\"upper\",\"dataTypeCase\":\"upper\",\"functionCase\":\"upper\",\"identifierCase\":\"preserve\" ,\"linesBetweenQueries\":2,\"expressionWidth\":48,\"newlineBeforeSemicolon\":\"false\"}",
						"-lmariadb",
					},
				},
			},
		},
	},
}
