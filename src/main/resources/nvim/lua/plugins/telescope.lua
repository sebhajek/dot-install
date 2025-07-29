return {
	{
		"nvim-telescope/telescope.nvim",
		event = "VimEnter",
		dependencies = {
			{ "nvim-lua/plenary.nvim" },
			{
				"nvim-telescope/telescope-fzf-native.nvim",
				build = "make",
				cond = function ()
					return vim.fn.executable("make") == 1
				end,
			},
			{ "nvim-telescope/telescope-ui-select.nvim" },
			{ "nvim-tree/nvim-web-devicons", enabled = vim.g.have_nerd_font },
		},
		config = function ()
			require("telescope").setup({
				extensions = {
					["ui-select"] = {
						require("telescope.themes").get_dropdown(),
					},
				},
			})

			pcall(require("telescope").load_extension, "fzf")
			pcall(require("telescope").load_extension, "ui-select")

			local builtin = require("telescope.builtin")

			local search_current_buff = function ()
				builtin.current_buffer_fuzzy_find(
					require("telescope.themes").get_dropdown({
						winblend = 10,
						previewer = false,
					})
				)
			end

			local life_grep_open = function ()
				builtin.live_grep({
					grep_open_files = true,
					prompt_title = "Live Grep in Open Files",
				})
			end

			local nvim_search = function ()
				builtin.find_files({ cwd = vim.fn.stdpath("config") })
			end

			-- Telescope keybindings
			vim.keymap.set(
				"n",
				"<leader>sh",
				builtin.help_tags,
				{ desc = "Telescope: [S]earch [H]elp" }
			)
			vim.keymap.set(
				"n",
				"<leader>sk",
				builtin.keymaps,
				{ desc = "Telescope: [S]earch [K]eymaps" }
			)
			vim.keymap.set(
				"n",
				"<leader>sf",
				builtin.find_files,
				{ desc = "Telescope: [S]earch [F]iles" }
			)
			vim.keymap.set(
				"n",
				"<leader>ss",
				builtin.builtin,
				{ desc = "Telescope: [S]earch [S]elect Telescope" }
			)
			vim.keymap.set(
				"n",
				"<leader>sw",
				builtin.grep_string,
				{ desc = "Telescope: [S]earch current [W]ord" }
			)
			vim.keymap.set(
				"n",
				"<leader>sg",
				builtin.live_grep,
				{ desc = "Telescope: [S]earch by [G]rep" }
			)
			vim.keymap.set(
				"n",
				"<leader>sd",
				builtin.diagnostics,
				{ desc = "Telescope: [S]earch [D]iagnostics" }
			)
			vim.keymap.set(
				"n",
				"<leader>sr",
				builtin.resume,
				{ desc = "Telescope: [S]earch [R]esume" }
			)
			vim.keymap.set(
				"n",
				"<leader>s.",
				builtin.oldfiles,
				{ desc = "Telescope: [S]earch Recent Files (\".\" for repeat)" }
			)
			vim.keymap.set(
				"n",
				"<leader><leader>",
				builtin.buffers,
				{ desc = "Telescope: [ ] Find existing buffers" }
			)
			vim.keymap.set(
				"n",
				"<leader>/",
				search_current_buff,
				{ desc = "Telescope: [/] Fuzzily search in current buffer" }
			)
			vim.keymap.set(
				"n",
				"<leader>s/",
				life_grep_open,
				{ desc = "Telescope: [S]earch [/] in Open Files" }
			)
			vim.keymap.set(
				"n",
				"<leader>sn",
				nvim_search,
				{ desc = "Telescope: [S]earch [N]eovim files" }
			)
		end,
	},
}
