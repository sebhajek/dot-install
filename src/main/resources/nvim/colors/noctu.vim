" noctu.nvim - NVim color scheme for 16-color terminals
" Originally based on noctu.vim by Noah Frederick (http://noahfrederick.com/)
" Intended to support:
"   - Following programming languages: 
"     Java, Scala, Lua, Python, JS, TS, JSX, ReScript, C, C++, Rust, Odin, Bash
"   - Following markup languages:
"     HTML, CSS, SCSS, JSON, JSONC, XML, TOML, TeX, LaTeX
"   - Following NVim plugins:
"     Telescope, whichkey, blink, nvim-cmp, tree-sitter, treesitter-context,
"     neo-tree, indent-blankline, gitsigns, LSP
" --------------------------------------------------------------
" Author: sebhajek
" Version:  1.8.0
" --------------------------------------------------------------

" Scheme setup {{{
set background=dark
hi! clear

if exists("syntax_on")
  syntax reset
endif

let g:colors_name = "noctu"

"}}}
" Vim {{{
" Vim UI {{{
hi! Normal              ctermfg=7
hi! Cursor              ctermfg=7     ctermbg=1
hi! CursorLine          ctermbg=0     cterm=NONE
hi! MatchParen          ctermfg=7     ctermbg=NONE  cterm=underline
hi! Pmenu               ctermfg=15    ctermbg=0
hi! PmenuThumb          ctermbg=7
hi! PmenuSBar           ctermbg=8
hi! PmenuSel            ctermfg=0     ctermbg=4
hi! ColorColumn         ctermbg=0
hi! SpellBad            ctermfg=1     ctermbg=NONE  cterm=underline
hi! SpellCap            ctermfg=10    ctermbg=NONE  cterm=underline
hi! SpellRare           ctermfg=11    ctermbg=NONE  cterm=underline
hi! SpellLocal          ctermfg=13    ctermbg=NONE  cterm=underline
hi! NonText             ctermfg=8
hi! LineNr              ctermfg=8     ctermbg=NONE
hi! CursorLineNr        ctermfg=11    ctermbg=0
hi! Visual              ctermfg=15    ctermbg=12
hi! IncSearch           ctermfg=15    ctermbg=13    cterm=NONE
hi! Search              ctermfg=15    ctermbg=10
hi! StatusLine          ctermfg=7     ctermbg=0     cterm=bold
hi! StatusLineNC        ctermfg=14    ctermbg=0     cterm=bold
hi! VertSplit           ctermfg=0     ctermbg=0     cterm=NONE
hi! TabLine             ctermfg=8     ctermbg=0     cterm=NONE
hi! TabLineSel          ctermfg=7     ctermbg=0
hi! Folded              ctermfg=6     ctermbg=0     cterm=bold
hi! Conceal             ctermfg=6     ctermbg=NONE
hi! Directory           ctermfg=12
hi! Title               ctermfg=3     cterm=bold
hi! ErrorMsg            ctermfg=15    ctermbg=1
hi! DiffAdd             ctermfg=0     ctermbg=2
hi! DiffChange          ctermfg=0     ctermbg=3
hi! DiffDelete          ctermfg=0     ctermbg=1
hi! DiffText            ctermfg=0     ctermbg=11    cterm=bold
hi! User1               ctermfg=1     ctermbg=0
hi! User2               ctermfg=4     ctermbg=0
hi! User3               ctermfg=2     ctermbg=0
hi! User4               ctermfg=3     ctermbg=0
hi! User5               ctermfg=5     ctermbg=0
hi! User6               ctermfg=6     ctermbg=0
hi! User7               ctermfg=7     ctermbg=0
hi! User8               ctermfg=8     ctermbg=0
hi! User9               ctermfg=15    ctermbg=5
hi! link CursorColumn  CursorLine
hi! link SignColumn    LineNr
hi! link WildMenu      Visual
hi! link FoldColumn    SignColumn
hi! link WarningMsg    ErrorMsg
hi! link MoreMsg       Title
hi! link Question      MoreMsg
hi! link ModeMsg       MoreMsg
hi! link TabLineFill   StatusLineNC
hi! link SpecialKey    NonText

