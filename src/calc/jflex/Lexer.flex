package calc.jflex;

import java.io.Reader;
import calc.byacc.Parser;
import calc.byacc.ParserVal;

%%

%{
    private Parser yyparser;
    public ParserVal yylval;

    public Lexer(Reader r, Parser yyparser) {
        this(r);
        this.yyparser = yyparser;
    }

    public int getLine() { 
        return yyline; 
    }
%}

%public
%class Lexer
%standalone
%unicode
%line

// Definición de un número entero o decimal
num = ([1-9][0-9]*|0)(\\.[0-9]+)?

%%
// Número entero o decimal
{num} { 
	 double value = Double.parseDouble(yytext());
     yyparser.setYylval(new ParserVal(value));
    return Parser.NUM; 
}

// Operadores
"+"          { return Parser.PLUS; }
"-"          { return Parser.MINUS; }
"*"          { return Parser.TIMES; }
"/"          { return Parser.DIV; }
"^"          { return Parser.POW; }
"("          { return Parser.LPAREN; }
")"          { return Parser.RPAREN; }

// Espacios en blanco y saltos de línea
[ \t\r]+     { /* Ignorar espacios en blanco */ }
\n           { return Parser.NEWLINE; }  // Cambiado para el manejo adecuado del salto de línea

<<EOF>>      { return -1; }      // Indicador de fin de archivo
.            { System.err.println("Error léxico: " + yytext()); } // Notificación de error para caracteres no reconocidos

