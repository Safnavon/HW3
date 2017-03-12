/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/***************************/
/* AUTHOR: OREN ISH SHALOM */
/***************************/

/*************/ 
/* USER CODE */ 
/*************/   
   
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/
      
%%
   
/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column
    
/*******************************************************************************/
/* Note that this has to be the EXACT smae name of the class the CUP generates */
/*******************************************************************************/
%cupsym TokenNames

/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup
   
/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied letter to letter into the Lexer class code.                */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{
	/*********************************************************************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/*********************************************************************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/*******************************************/
	/* Enable line number extraction from main */
	/*******************************************/
	public int getLine() { return yyline + 1; } 
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f] | \/\*(.|[\r\n])*?\*\/ | \/\/.*
INTEGER			= 0 | \-?[1-9][0-9]*
ID				= [a-z]+
CLASS_ID= [A-Z][a-zA-Z0-9_]*
ID= [a-z][a-zA-Z0-9_]*
QUOTE= \".*\"
   
/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/
   
/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/

<YYINITIAL> {

"if"				{ return symbol(TokenNames.IF);}
"."					{ return symbol(TokenNames.DOT);}
"+"					{ return symbol(TokenNames.PLUS);}
"-"					{ return symbol(TokenNames.MINUS);}
"*"					{ return symbol(TokenNames.TIMES);}
"/"					{ return symbol(TokenNames.DIVIDE);}
"<="				{ return symbol(TokenNames.LTE, "LTE");}
"<"					{ return symbol(TokenNames.LT, "LT");}
">"					{ return symbol(TokenNames.GT, "GT");}
">="				{ return symbol(TokenNames.GTE, "GTE");}
"=="				{ return symbol(TokenNames.EQUAL, "EQUAL");}
"!="				{ return symbol(TokenNames.NEQUAL, "NEQUAL");}
":="				{ return symbol(TokenNames.ASSIGN);}
"="				    { return symbol(TokenNames.ASSIGN);}
"("					{ return symbol(TokenNames.LPAREN);}
")"					{ return symbol(TokenNames.RPAREN);}
"["					{ return symbol(TokenNames.LBRACK);}
"]"					{ return symbol(TokenNames.RBRACK);}
"{"					{ return symbol(TokenNames.LBRACE);}
"}"					{ return symbol(TokenNames.RBRACE);}
";"					{ return symbol(TokenNames.SEMICOLON);}
","					{ return symbol(TokenNames.COMMA, "COMMA");}
"null"				{ return symbol(TokenNames.NULL, "NULL");}
"new"				{ return symbol(TokenNames.NEW, "NEW");}
"class"				{ return symbol(TokenNames.CLASS, "CLASS");}  
"class"				{ return symbol(TokenNames.CLASS, "CLASS");}
"extends"			{ return symbol(TokenNames.EXTENDS, "EXTENDS");}
"while"				{ return symbol(TokenNames.WHILE, "WHILE");}
"int"			    { return symbol(TokenNames.TYPE_INT, "TYPE_INT");}
"string"			{ return symbol(TokenNames.TYPE_STRING, "TYPE_STRING");}
"void"				{ return symbol(TokenNames.VOID, "VOID");}
"return"			{ return symbol(TokenNames.RETURN, "RETURN");}
{ID}				{ return symbol(TokenNames.ID, new String(yytext()));}
{CLASS_ID}			{ return symbol(TokenNames.CLASS_ID, new String(yytext()));} 
{INTEGER}			{ return symbol(TokenNames.INT, new Integer(yytext()));}
{WhiteSpace}		{ /* just skip what was found, do nothing */ }
{LineTerminator}	{ /* just skip what was found, do nothing */ }
<<EOF>>				{ return symbol(TokenNames.EOF);}
{QUOTE}				{ return symbol(TokenNames.QUOTE, new String(yytext()));}
.|\n       		  	{ throw new Error("Unrecognized string in line " + (yyline+1) + ": \"" + yytext() + "\""); }

}