"}}}
" Vim {{{
hi! link vimSetSep    Delimiter
hi! link vimContinue  Delimiter
hi! link vimHiAttrib  Constant
"}}}
" NERDTree {{{
hi! link NERDTreeHelp      Comment
hi! link NERDTreeExecFile  String
"}}}
" Vimwiki {{{
hi! link VimwikiHeaderChar  markdownHeadingDelimiter
hi! link VimwikiList        markdownListMarker
hi! link VimwikiCode        markdownCode
hi! link VimwikiCodeChar    markdownCodeDelimiter
"}}}
" Help {{{
hi! link helpExample         String
hi! link helpHeadline        Title
hi! link helpSectionDelim    Comment
hi! link helpHyperTextEntry  Statement
hi! link helpHyperTextJump   Underlined
hi! link helpURL             Underlined
"}}}
" Plugins {{{
" CtrlP {{{
hi! link CtrlPMatch    String
hi! link CtrlPLinePre  Comment
"}}}
" Telescope {{{
hi! TelescopeSelection      ctermfg=15    ctermbg=0     cterm=bold
hi! TelescopeSelectionCaret ctermfg=4     ctermbg=0     cterm=bold
hi! TelescopeMultiSelection ctermfg=13    ctermbg=0
hi! TelescopeNormal          ctermfg=7     ctermbg=NONE
hi! TelescopeBorder          ctermfg=8     ctermbg=NONE
hi! TelescopePromptBorder    ctermfg=4     ctermbg=NONE
hi! TelescopeResultsBorder   ctermfg=8     ctermbg=NONE
hi! TelescopePreviewBorder   ctermfg=8     ctermbg=NONE
hi! TelescopeMatching        ctermfg=11    ctermbg=NONE  cterm=bold
hi! TelescopePromptPrefix    ctermfg=4     ctermbg=NONE  cterm=bold
hi! TelescopeTitle           ctermfg=3     ctermbg=NONE  cterm=bold
"}}}
" Blink / nvim-cmp {{{
hi! BlinkCmpMenu             ctermfg=15    ctermbg=0
hi! BlinkCmpMenuBorder       ctermfg=8     ctermbg=0
hi! BlinkCmpMenuSelection    ctermfg=0     ctermbg=4
hi! BlinkCmpSignatureHelp    ctermfg=7     ctermbg=0
hi! BlinkCmpSignatureHelpBorder ctermfg=8  ctermbg=0
hi! BlinkCmpDoc              ctermfg=7     ctermbg=0
hi! BlinkCmpDocBorder        ctermfg=8     ctermbg=0
hi! BlinkCmpKindText         ctermfg=7     ctermbg=NONE
hi! BlinkCmpKindMethod       ctermfg=4     ctermbg=NONE
hi! BlinkCmpKindFunction     ctermfg=4     ctermbg=NONE
hi! BlinkCmpKindConstructor  ctermfg=4     ctermbg=NONE
hi! BlinkCmpKindField        ctermfg=6     ctermbg=NONE
hi! BlinkCmpKindVariable     ctermfg=7     ctermbg=NONE
hi! BlinkCmpKindClass        ctermfg=3     ctermbg=NONE
hi! BlinkCmpKindInterface    ctermfg=3     ctermbg=NONE
hi! BlinkCmpKindModule       ctermfg=13    ctermbg=NONE
hi! BlinkCmpKindProperty     ctermfg=6     ctermbg=NONE
hi! BlinkCmpKindUnit         ctermfg=12    ctermbg=NONE
hi! BlinkCmpKindValue        ctermfg=12    ctermbg=NONE
hi! BlinkCmpKindEnum         ctermfg=3     ctermbg=NONE
hi! BlinkCmpKindKeyword      ctermfg=2     ctermbg=NONE
hi! BlinkCmpKindSnippet      ctermfg=11    ctermbg=NONE
hi! BlinkCmpKindColor        ctermfg=13    ctermbg=NONE
hi! BlinkCmpKindFile         ctermfg=12    ctermbg=NONE
hi! BlinkCmpKindReference    ctermfg=13    ctermbg=NONE
hi! BlinkCmpKindFolder       ctermfg=12    ctermbg=NONE
hi! BlinkCmpKindEnumMember   ctermfg=6     ctermbg=NONE
hi! BlinkCmpKindConstant     ctermfg=13    ctermbg=NONE
hi! BlinkCmpKindStruct       ctermfg=3     ctermbg=NONE
hi! BlinkCmpKindEvent        ctermfg=13    ctermbg=NONE
hi! BlinkCmpKindOperator     ctermfg=7     ctermbg=NONE
hi! BlinkCmpKindTypeParameter ctermfg=3    ctermbg=NONE

