import AST.*;
import java_cup.runtime.*;

%%

%cup
%class Lexer
%type Token
%line
%scanerror RuntimeException

%{
	public int getLineNumber() { return yyline+1; }
%}


LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f]
INTEGER			= (0 | [1-9][0-9]*)
ID				= [a-z][A-Za-z_0-9]*
CLASS_ID		= [A-Z][A-Za-z_0-9]*
QUOTE			= [\"]([ -!#-\[\]-~] | [\\][\\] | [\\][\"] | [\\][t] | [\\][n])*[\"]
COMMENT			= ([/][\*]( [\*]+ ([^/\*] | \r|\n|\r\n) | [^\*] | \r|\n|\r\n)*([\*]+[/])) | [/][/].*


%%

   
<YYINITIAL> {
   
"="					{ return new Token(yyline, yytext(), sym.ASSIGN);}
"boolean"			{ return new Token(yyline, yytext(), sym.BOOLEAN);}
"break"				{ return new Token(yyline, yytext(), sym.BREAK);}
"class"				{ return new Token(yyline, yytext(), sym.CLASS);}
","					{ return new Token(yyline, yytext(), sym.COMMA);}
"continue"			{ return new Token(yyline, yytext(), sym.CONTINUE);}
"/"					{ return new Token(yyline, yytext(), sym.DIVIDE);}
"."					{ return new Token(yyline, yytext(), sym.DOT);}
"=="				{ return new Token(yyline, yytext(), sym.EQUAL);}
"extends"			{ return new Token(yyline, yytext(), sym.EXTENDS);}
"else"				{ return new Token(yyline, yytext(), sym.ELSE);}
"false"				{ return new Token(yyline, yytext(), sym.FALSE);}
">"					{ return new Token(yyline, yytext(), sym.GT);}
">="				{ return new Token(yyline, yytext(), sym.GTE);}
"if"				{ return new Token(yyline, yytext(), sym.IF);}
"int"				{ return new Token(yyline, yytext(), sym.INT);}
"&&"				{ return new Token(yyline, yytext(), sym.LAND);}
"["					{ return new Token(yyline, yytext(), sym.LB);}
"("					{ return new Token(yyline, yytext(), sym.LP);}
"{"					{ return new Token(yyline, yytext(), sym.LCBR);}
"length"			{ return new Token(yyline, yytext(), sym.LENGTH);}
"new"				{ return new Token(yyline, yytext(), sym.NEW);}
"!"					{ return new Token(yyline, yytext(), sym.LNEG);}
"||"				{ return new Token(yyline, yytext(), sym.LOR);}
"<"					{ return new Token(yyline, yytext(), sym.LT);}
"<="				{ return new Token(yyline, yytext(), sym.LTE);}
"-"					{ return new Token(yyline, yytext(), sym.MINUS);}
"%"					{ return new Token(yyline, yytext(), sym.MOD);}
"*"					{ return new Token(yyline, yytext(), sym.MULTIPLY);}
"!="				{ return new Token(yyline, yytext(), sym.NEQUAL);}
"null"				{ return new Token(yyline, yytext(), sym.NULL);}
"+"					{ return new Token(yyline, yytext(), sym.PLUS);}
"]"					{ return new Token(yyline, yytext(), sym.RB);}
"}"					{ return new Token(yyline, yytext(), sym.RCBR);}
"return"			{ return new Token(yyline, yytext(), sym.RETURN);}
")"					{ return new Token(yyline, yytext(), sym.RP);}
";"					{ return new Token(yyline, yytext(), sym.SEMI);}
"static"			{ return new Token(yyline, yytext(), sym.STATIC);}
"string"			{ return new Token(yyline, yytext(), sym.STRING);}
"this"				{ return new Token(yyline, yytext(), sym.THIS);}
"true"				{ return new Token(yyline, yytext(), sym.TRUE);}
"void"				{ return new Token(yyline, yytext(), sym.VOID);}
"while"				{ return new Token(yyline, yytext(), sym.WHILE);}
{INTEGER}			{
		 				return new Token(yyline, "INTEGER", sym.INTEGER, new Integer(yytext()));
					}   
{ID}				{
						return new Token(yyline, "ID", sym.ID, yytext());
					}
{CLASS_ID}			{
						return new Token(yyline, "CLASS_ID", sym.CLASS_ID, yytext());
					}
{QUOTE}				{
						return new Token(yyline, "QUOTE", sym.QUOTE, yytext());
					}
{WhiteSpace}		{ /* just skip what was found, do nothing */ } 
{COMMENT}			{ /* just skip what was found, do nothing */ }
[^]                 { 
						System.out.print((yyline+1) + ": Lexical error: illegal character '" + yytext() + "'");
						throw new RuntimeException();
					} 
}
<<EOF>>				{ 	return new Token(yyline, "EOF", sym.EOF);
					}