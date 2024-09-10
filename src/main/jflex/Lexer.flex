/**
 * Escáner que detecta el lenguaje C_1
*/

package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;

%%

%{

public Token actual;

%}

%public
%class Lexer
%standalone
%unicode

espacio=[ \t\n]
if = "if"
else = "else"
while = "while"
int = "int"
float = "float"
coma = ","
pyc = ";"
lpar = "("
rpar = ")"
ent = [0-9]+
decimal = {ent} \.{ent}*
notcient = {ent} \.{ent}* ([eE][+-]? {ent})?


num = {ent} | {decimal} | {notcient}
id = [a-zA-Z_][a-zA-Z_0-9]*


%%

{espacio} {/* La acción léxica puede ir vacía si queremos que el escáner ignore la regla */}

{lpar}             { actual = new Token(ClaseLexica.LPAR, yytext()); System.out.println(actual.toString()); }
{rpar}             { actual = new Token(ClaseLexica.RPAR, yytext()); System.out.println(actual.toString()); }
{coma}             { actual = new Token(ClaseLexica.COMA, yytext()); System.out.println(actual.toString()); }
{pyc}              { actual = new Token(ClaseLexica.PYC, yytext()); System.out.println(actual.toString()); }
{if}                { actual = new Token(ClaseLexica.IF, yytext()); System.out.println(actual.toString()); }
{else}             { actual = new Token(ClaseLexica.ELSE, yytext()); System.out.println(actual.toString()); }
{while}            { actual = new Token(ClaseLexica.WHILE, yytext()); System.out.println(actual.toString()); }
{int}              { actual = new Token(ClaseLexica.INT, yytext()); System.out.println(actual.toString()); }
{float}            { actual = new Token(ClaseLexica.FLOAT, yytext()); System.out.println(actual.toString()); }
{num}              { actual = new Token(ClaseLexica.NUMERO, yytext()); System.out.println(actual.toString()); }
{id}               { actual = new Token(ClaseLexica.ID, yytext()); System.out.println(actual.toString()); }


