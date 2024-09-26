package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;

%%

%{

public Token actual;
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
"int" { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.INT; }
"float" { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.FLOAT; }
"if" { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.IF; }
"else" { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.ELSE; }
"while" { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.WHILE; }
"(" { System.out.println("Encontramos un paréntesis"); return ClaseLexica.LPAR; }
")" { System.out.println("Encontramos un paréntesis"); return ClaseLexica.RPAR; }
"=" { System.out.println("Encontramos un operador"); return ClaseLexica.ASIG; }
"+" { System.out.println("Encontramos un operador"); return ClaseLexica.ADD; }
"-" { System.out.println("Encontramos un operador"); return ClaseLexica.MINUS; }
"*" { System.out.println("Encontramos un operador"); return ClaseLexica.TIMES; }
"/" { System.out.println("Encontramos un operador"); return ClaseLexica.DIV; }
";" { System.out.println("Encontramos un signo de puntuación"); return ClaseLexica.PYC; }
"," { System.out.println("Encontramos un signo de puntuación"); return ClaseLexica.COMA; }
{id} { System.out.println("Encontramos un identificador"); return ClaseLexica.ID; }
{num} { System.out.println("Encontramos un número"); return ClaseLexica.NUM; }
//Aquí el resto de las definiciones
<<EOF>> { return 0; }
. { return -1; }
