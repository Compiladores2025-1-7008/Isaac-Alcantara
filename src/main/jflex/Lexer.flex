/**
 * Escáner que detecta un lenguaje que posee números, palabras reservadas, identificadores, espacios y signos de puntación
 * (algunos de esos signos incluyen coma, punto y coma, paréntesis, etcétera)
*/

package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;

%%

%{
//Variable global que sirve para almacenar y presentar el Token actual
public Token actual;

%}

%public
%class Lexer
%standalone
%unicode

espacio=[ \t\n] //expresión regular para reconocer los espacios
if = "if" //expresión regular para reconocer la palabra reservada 'if'
else = "else" //expresión regular para reconocer la palabra reservada 'else'
while = "while" //expresión regular para reconocer la palabra reservada 'while'
int = "int" //expresión regular para reconocer la palabra reservada 'int'
float = "float" //expresión regular para reconocer la palabra reservada 'float'
coma = "," //expresión regular para reconocer una coma
pyc = ";" //expresión regular para reconocer un punto y coma
lpar = "(" //expresión regular para reconocer un paréntesis izquierdo
rpar = ")" //expresión regular para reconocer un paréntesis derecho
ent = [0-9]+ //expresión regular para reconocer números enteros
decimal = {ent} \.{ent}* //expresión regular para reconocer números decimales o flotantes
notcient = {ent} \.?{ent}* ([eE][+-]? {ent})? //expresión regular para reconocer números en notación científica


num = {ent} | {decimal} | {notcient} //macro para reconocer un número dle lenguaje
id = [a-zA-Z_][a-zA-Z_0-9]* //macro con expresión regular para reconocer una identificador del lenguaje


%%

/**
 * Acciones léxicas a llevar a cabo para cada macro definida
 * se usan distintos constructores de la clase Token dependiendo de si el la clase léxica tiene un único elemento o no
*/
{espacio} {/* La acción léxica puede ir vacía si queremos que el escáner ignore la regla */}

{lpar}             { actual = new Token(ClaseLexica.LPAR); System.out.println(actual.toString()); }
{rpar}             { actual = new Token(ClaseLexica.RPAR); System.out.println(actual.toString()); }
{coma}             { actual = new Token(ClaseLexica.COMA); System.out.println(actual.toString()); }
{pyc}              { actual = new Token(ClaseLexica.PYC); System.out.println(actual.toString()); }
{if}               { actual = new Token(ClaseLexica.IF); System.out.println(actual.toString()); }
{else}             { actual = new Token(ClaseLexica.ELSE); System.out.println(actual.toString()); }
{while}            { actual = new Token(ClaseLexica.WHILE); System.out.println(actual.toString()); }
{int}              { actual = new Token(ClaseLexica.INT); System.out.println(actual.toString()); }
{float}            { actual = new Token(ClaseLexica.FLOAT); System.out.println(actual.toString()); }
{num}              { actual = new Token(ClaseLexica.NUMERO, yytext()); System.out.println(actual.toString()); }
{id}               { actual = new Token(ClaseLexica.ID, yytext()); System.out.println(actual.toString()); }
.                  { System.out.println("Error léxico: " + yytext());}


