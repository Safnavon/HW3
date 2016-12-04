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
    private Symbol symbol(int type, String name)              {System.out.printf("%d:%s\n",yyline+1, name); return new Symbol(type, yyline, yycolumn);}
    private Symbol symbol(int type, String name, Object value) {System.out.printf("%d:%s(%s)\n",yyline+1, name ,yytext());return new Symbol(type, yyline, yycolumn, value);}

%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f] | \/\*(.|[\r\n])*?\*\/ | \/\/.*
INTEGER			= 0 | [1-9][0-9]*
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
   
"+"					{ return symbol(CUP_FILESym.PLUS, "PLUS");}
"-"					{ return symbol(CUP_FILESym.MINUS, "MINUS");}
"*"					{ return symbol(CUP_FILESym.TIMES, "TIMES");}
"/"					{ return symbol(CUP_FILESym.DIVIDE, "DIVIDE");}
"("					{ return symbol(CUP_FILESym.LPAREN, "LPAREN");}
")"					{ return symbol(CUP_FILESym.RPAREN, "RPAREN");}
{INTEGER}			{return symbol(CUP_FILESym.NUMBER,"NUMBER", new Integer(yytext()));}   
{WhiteSpace}		{ /* just skip what was found, do nothing */ }
"assign"					{ return symbol(CUP_FILESym.ASSIGN, "ASSIGN");}
"boolean"					{ return symbol(CUP_FILESym.BOOLEAN, "BOOLEAN");}
"break"					{ return symbol(CUP_FILESym.BREAK, "BREAK");}
"class"					{ return symbol(CUP_FILESym.CLASS, "CLASS");}
","					{ return symbol(CUP_FILESym.COMMA, "COMMA");}
"continue"					{ return symbol(CUP_FILESym.CONTINUE, "CONTINUE");}
"."					{ return symbol(CUP_FILESym.DOT, "DOT");}
"="					{ return symbol(CUP_FILESym.EQUAL, "EQUAL");}
"extends"					{ return symbol(CUP_FILESym.EXTENDS, "EXTENDS");}
"else"					{ return symbol(CUP_FILESym.ELSE, "ELSE");}
"false"					{ return symbol(CUP_FILESym.FALSE, "FALSE");}
">"					{ return symbol(CUP_FILESym.GT, "GT");}
">="					{ return symbol(CUP_FILESym.GTE, "GTE");}
"if"					{ return symbol(CUP_FILESym.IF, "IF");}
"int"					{ return symbol(CUP_FILESym.INT, "INT");}
"integer"					{ return symbol(CUP_FILESym.INTEGER, "INTEGER");}
"&&"					{ return symbol(CUP_FILESym.LAND, "LAND");}
"["					{ return symbol(CUP_FILESym.LB, "LB");}
"{"					{ return symbol(CUP_FILESym.LCBR, "LCBR");}
"length"					{ return symbol(CUP_FILESym.LENGTH, "LENGTH");}
"new"					{ return symbol(CUP_FILESym.NEW, "NEW");}
"!"					{ return symbol(CUP_FILESym.LNEG, "LNEG");}
"||"					{ return symbol(CUP_FILESym.LOR, "LOR");}
"<="					{ return symbol(CUP_FILESym.LTE, "LTE");}
"<"					{ return symbol(CUP_FILESym.LT, "LT");}
"%"					{ return symbol(CUP_FILESym.MOD, "MOD");} 
"!="				{ return symbol(CUP_FILESym.NEQUAL, "NEQUAL");}
"null"					{ return symbol(CUP_FILESym.NULL, "NULL");}
"]"					{ return symbol(CUP_FILESym.RB, "RB");}
"}"					{ return symbol(CUP_FILESym.RCBR, "RCBR");}
"return"					{ return symbol(CUP_FILESym.RETURN, "RETURN");}
"rp"					{ return symbol(CUP_FILESym.RP, "RP");}
";"					{ return symbol(CUP_FILESym.SEMI, "SEMI");}
"static"					{ return symbol(CUP_FILESym.STATIC, "STATIC");}
"string"					{ return symbol(CUP_FILESym.STRING, "STRING");} 
"this"					{ return symbol(CUP_FILESym.THIS, "THIS");}
"true"					{ return symbol(CUP_FILESym.TRUE, "TRUE");}
"void"					{ return symbol(CUP_FILESym.VOID, "VOID");} 
"while"					{ return symbol(CUP_FILESym.WHILE, "WHILE");}
<<EOF>>					{  }  
{CLASS_ID}					{ return symbol(CUP_FILESym.CLASS_ID, "CLASS_ID", yytext());}
{ID}					{ return symbol(CUP_FILESym.ID, "ID", yytext());}
{QUOTE}					{ return symbol(CUP_FILESym.QUOTE, "QUOTE", yytext());}
.|\n       		  	{ throw new Error("Unrecognized string in line " + (yyline+1) + ": \"" + yytext() + "\""); }

}
