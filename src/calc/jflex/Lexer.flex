package calc.jflex;

// Importación de clases necesarias
import java.io.Reader;
import calc.byacc.Parser;  // Importa el parser generado con BYACC
import calc.byacc.ParserVal;  // Clase que representa el valor de cada token

%%

%{
    // Declaración de variables y métodos auxiliares del lexer
    private Parser yyparser;  // Instancia del parser que permite comunicarse con él
    public ParserVal yylval;  // Valor del token actual

    // Constructor que recibe un Reader y una instancia del parser
    public Lexer(Reader r, Parser yyparser) {
        this(r);  // Llama al constructor del lexer con el Reader
        this.yyparser = yyparser;  // Asigna el parser a la variable de instancia
    }

    // Método para obtener la línea actual, útil para reportar errores
    public int getLine() { 
        return yyline; 
    }
%}

// Configuraciones del lexer
%public       // Declara la clase como pública
%class Lexer  // Define el nombre de la clase como "Lexer"
%standalone   // Genera un lexer autónomo que puede usarse independientemente
%unicode      // Habilita soporte para caracteres Unicode
%line         // Habilita el seguimiento de líneas en la variable yyline

// Definición de un número entero o decimal
num = ([1-9][0-9]*|0)(\\.[0-9]+)?  // Un número que puede ser entero o decimal

%%
// Regla para reconocer números enteros o decimales
{num} { 
     // Convierte el texto del token a un valor double
	 double value = Double.parseDouble(yytext());
     // Asigna el valor del token actual al parser
     yyparser.setYylval(new ParserVal(value));
    return Parser.NUM;  // Retorna el token NUM, indicando que es un número
}

// Reglas para reconocer operadores matemáticos y paréntesis
"+"          { return Parser.PLUS; }   // Suma
"-"          { return Parser.MINUS; }  // Resta
"*"          { return Parser.TIMES; }  // Multiplicación
"/"          { return Parser.DIV; }    // División
"^"          { return Parser.POW; }    // Potencia
"("          { return Parser.LPAREN; } // Paréntesis izquierdo
")"          { return Parser.RPAREN; } // Paréntesis derecho

// Regla para ignorar espacios en blanco y tabulaciones
[ \t\r]+     { /* Ignorar espacios en blanco */ }

// Regla para manejar saltos de línea y retornar el token NEWLINE
\n           { return Parser.NEWLINE; }  // Retorna un token para el salto de línea

// Regla para manejar el fin de archivo (EOF)
<<EOF>>      { return -1; }      // Retorna -1 para indicar el fin de archivo

// Manejo de errores léxicos para caracteres no reconocidos
.            { System.err.println("Error léxico: " + yytext()); } // Mensaje de error para caracteres inválidos

