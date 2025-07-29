local M = {}

---@param keys string
---@param func function
---@param desc string
---@param conditional boolean
---@param mode string|string[]?
---@param prefix string?
---@param bufnr integer?
local map = function (keys, func, desc, conditional, mode, bufnr, prefix)
	mode = mode or "n"
	conditional = conditional or true
	if conditional then
		vim.keymap.set(
			mode,
			keys,
			func,
			{ buffer = bufnr or nil, desc = prefix .. desc }
		)
	end
end

local map_ls = function (keys, func, desc, bufnr, conditional, mode)
	map(keys, func, desc, conditional, mode, bufnr, "LSP: ")
end

-- This function resolves a difference between neovim nightly (version 0.11) and stable (version 0.10)
---@param client vim.lsp.Client
---@param method vim.lsp.protocol.Method
---@param bufnr? integer some lsp support methods only in specific files
---@return boolean
local function client_supports_method (client, method, bufnr)
	if vim.fn.has("nvim-0.11") == 1 then
		return client:supports_method(method, bufnr)
	else
		return client.supports_method(method, { bufnr = bufnr })
	end
end

M.on_attach = function (client, bufnr)
	local toggle_inlay_hints = function ()
		vim.lsp.inlay_hint.enable(
			not vim.lsp.inlay_hint.is_enabled({ bufnr = bufnr })
		)
	end

	---@type boolean
	local client_supports_highlight = false
		and client
		and client_supports_method(
			client,
			vim.lsp.protocol.Methods.textDocument_documentHighlight,
			bufnr
		)
	---@type boolean
	local client_supports_inlayhint = client
		and client_supports_method(
			client,
			vim.lsp.protocol.Methods.textDocument_inlayHint,
			bufnr
		)

	---@type boolean
	local client_supports_codelens = client
		and client_supports_method(
			client,
			vim.lsp.protocol.Methods.textDocument_codeLens,
			bufnr
		)

	map_ls("<leader>Rn", vim.lsp.buf.rename, "[R]e[n]ame", bufnr)
	map_ls(
		"<leader>ca",
		vim.lsp.buf.code_action,
		"[C]ode [A]ction",
		bufnr,
		true,
		{ "n", "x" }
	)
	map_ls(
		"<leader>gr",
		require("telescope.builtin").lsp_references,
		"[G]oto [R]eferences",
		bufnr
	)
	map_ls(
		"<leader>gi",
		require("telescope.builtin").lsp_implementations,
		"[G]oto [I]mplementation",
		bufnr
	)
	map_ls(
		"<leader>gd",
		require("telescope.builtin").lsp_definitions,
		"[G]oto [D]efinition",
		bufnr
	)
	map_ls("<leader>gD", vim.lsp.buf.declaration, "[G]oto [D]eclaration")
	map_ls(
		"<leader>os",
		require("telescope.builtin").lsp_document_symbols,
		"[O]pen Document [S]ymbols",
		bufnr
	)
	map_ls(
		"<leader>ow",
		require("telescope.builtin").lsp_dynamic_workspace_symbols,
		"[O]pen [W]orkspace",
		bufnr
	)
	map_ls(
		"<leader>gt",
		require("telescope.builtin").lsp_type_definitions,
		"[G]oto [T]ype Definition",
		bufnr
	)
	map_ls(
		"<leader>th",
		toggle_inlay_hints,
		"[T]oggle Inlay [H]ints",
		bufnr,
		client_supports_inlayhint
	)
	map_ls(
		"<leader>cl",
		vim.lsp.codelens.run,
		"[C]ode [L]ens",
		bufnr,
		client_supports_codelens,
		{ "n", "x" }
	)

	if client_supports_codelens then
		local codelens_augroup = vim.api.nvim_create_augroup(
			"kickstart-lsp-codelens",
			{ clear = false }
		)
		vim.api.nvim_create_autocmd(
			{ "BufEnter", "CursorHold", "InsertLeave" },
			{
				group = codelens_augroup,
				buffer = bufnr,
				callback = function ()
					vim.lsp.codelens.refresh()
				end,
			}
		)
	end

	if client_supports_highlight then
		local highlight_augroup = vim.api.nvim_create_augroup(
			"kickstart-lsp-highlight",
			{ clear = false }
		)
		vim.api.nvim_create_autocmd({ "CursorHold", "CursorHoldI" }, {
			buffer = bufnr,
			group = highlight_augroup,
			callback = vim.lsp.buf.document_highlight,
		})
		vim.api.nvim_create_autocmd({ "CursorMoved", "CursorMovedI" }, {
			buffer = bufnr,
			group = highlight_augroup,
			callback = vim.lsp.buf.clear_references,
		})
		vim.api.nvim_create_autocmd("LspDetach", {
			group = vim.api.nvim_create_augroup(
				"kickstart-lsp-detach",
				{ clear = true }
			),
			callback = function (event2)
				vim.lsp.buf.clear_references()
				vim.api.nvim_clear_autocmds({
					group = "kickstart-lsp-highlight",
					buffer = event2.buf,
				})
			end,
		})
	end

	if client and client.name == "clangd" then
		vim.keymap.set(
			"n",
			"<leader>ch",
			"<cmd>LspClangdSwitchSourceHeader<CR>",
			{
				buffer = bufnr,
				desc = "LSP: Switch between source/header",
			}
		)
	end
end

return M