" nvim-cmp compatibility
hi! link CmpItemMenu           BlinkCmpMenu
hi! link CmpItemMenuBorder     BlinkCmpMenuBorder
hi! link CmpItemAbbrMatch      TelescopeMatching
hi! link CmpItemAbbrMatchFuzzy TelescopeMatching
hi! link CmpItemKindText       BlinkCmpKindText
hi! link CmpItemKindMethod     BlinkCmpKindMethod
hi! link CmpItemKindFunction   BlinkCmpKindFunction
hi! link CmpItemKindConstructor BlinkCmpKindConstructor
hi! link CmpItemKindField      BlinkCmpKindField
hi! link CmpItemKindVariable   BlinkCmpKindVariable
hi! link CmpItemKindClass      BlinkCmpKindClass
hi! link CmpItemKindInterface  BlinkCmpKindInterface
hi! link CmpItemKindModule     BlinkCmpKindModule
hi! link CmpItemKindProperty   BlinkCmpKindProperty
hi! link CmpItemKindUnit       BlinkCmpKindUnit
hi! link CmpItemKindValue      BlinkCmpKindValue
hi! link CmpItemKindEnum       BlinkCmpKindEnum
hi! link CmpItemKindKeyword    BlinkCmpKindKeyword
hi! link CmpItemKindSnippet    BlinkCmpKindSnippet
hi! link CmpItemKindColor      BlinkCmpKindColor
hi! link CmpItemKindFile       BlinkCmpKindFile
hi! link CmpItemKindReference  BlinkCmpKindReference
hi! link CmpItemKindFolder     BlinkCmpKindFolder
hi! link CmpItemKindEnumMember BlinkCmpKindEnumMember
hi! link CmpItemKindConstant   BlinkCmpKindConstant
hi! link CmpItemKindStruct     BlinkCmpKindStruct
hi! link CmpItemKindEvent      BlinkCmpKindEvent
hi! link CmpItemKindOperator   BlinkCmpKindOperator
hi! link CmpItemKindTypeParameter BlinkCmpKindTypeParameter
"}}}
" Neo-tree {{{
hi! NeoTreeNormal            ctermfg=7     ctermbg=NONE
hi! NeoTreeNormalNC          ctermfg=7     ctermbg=NONE
hi! NeoTreeDirectoryName     ctermfg=12    ctermbg=NONE
hi! NeoTreeDirectoryIcon     ctermfg=12    ctermbg=NONE
hi! NeoTreeFileName          ctermfg=7     ctermbg=NONE
hi! NeoTreeFileIcon          ctermfg=7     ctermbg=NONE
hi! NeoTreeRootName          ctermfg=3     ctermbg=NONE  cterm=bold
hi! NeoTreeSymbolicLinkTarget ctermfg=6    ctermbg=NONE
hi! NeoTreeGitAdded          ctermfg=2     ctermbg=NONE
hi! NeoTreeGitDeleted        ctermfg=1     ctermbg=NONE
hi! NeoTreeGitModified       ctermfg=3     ctermbg=NONE
hi! NeoTreeGitConflict       ctermfg=1     ctermbg=NONE  cterm=bold
hi! NeoTreeGitUntracked      ctermfg=13    ctermbg=NONE
hi! NeoTreeGitUnstaged       ctermfg=11    ctermbg=NONE
hi! NeoTreeGitStaged         ctermfg=2     ctermbg=NONE
hi! NeoTreeIndentMarker      ctermfg=8     ctermbg=NONE
hi! NeoTreeExpander          ctermfg=8     ctermbg=NONE
hi! NeoTreeCursorLine        ctermfg=15    ctermbg=0     cterm=bold
"}}}
" Indent-blankline {{{
hi! IblIndent                ctermfg=8     ctermbg=NONE
hi! IblWhitespace            ctermfg=8     ctermbg=NONE
hi! IblScope                 ctermfg=6     ctermbg=NONE
"}}}
" Gitsigns {{{
hi! GitSignsAdd              ctermfg=2     ctermbg=NONE
hi! GitSignsChange           ctermfg=3     ctermbg=NONE
hi! GitSignsDelete           ctermfg=1     ctermbg=NONE
hi! GitSignsTopdelete        ctermfg=1     ctermbg=NONE
hi! GitSignsChangedelete     ctermfg=5     ctermbg=NONE
hi! GitSignsUntracked        ctermfg=13    ctermbg=NONE
hi! GitSignsAddNr            ctermfg=2     ctermbg=NONE
hi! GitSignsChangeNr         ctermfg=3     ctermbg=NONE
hi! GitSignsDeleteNr         ctermfg=1     ctermbg=NONE
hi! GitSignsTopdeleteNr      ctermfg=1     ctermbg=NONE
hi! GitSignsChangedeleteNr   ctermfg=5     ctermbg=NONE
hi! GitSignsUntrackedNr      ctermfg=13    ctermbg=NONE
hi! GitSignsAddLn            ctermfg=2     ctermbg=0
hi! GitSignsChangeLn         ctermfg=3     ctermbg=0
hi! GitSignsDeleteLn         ctermfg=1     ctermbg=0
hi! GitSignsAddPreview       ctermfg=2     ctermbg=0
hi! GitSignsDeletePreview    ctermfg=1     ctermbg=0
hi! GitSignsCurrentLineBlame ctermfg=8     ctermbg=NONE  cterm=italic
"}}}
" TreeSitter Context {{{
hi! TreesitterContext        ctermfg=7     ctermbg=0
hi! TreesitterContextLineNumber ctermfg=11 ctermbg=0
hi! TreesitterContextSeparator  ctermfg=8  ctermbg=NONE
"}}}
" LSP {{{
hi! DiagnosticError          ctermfg=1     ctermbg=NONE
hi! DiagnosticWarn           ctermfg=3     ctermbg=NONE
hi! DiagnosticInfo           ctermfg=4     ctermbg=NONE
hi! DiagnosticHint           ctermfg=6     ctermbg=NONE
hi! DiagnosticOk             ctermfg=2     ctermbg=NONE
hi! DiagnosticVirtualTextError ctermfg=1   ctermbg=NONE  cterm=italic
hi! DiagnosticVirtualTextWarn  ctermfg=3   ctermbg=NONE  cterm=italic
hi! DiagnosticVirtualTextInfo  ctermfg=4   ctermbg=NONE  cterm=italic
hi! DiagnosticVirtualTextHint  ctermfg=6   ctermbg=NONE  cterm=italic
hi! DiagnosticVirtualTextOk    ctermfg=2   ctermbg=NONE  cterm=italic
hi! DiagnosticUnderlineError   ctermfg=1   ctermbg=NONE  cterm=underline
hi! DiagnosticUnderlineWarn    ctermfg=3   ctermbg=NONE  cterm=underline
hi! DiagnosticUnderlineInfo    ctermfg=4   ctermbg=NONE  cterm=underline
hi! DiagnosticUnderlineHint    ctermfg=6   ctermbg=NONE  cterm=underline
hi! DiagnosticUnderlineOk      ctermfg=2   ctermbg=NONE  cterm=underline
hi! DiagnosticFloatingError    ctermfg=1   ctermbg=0
hi! DiagnosticFloatingWarn     ctermfg=3   ctermbg=0
hi! DiagnosticFloatingInfo     ctermfg=4   ctermbg=0
hi! DiagnosticFloatingHint     ctermfg=6   ctermbg=0
hi! DiagnosticFloatingOk       ctermfg=2   ctermbg=0
hi! DiagnosticSignError        ctermfg=1   ctermbg=NONE
hi! DiagnosticSignWarn         ctermfg=3   ctermbg=NONE
hi! DiagnosticSignInfo         ctermfg=4   ctermbg=NONE
hi! DiagnosticSignHint         ctermfg=6   ctermbg=NONE
hi! DiagnosticSignOk           ctermfg=2   ctermbg=NONE

