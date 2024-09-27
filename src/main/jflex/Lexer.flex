package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;

%%

%{

public Token token_actual;
public int getLine() { return yyline; }

%}

%public
%class Lexer
%standalone
%unicode
%line

espacio=[ \t\n]



num = [0-9]+ 
id = [a-zA-Z_][a-zA-Z_0-9]* //macro con expresión regular para reconocer una identificador del lenguaje


%%

{espacio}+ {}
"int" {  token_actual = new Token(ClaseLexica.INT, yytext()); System.out.println("Encontramos una palabra reservada: "+ token_actual);return token_actual.getClaseLexica();}
"float" {  token_actual = new Token(ClaseLexica.FLOAT, yytext()); System.out.println("Encontramos una palabra reservada"+ token_actual);return token_actual.getClaseLexica();}
"if" {token_actual = new Token(ClaseLexica.IF, yytext()); System.out.println("Encontramos una palabra reservada: " +token_actual);  return token_actual.getClaseLexica();}
"else" { System.out.println("Encontramos una palabra reservada: "); token_actual = new Token(ClaseLexica.ELSE, yytext()); return token_actual.getClaseLexica();}
"while" { token_actual = new Token(ClaseLexica.WHILE, yytext());System.out.println("Encontramos una palabra reservada: " +token_actual);  return token_actual.getClaseLexica();}
"(" { token_actual = new Token(ClaseLexica.LPAR, yytext()); System.out.println("Encontramos un paréntesis: "+token_actual);  return token_actual.getClaseLexica();}
")" {  token_actual = new Token(ClaseLexica.RPAR, yytext()); System.out.println("Encontramos un paréntesis: "+token_actual); return token_actual.getClaseLexica();}
"=" { token_actual = new Token(ClaseLexica.ASIG, yytext());  System.out.println("Encontramos un operador: "+token_actual); return token_actual.getClaseLexica();}
"+" { token_actual = new Token(ClaseLexica.ADD, yytext()); System.out.println("Encontramos un operador: "+token_actual);  return token_actual.getClaseLexica();}
"-" { token_actual = new Token(ClaseLexica.MINUS, yytext()); System.out.println("Encontramos un operador: "+token_actual); return token_actual.getClaseLexica(); }
"*" { token_actual = new Token(ClaseLexica.TIMES, yytext()); System.out.println("Encontramos un operador: "+token_actual); return token_actual.getClaseLexica();}
"/" { token_actual = new Token(ClaseLexica.DIV, yytext()); System.out.println("Encontramos un operador: "+token_actual); return token_actual.getClaseLexica();}
";" { token_actual = new Token(ClaseLexica.PYC, yytext()); System.out.println("Encontramos un signo de puntuación: "+token_actual); return token_actual.getClaseLexica();}
"," { token_actual = new Token(ClaseLexica.COMA, yytext()); System.out.println("Encontramos un signo de puntuación: "+token_actual); return token_actual.getClaseLexica();}
{id} { token_actual = new Token(ClaseLexica.ID, yytext());System.out.println("Encontramos un identificador: "+token_actual);  return token_actual.getClaseLexica();}
{num} {  token_actual = new Token(ClaseLexica.NUM, yytext());System.out.println("Encontramos un número: "+token_actual); return token_actual.getClaseLexica();}
//Aquí el resto de las definiciones
<<EOF>> { token_actual = new Token(0, yytext());  return token_actual.getClaseLexica();}
. { token_actual = new Token(-1, yytext()); return token_actual.getClaseLexica();}
