/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

//Nicolas Furfari and David Atkins worked in a group on this assignment.

%%

%class Lexer
%byaccj
%line
%column

%{

  public Parser   parser;
  public int      lineno;
  public int      column;

  public Lexer(java.io.Reader r, Parser parser) {
    this(r);
    this.parser = parser;
    this.lineno = 1;
    this.column = 1;
  }

  public int getLine(){
    return this.yyline;
  }

  public int getColumn(){
    return this.yycolumn;
  }

%}

bool        = "true" | "false"
int         = [0-9]+
identifier  = [a-zA-Z][a-zA-Z0-9_]*
newline     = \n
whitespace  = [ \t\r]+
linecomment = "##".*
blkcomment  = "#{"[^]*"}#"

%%

"func"                              { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.FUNC       ; }
"call"                              { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.CALL       ; }
"return"                            { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.RETURN     ; }
"var"                               { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.VAR        ; }
"if"                               { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.IF        ; }
"else"                               { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.ELSE        ; }
"{"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.BEGIN      ; }
"}"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.END        ; }
"while"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.WHILE        ; }
"("                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.LPAREN     ; }
")"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.RPAREN     ; }
"int"                               { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.INT        ; }
"bool"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.BOOL        ; }
"print"                             { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.PRINT      ; }
"<-"                                { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.ASSIGN     ; }
"->"                                { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.FUNCRET    ; }
"+"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.ADD        ; }
"-"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.SUB        ; }
"*"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.MUL        ; }
"/"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.DIV        ; }
"%"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.MOD        ; }
"and"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.AND        ; }
"or"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.OR        ; }
"not"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.NOT        ; }
"<"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.LT        ; }
">"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.GT        ; }
"<="                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.LE        ; }
">="                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.GE        ; }
"="                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.EQ         ; }
"!="                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.NE        ; }
";"                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.SEMI       ; }
","                                 { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.COMMA      ; }
"true" | "false"                    { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.BOOL_LIT   ; }
{int}                               { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.INT_LIT    ; }
{identifier}                        { parser.yylval = new ParserVal(new Token(yytext(), yyline, yycolumn)); return Parser.IDENT      ; }
{linecomment}                       { /* skip */ }
{newline}                           { /* skip */ }
{whitespace}                        { /* skip */ }
{blkcomment}                        { /* skip */ }


\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