" LSP References
hi! LspReferenceText         ctermfg=NONE  ctermbg=0     cterm=bold
hi! LspReferenceRead         ctermfg=NONE  ctermbg=0     cterm=bold
hi! LspReferenceWrite        ctermfg=NONE  ctermbg=0     cterm=bold

" LSP Semantic Tokens
hi! link @lsp.type.class              Type
hi! link @lsp.type.decorator          PreProc
hi! link @lsp.type.enum               Type
hi! link @lsp.type.enumMember         Constant
hi! link @lsp.type.function           Function
hi! link @lsp.type.interface          Type
hi! link @lsp.type.macro              PreProc
hi! link @lsp.type.method             Function
hi! link @lsp.type.namespace          Identifier
hi! link @lsp.type.parameter          Identifier
hi! link @lsp.type.property           Identifier
hi! link @lsp.type.struct             Type
hi! link @lsp.type.type               Type
hi! link @lsp.type.typeParameter      Type
hi! link @lsp.type.variable           Identifier
"}}}
" Shell {{{
hi! shDerefSimple     ctermfg=11
hi! link shDerefVar  shDerefSimple
"}}}
" Netrw {{{
hi! netrwExe       ctermfg=9
hi! netrwClassify  ctermfg=13  cterm=bold
"}}}
"}}}
"}}}
" Generic syntax {{{
" Syntastic {{{
hi! SyntasticWarningSign       ctermfg=3  ctermbg=NONE
hi! SyntasticErrorSign         ctermfg=1  ctermbg=NONE
hi! SyntasticStyleWarningSign  ctermfg=2  ctermbg=NONE
hi! SyntasticStyleErrorSign    ctermfg=4  ctermbg=NONE
"}}}
" Core Syntax Groups {{{
hi! Delimiter       ctermfg=7
hi! Comment         ctermfg=9   cterm=italic
hi! Underlined      ctermfg=4   cterm=underline
hi! Type            ctermfg=5
hi! String          ctermfg=11
hi! Keyword         ctermfg=2   cterm=bold
hi! Todo            ctermfg=15  ctermbg=NONE     cterm=bold,underline
hi! Function        ctermfg=4
hi! Identifier      ctermfg=7   cterm=NONE
hi! Statement       ctermfg=2   cterm=bold
hi! Constant        ctermfg=13
hi! Number          ctermfg=12
hi! Boolean         ctermfg=4
hi! Special         ctermfg=13
hi! Ignore          ctermfg=0
hi! PreProc         ctermfg=6   cterm=bold
hi! link Operator  Delimiter
hi! link Error     ErrorMsg
"}}}
" Tree Sitter {{{
hi! link @comment                   Comment
hi! link @keyword                   Keyword
hi! link @keyword.function          Keyword
hi! link @operator                  Operator
hi! link @punctuation               Delimiter
hi! link @punctuation.delimiter     Delimiter
hi! link @punctuation.bracket       Delimiter
hi! link @punctuation.special       Special
hi! link @string                    String
hi! link @string.documentation      Comment
hi! link @character                 Character
hi! link @number                    Number
hi! link @boolean                   Boolean
hi! link @float                     Number
hi! link @function                  Function
hi! link @function.builtin          Function
hi! link @function.macro            PreProc
hi! link @parameter                 Identifier
hi! link @method                    Function
hi! link @field                     Identifier
hi! link @property                  Identifier
hi! link @variable                  Identifier
hi! link @variable.builtin          Constant
hi! link @type                      Type
hi! link @type.builtin              Type
hi! link @namespace                 Identifier
hi! link @module                    Identifier
hi! link @constant                  Constant
hi! link @constant.builtin          Constant
hi! link @tag                       Statement
hi! link @attribute                 PreProc
hi! link @constructor               Function
hi! link @label                     Statement
hi! link @text                      Normal
hi! link @text.title                Title
hi! link @text.strong               markdownBold
hi! link @text.emphasis             markdownItalic
hi! link @text.uri                  Underlined
hi! link @text.literal              String
hi! link @text.todo                 Todo
hi! link @note                      Todo
hi! link @warning                   WarningMsg
hi! link @danger                    ErrorMsg
"}}}
"}}}
" Programming Languages {{{
" Java {{{
hi! link javaAnnotation    PreProc
hi! link javaClass         Type
hi! link javaComment       Comment
hi! link javaConditional   Keyword
hi! link javaDocComment    Comment
hi! link javaDocTags       Keyword
hi! link javaDocMarkup     PreProc
hi! link javaEnum          Type
hi! link javaException     Exception
hi! link javaFunction      Function
hi! link javaIdentifier    Identifier
hi! link javaImport        PreProc
hi! link javaInterface     Type
hi! link javaKeyword       Keyword
hi! link javaNumber        Number
hi! link javaOperator      Operator
hi! link javaStatement     Statement
hi! link javaString        String
hi! link javaVariable      Identifier
"}}}
" Scala {{{
hi! link scalaAnnotation      PreProc
hi! link scalaClass           Type
hi! link scalaComment         Comment
hi! link scalaDocComment      Comment
hi! link scalaFunction        Function
hi! link scalaKeyword         Keyword
hi! link scalaMacro           PreProc
hi! link scalaNumber          Number
hi! link scalaOperator        Operator
hi! link scalaString          String
hi! link scalaTrait           Type
hi! link scalaType            Type
hi! link scalaVariable        Identifier
"}}}
" Rust {{{
hi! link rustFunction       Function
hi! link rustMacro          PreProc
hi! link rustKeyword        Keyword
hi! link rustString         String
hi! link rustNumber         Number
hi! link rustBoolean        Boolean
hi! link rustComment        Comment
hi! link rustOperator       Operator
hi! link rustType           Type
hi! link rustIdentifier     Identifier
"}}}
" C / C++ {{{
hi! link cFunction        Function
hi! link cStructure       Type
hi! link cType            Type
hi! link cKeyword         Keyword
hi! link cString          String
hi! link cNumber          Number
hi! link cPreCondit       PreProc
hi! link cComment         Comment
hi! link cOperator        Operator
hi! link cIdentifier      Identifier
"}}}
" Python {{{
hi! link pythonFunction      Function
hi! link pythonDecorator     PreProc
hi! link pythonBuiltin       Statement
hi! link pythonStatement     Statement
hi! link pythonOperator      Operator
hi! link pythonBoolean       Boolean
hi! link pythonNumber        Number
hi! link pythonString        String
hi! link pythonComment       Comment
hi! link pythonClass         Type
hi! link pythonIdentifier    Identifier
hi! link pythonParameter     Identifier
"}}}
" Lua {{{
hi! link luaFunction      Function
hi! link luaTable        Identifier
hi! link luaKeyword      Keyword
hi! link luaString       String
hi! link luaNumber       Number
hi! link luaBoolean      Boolean
hi! link luaComment      Comment
hi! link luaOperator     Operator
hi! link luaParen        Delimiter
hi! link luaVariable     Identifier
"}}}
" JavaScript {{{
hi! link javaScript        Normal
hi! link javaScriptBraces  Delimiter
" JSX {{{
hi! link @tag.jsx                   Statement
hi! link @tag.attribute.jsx         PreProc
hi! link @constructor.jsx           Function
hi! link @string.special.jsx        String
hi! link @variable.member.jsx       Identifier
hi! link @punctuation.bracket.jsx   Delimiter
"}}}
" TypeScript {{{
hi! link typescriptReserved     Keyword
hi! link typescriptEndColons    Delimiter
hi! link typescriptParens       Delimiter
hi! link typescriptBraces       Delimiter
hi! link typescriptOpSymbols    Operator
hi! link typescriptFuncKeyword  Keyword
hi! link typescriptIdentifier   Identifier
hi! link typescriptGlobal       Constant
hi! link typescriptType         Type
hi! link typescriptObjectLabel  Identifier
hi! link typescriptVariable     Identifier
hi! link typescriptNull         Constant
hi! link typescriptNumber       Number
hi! link typescriptBoolean      Boolean
hi! link typescriptString       String
hi! link typescriptCall         Function
"}}}
" TSX {{{
hi! link tsxTagName             Statement
hi! link tsxCloseTag            Statement
hi! link tsxCloseComponentName Statement
hi! link tsxComponentName       Statement
hi! link tsxAttribute           PreProc
hi! link tsxEqual               Operator
hi! link tsxString              String
hi! link tsxIntrinsicTagName    Statement
hi! link tsxJsxRegion           Normal
hi! link @tag.tsx               Statement
hi! link @tag.attribute.tsx     PreProc
hi! link @constructor.tsx       Function
hi! link @string.special.tsx    String
hi! link @variable.member.tsx   Identifier
hi! link @punctuation.bracket.tsx Delimiter
"}}}
"}}}
" Bash {{{
hi! link shFunction       Function
hi! link shParameter      Identifier
hi! link shVariable       Identifier
hi! link shKeyword        Keyword
hi! link shString         String
hi! link shNumber         Number
hi! link shBoolean        Boolean
hi! link shComment        Comment
hi! link shOperator       Operator
"}}}
" PHP {{{
hi! phpSpecialFunction    ctermfg=5
hi! phpIdentifier         ctermfg=11
hi! phpParent             ctermfg=7
hi! link phpVarSelector  phpIdentifier
hi! link phpHereDoc      String
hi! link phpDefine       Statement
"}}}
"}}}
" Markup Languages {{{
" HTML {{{
hi! htmlTagName              ctermfg=2
hi! htmlTag                  ctermfg=2
hi! htmlArg                  ctermfg=10
hi! htmlH1                   cterm=bold
hi! htmlBold                 cterm=bold
hi! htmlItalic               cterm=underline
hi! htmlUnderline            cterm=underline
hi! htmlBoldItalic           cterm=bold,underline
hi! htmlBoldUnderline        cterm=bold,underline
hi! htmlUnderlineItalic      cterm=underline
hi! htmlBoldUnderlineItalic  cterm=bold,underline
hi! link htmlLink           Underlined
hi! link htmlEndTag         htmlTag
"}}}
" CSS {{{
hi! cssTagName              ctermfg=1     cterm=bold
hi! cssIdentifier           ctermfg=11    cterm=NONE
hi! cssClassName            ctermfg=11    cterm=NONE
hi! cssPseudoClass          ctermfg=13    cterm=NONE
hi! cssPseudoClassId        ctermfg=13    cterm=NONE
hi! cssColor                ctermfg=12    cterm=NONE
hi! cssImportant            ctermfg=1     cterm=bold
hi! cssAtRule               ctermfg=5     cterm=bold
hi! cssAtKeyword            ctermfg=5     cterm=bold
hi! cssMedia                ctermfg=5     cterm=bold
hi! cssMediaType            ctermfg=13    cterm=NONE
hi! cssFunction             ctermfg=4     cterm=NONE
hi! cssURL                  ctermfg=11    cterm=underline
hi! cssUnitDecorators       ctermfg=12    cterm=NONE
hi! cssValueLength          ctermfg=12    cterm=NONE
hi! cssValueNumber          ctermfg=12    cterm=NONE
hi! cssValueAngle           ctermfg=12    cterm=NONE
hi! cssValueTime            ctermfg=12    cterm=NONE
hi! cssValueFrequency       ctermfg=12    cterm=NONE
hi! cssBraces               ctermfg=7     cterm=NONE
hi! cssInclude              ctermfg=7     cterm=NONE
hi! cssKeyFrame             ctermfg=13    cterm=NONE
hi! cssKeyFrameSelector     ctermfg=2     cterm=bold
hi! cssVendor               ctermfg=6     cterm=NONE
hi! cssNoise                ctermfg=7     cterm=NONE
hi! link cssAttr           String
hi! link cssProp           Statement
hi! link cssComment        Comment
hi! link cssDefinition     Normal
hi! link cssStringQ        String
hi! link cssStringQQ       String
"}}}
" SCSS {{{
hi! scssImport              ctermfg=5     cterm=bold
hi! scssInclude             ctermfg=5     cterm=bold
hi! scssMixin               ctermfg=5     cterm=bold
hi! scssExtend              ctermfg=5     cterm=bold
hi! scssFunction            ctermfg=4     cterm=NONE
hi! scssVariable            ctermfg=6     cterm=NONE
hi! scssVariableAssignment  ctermfg=7     cterm=NONE
hi! scssDefault             ctermfg=13    cterm=bold
hi! scssGlobal              ctermfg=13    cterm=bold
hi! scssOptional            ctermfg=13    cterm=bold
hi! scssInterpolation       ctermfg=6     cterm=NONE
hi! scssInterpolationDelimiter ctermfg=6  cterm=NONE
hi! scssAmpersand           ctermfg=1     cterm=bold
hi! scssAt                  ctermfg=5     cterm=bold
hi! link scssComment        Comment
hi! link scssString         String
hi! link scssNumber         Number
hi! link scssBraces         Delimiter
"}}}
" XML {{{
hi! xmlTagName       ctermfg=4
hi! xmlTag           ctermfg=12
hi! link xmlString  xmlTagName
hi! link xmlAttrib  xmlTag
hi! link xmlEndTag  xmlTag
hi! link xmlEqual   xmlTag
"}}}
" JSON {{{
hi! jsonKeyword             ctermfg=4     cterm=NONE
hi! jsonString              ctermfg=11    cterm=NONE
hi! jsonNumber              ctermfg=12    cterm=NONE
hi! jsonBoolean             ctermfg=4     cterm=NONE
hi! jsonNull                ctermfg=13    cterm=NONE
hi! jsonBraces              ctermfg=7     cterm=NONE
hi! jsonNoise               ctermfg=7     cterm=NONE
hi! jsonQuote               ctermfg=7     cterm=NONE
hi! jsonEscape              ctermfg=6     cterm=bold
hi! link jsonStringSQError  Error
hi! link jsonNoQuotesError  Error
hi! link jsonTripleQuotesError Error
hi! link jsonMissingCommaError Error
hi! link jsonTrailingCommaError Error
hi! link jsonDuplicateKey   Error
hi! link jsonComment        Comment
"}}}
" JSONC {{{
hi! link jsoncKeyword       jsonKeyword
hi! link jsoncString        jsonString
hi! link jsoncNumber        jsonNumber
hi! link jsoncBoolean       jsonBoolean
hi! link jsoncNull          jsonNull
hi! link jsoncBraces        jsonBraces
hi! link jsoncNoise         jsonNoise
hi! link jsoncQuote         jsonQuote
hi! link jsoncEscape        jsonEscape
hi! link jsoncComment       Comment
hi! link jsoncLineComment   Comment
"}}}
" TOML {{{
hi! tomlTable               ctermfg=5     cterm=bold
hi! tomlKey                 ctermfg=4     cterm=NONE
hi! tomlString              ctermfg=11    cterm=NONE
hi! tomlInteger             ctermfg=12    cterm=NONE
hi! tomlFloat               ctermfg=12    cterm=NONE
hi! tomlBoolean             ctermfg=4     cterm=NONE
hi! tomlDate                ctermfg=13    cterm=NONE
hi! tomlArray               ctermfg=7     cterm=NONE
hi! tomlTableArray          ctermfg=5     cterm=bold
hi! tomlKeyDot              ctermfg=7     cterm=NONE
hi! tomlKeyValueSep         ctermfg=7     cterm=NONE
hi! link tomlComment        Comment
hi! link tomlError          Error
"}}}
" TeX / LaTeX {{{
hi! texStatement            ctermfg=5     cterm=bold
hi! texBeginEnd             ctermfg=2     cterm=bold
hi! texBeginEndName         ctermfg=4     cterm=NONE
hi! texOption               ctermfg=11    cterm=NONE
hi! texArg                  ctermfg=11    cterm=NONE
hi! texPartArgTitle         ctermfg=3     cterm=bold
hi! texTitle                ctermfg=3     cterm=bold
hi! texRefZone              ctermfg=12    cterm=underline
hi! texMathZoneX            ctermfg=11    cterm=NONE
hi! texMathZoneA            ctermfg=11    cterm=NONE
hi! texMathZoneB            ctermfg=11    cterm=NONE
hi! texMathZoneC            ctermfg=11    cterm=NONE
hi! texMathZoneD            ctermfg=11    cterm=NONE
hi! texMathZoneE            ctermfg=11    cterm=NONE
hi! texMathZoneV            ctermfg=11    cterm=NONE
hi! texMathZoneW            ctermfg=11    cterm=NONE
hi! texMathZoneY            ctermfg=11    cterm=NONE
hi! texMathZoneZ            ctermfg=11    cterm=NONE
hi! texMathDelimZone        ctermfg=6     cterm=NONE
hi! texDelimiter            ctermfg=1     cterm=NONE
hi! texSpecialChar          ctermfg=6     cterm=bold
hi! texCite                 ctermfg=12    cterm=NONE
hi! texBibKey               ctermfg=12    cterm=NONE
hi! texTabularChar          ctermfg=1     cterm=NONE
hi! texInputFile            ctermfg=4     cterm=underline
hi! texLength               ctermfg=12    cterm=NONE
hi! texMatcher              ctermfg=1     cterm=NONE
hi! texRefArg               ctermfg=12    cterm=NONE
hi! texFileArg              ctermfg=4     cterm=underline
hi! texUrlArg               ctermfg=4     cterm=underline
hi! texOnlyMath             ctermfg=8     cterm=NONE
hi! texComment              ctermfg=9     cterm=italic
hi! texTodo                 ctermfg=15    ctermbg=NONE  cterm=bold,underline

