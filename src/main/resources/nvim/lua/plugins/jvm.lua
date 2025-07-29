return {
	{
		"mfussenegger/nvim-jdtls",
		ft = { "java" },
		config = function ()
			local on_attach = require("utils.lsp").on_attach
			local home = os.getenv("HOME")
			local workspace_path = home
				.. "/.local/share/eclipse/"
				.. vim.fn.fnamemodify(vim.fn.getcwd(), ":p:h:t")

			local jdtls_path = home .. "/.local/share/nvim/mason/packages/jdtls"
			local path_to_jar = jdtls_path
				.. "/plugins/org.eclipse.equinox.launcher_*.jar"
			local path_to_config = jdtls_path .. "/config_linux" -- or config_mac/config_win
			local lombok_path = home .. "/.local/share/lombok/lombok.jar"

			local config = {
				cmd = {
					"java",
					"-Declipse.application=org.eclipse.jdt.ls.core.id1",
					"-Dosgi.bundles.defaultStartLevel=4",
					"-Declipse.product=org.eclipse.jdt.ls.core.product",
					"-Dlog.protocol=true",
					"-Dlog.level=ALL",
					"-Xms1g",
					"--add-modules=ALL-SYSTEM",
					"--add-opens",
					"java.base/java.util=ALL-UNNAMED",
					"--add-opens",
					"java.base/java.lang=ALL-UNNAMED",
					"-javaagent:" .. lombok_path,
					"-Xbootclasspath/a:" .. lombok_path,
					"-jar",
					vim.fn.glob(path_to_jar),
					"-configuration",
					path_to_config,
					"-data",
					workspace_path,
				},
				root_dir = require("jdtls.setup").find_root({
					".git",
					"mvnw",
					"gradlew",
					"pom.xml",
					"build.gradle",
				}) or vim.fn.getcwd(),
				settings = {
					java = {},
				},
				init_options = {
					bundles = {},
				},
				capabilities = require("blink.cmp").get_lsp_capabilities(),
				on_attach = on_attach,
			}
			require("jdtls").start_or_attach(config)
		end,
	},
	{
		"scalameta/nvim-metals",
		dependencies = { { "nvim-lua/plenary.nvim" } },
		ft = { "scala", "sbt", "java" },
		opts = function ()
			local on_attach = require("utils.lsp").on_attach
			local metals = require("metals")
			local mcfg = metals.bare_config()
			local capabilities = require("blink.cmp").get_lsp_capabilities()
			mcfg.capabilities = capabilities
			mcfg.init_options = {
				statusBarProvider = "on",
			}

			mcfg.settings = {
				showImplicitArguments = true,
				enableSemanticHighlighting = true,
				superMethodLensesEnabled = true,
				showInferredType = true,
				excludedPackages = {},
				fallbackScalaVersion = "3.3.6",
			}

			mcfg.on_attach = function (client, bufnr)
				on_attach(client, bufnr)
				local map = function (m, lhs, rhs, desc)
					vim.keymap.set(m, lhs, rhs, { buffer = bufnr, desc = desc })
				end
				map("n", "<leader>mc", metals.commands, "Metals Commands")
				map("n", "<leader>mi", metals.toggle_setting, "Toggle Setting")
			end
			return mcfg
		end,
		config = function (_, mcfg)
			local group =
				vim.api.nvim_create_augroup("nvim-metals", { clear = true })

			vim.api.nvim_create_autocmd("FileType", {
				pattern = { "scala", "sbt", "java" },
				group = group,
				callback = function ()
					require("metals").initialize_or_attach(mcfg)
				end,
			})
		end,
	},
}
