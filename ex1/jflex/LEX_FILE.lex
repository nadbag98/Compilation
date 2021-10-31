/***************************/
/* FILE NAME: LEX_FILE.lex */
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
/* Note that this has to be the EXACT same name of the class the CUP generates */
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
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
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

	/**********************************************/
	/* Enable token position extraction from main */
	/**********************************************/
	public int getTokenStartPosition() { return yycolumn + 1; } 
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator		= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f]
INTEGER			= 0 | [1-9][0-9]*
LETTERS			= [a-zA-Z]
ID			= {LETTERS}[a-zA-z0-9]*
LComFriend		= {LETTERS} | [0-9] | [ \t\f] | [\(\)\[\]\{\}\?\!\+\-\*\/\.\;] 

NOTSLASHNOTSTAR 	= {LETTERS} | [0-9] | {WhiteSpace} | [\(\)\[\]\{\}\?\!\+\-\.\;]

BComFriend		= {LETTERS} | [0-9] | {WhiteSpace} | [\(\)\[\]\{\}\?\!\+\-\/\.\;] | (\*)+{NOTSLASHNOTSTAR}

LCOMMENT		= \/\/{LComFriend}*{LineTerminator}

BCOMMENT		= \/\*{BComFriend}*(\*)+\/ 
BADCOM			= \/\*{BComFriend}*
LASTLCOM		= \/\/{LComFriend}*

ONLYSTARS		= \/\*(\*)*\*\/

COMMENT			= {LCOMMENT} | {BCOMMENT} | {LASTLCOM} | {ONLYSTARS}
STRING			= \"{LETTERS}*\"

DIVIDE 			= \/
CATCHALL		= .

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
{WhiteSpace}		{}
{COMMENT}		{ System.out.println(yytext());}
"class"			{ return symbol(TokenNames.CLASS);}
"nil"			{ return symbol(TokenNames.NIL);}
"array"			{ return symbol(TokenNames.ARRAY);}
"while"			{ return symbol(TokenNames.WHILE);}
"extends"		{ return symbol(TokenNames.EXTENDS);}
"return"		{ return symbol(TokenNames.RETURN);}
"new"			{ return symbol(TokenNames.NEW);}
"if"			{ return symbol(TokenNames.IF);}
"["			{ return symbol(TokenNames.LBRACK);}
"]"			{ return symbol(TokenNames.RBRACK);}
"{"			{ return symbol(TokenNames.LBRACE);}
"}"			{ return symbol(TokenNames.RBRACE);}
","			{ return symbol(TokenNames.COMMA);}
"."			{ return symbol(TokenNames.DOT);}
";"			{ return symbol(TokenNames.SEMICOLON);}
":="			{ return symbol(TokenNames.ASSIGN);}
"="			{ return symbol(TokenNames.EQ);}
"<"			{ return symbol(TokenNames.LT);}
">"			{ return symbol(TokenNames.GT);}
"+"			{ return symbol(TokenNames.PLUS);}
"-"			{ return symbol(TokenNames.MINUS);}
"*"			{ return symbol(TokenNames.TIMES);}
"("			{ return symbol(TokenNames.LPAREN);}
")"			{ return symbol(TokenNames.RPAREN);}
{INTEGER}		{ if (yytext().charAt(0) == '0' && yytext().length() > 1) {
				return symbol(TokenNames.ERROR);
				} else return symbol(TokenNames.INT, new Integer(yytext()));}
{ID}			{ return symbol(TokenNames.ID,  new String( yytext()));}
{STRING}		{ return symbol(TokenNames.STRING,     new String( yytext()));}
{BADCOM}		{ return symbol(TokenNames.ERROR);}
{DIVIDE}		{ return symbol(TokenNames.DIVIDE);}
{CATCHALL}		{ return symbol(TokenNames.ERROR);}
<<EOF>>			{ return symbol(TokenNames.EOF);}
}