" LaTeX specific
hi! link latexStatement     texStatement
hi! link latexBeginEnd      texBeginEnd
hi! link latexDocumentclass texStatement
hi! link latexPackage      texStatement
hi! link latexTitle        texTitle
hi! link latexSection      texPartArgTitle
hi! link latexMath         texMathZoneX
hi! link latexComment      texComment
"}}}
" Markdown {{{
hi! link markdownHeadingRule        NonText
hi! link markdownHeadingDelimiter   markdownHeadingRule
hi! link markdownLinkDelimiter      Delimiter
hi! link markdownURLDelimiter       Delimiter
hi! link markdownCodeDelimiter      NonText
hi! link markdownLinkTextDelimiter  markdownLinkDelimiter
hi! link markdownUrl                markdownLinkText
hi! link markdownAutomaticLink      markdownLinkText
hi! link markdownCodeBlock          String
hi! markdownCode                     cterm=bold
hi! markdownBold                     cterm=bold
hi! markdownItalic                   cterm=underline
"}}}
"}}}
" SCM {{{
" Git {{{
hi! gitCommitBranch               ctermfg=3
hi! gitCommitSelectedType         ctermfg=10
hi! gitCommitSelectedFile         ctermfg=2
hi! gitCommitUnmergedType         ctermfg=9
hi! gitCommitUnmergedFile         ctermfg=1
hi! link gitCommitFile           Directory
hi! link gitCommitUntrackedFile  gitCommitUnmergedFile
hi! link gitCommitDiscardedType  gitCommitUnmergedType
hi! link gitCommitDiscardedFile  gitCommitUnmergedFile
"}}}
" Diff {{{
hi! diffAdded  ctermfg=2
hi! diffRemoved  ctermfg=1
hi! link diffFile  PreProc
hi! link diffLine  Title
"}}}
"}}}

" vim: fdm=marker:sw=2:sts=2:et
