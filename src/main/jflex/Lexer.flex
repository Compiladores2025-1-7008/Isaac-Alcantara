package main.jflex;

import main.byacc.Parser; // Importa la clase Parser para utilizar las definiciones de tokens y métodos de manejo de valores
import main.byacc.ParserVal; // Importa ParserVal para almacenar valores de tokens
import java.io.Reader;

%%

%{
  private Parser yyparser; // Declara una referencia al parser para pasarle tokens y valores

  // Constructor de Lexer, recibe un Reader y una referencia al parser
  public Lexer(Reader r, Parser yyparser) {
    this(r); // Llama al constructor de la superclase con el Reader
    this.yyparser = yyparser; // Asigna el parser pasado al campo yyparser
  }

  public int getLine() { return yyline; } // Método que devuelve la línea actual de análisis
%}

%public
%class Lexer // Define la clase Lexer como pública
%unicode // Soporte para Unicode
%standalone // Define el lexer como independiente
%line // Activa el seguimiento de la línea actual en el lexer

// Definiciones de patrones de token
digit = [0-9] // Define un dígito como un número entre 0 y 9
letter = [_a-zA-Z] // Define una letra como un carácter alfabético o un guion bajo
num = {digit}+"."{digit}+ | {digit}+ // Define un número como una secuencia de dígitos con o sin punto decimal
var = {letter}({letter}|{digit})* // Define una variable como una letra seguida de letras o dígitos

%%

"var"          { // Coincide con la palabra clave "var"
                      System.out.println("Token: VAR_KEYWORD"); // Imprime el tipo de token
                      return Parser.VAR_KEYWORD; // Retorna el token VAR_KEYWORD al parser
		}

{num}           { // Coincide con el patrón de números definidos previamente
                      double value = Double.parseDouble(yytext()); // Convierte el texto del token en un valor numérico
                      System.out.println("Token: NUM, Valor: " + value); // Imprime el token y su valor
                      yyparser.setYylval(new ParserVal(value)); // Almacena el valor en el parser
                      return Parser.NUM; // Retorna el token NUM al parser
                   }

{var}              { // Coincide con el patrón de variables
                      String value = yytext(); // Obtiene el valor de texto de la variable
                      System.out.println("Token: VAR, Valor: " + value); // Imprime el token y su valor
                      yyparser.setYylval(new ParserVal(value)); // Almacena el valor en el parser
                      return Parser.VAR; // Retorna el token VAR al parser
                   }

"="                 { // Coincide con el operador de asignación
                      System.out.println("Token: ASIG"); // Imprime el tipo de token
                      return Parser.ASIG; // Retorna el token ASIG al parser
                   }
"+"                 { // Coincide con el operador de suma
                      System.out.println("Token: PLUS");
                      return Parser.PLUS;
                   }
"-"                 { // Coincide con el operador de resta
                      System.out.println("Token: MINUS");
                      return Parser.MINUS;
                   }
"*"                 { // Coincide con el operador de multiplicación
                      System.out.println("Token: TIMES");
                      return Parser.TIMES;
                   }
"/"                 { // Coincide con el operador de división
                      System.out.println("Token: DIV");
                      return Parser.DIV;
                   }
"("                 { // Coincide con el paréntesis izquierdo
                      System.out.println("Token: LPAREN");
                      return Parser.LPAREN;
                   }
")"                 { // Coincide con el paréntesis derecho
                      System.out.println("Token: RPAREN");
                      return Parser.RPAREN;
                   }

[ \t\n\r\f]+        { /* Ignora espacios en blanco */ } // Ignora espacios, tabulaciones y saltos de línea

<<EOF>>             { return 0; } // Retorna 0 al alcanzar el final de archivo

.                   { return -1; } // Retorna -1 para cualquier otro carácter no reconocido

