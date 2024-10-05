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
id = [a-zA-Z_][a-zA-Z_0-9]* // macro para reconocer un identificador del lenguaje

%%

{espacio}+ { /* Ignorar espacios */ }

"int" { 
    System.out.println("Encontramos una palabra reservada 'int'");
    actual = new Token(ClaseLexica.INT, yytext());
    return actual.getClaseLexica(); // Retornamos la clase léxica del token
}

"float" { 
    System.out.println("Encontramos una palabra reservada 'float'");
    actual = new Token(ClaseLexica.FLOAT, yytext());
    return actual.getClaseLexica(); 
}

"if" { 
    System.out.println("Encontramos una palabra reservada 'if'");
    actual = new Token(ClaseLexica.IF, yytext());
    return actual.getClaseLexica(); 
}

"else" { 
    System.out.println("Encontramos una palabra reservada 'else'");
    actual = new Token(ClaseLexica.ELSE, yytext());
    return actual.getClaseLexica(); 
}

"while" { 
    System.out.println("Encontramos una palabra reservada 'while'");
    actual = new Token(ClaseLexica.WHILE, yytext());
    return actual.getClaseLexica(); 
}

"(" { 
    System.out.println("Encontramos un paréntesis '('");
    actual = new Token(ClaseLexica.LPAR, yytext());
    return actual.getClaseLexica(); 
}

")" { 
    System.out.println("Encontramos un paréntesis ')'");
    actual = new Token(ClaseLexica.RPAR, yytext());
    return actual.getClaseLexica(); 
}

"=" { 
    System.out.println("Encontramos un operador '='");
    actual = new Token(ClaseLexica.ASIG, yytext());
    return actual.getClaseLexica(); 
}

"+" { 
    System.out.println("Encontramos un operador '+'");
    actual = new Token(ClaseLexica.ADD, yytext());
    return actual.getClaseLexica(); 
}

"-" { 
    System.out.println("Encontramos un operador '-'");
    actual = new Token(ClaseLexica.MINUS, yytext());
    return actual.getClaseLexica(); 
}

"*" { 
    System.out.println("Encontramos un operador '*'");
    actual = new Token(ClaseLexica.TIMES, yytext());
    return actual.getClaseLexica(); 
}

"/" { 
    System.out.println("Encontramos un operador '/'");
    actual = new Token(ClaseLexica.DIV, yytext());
    return actual.getClaseLexica(); 
}

";" { 
    System.out.println("Encontramos un signo de puntuación ';'");
    actual = new Token(ClaseLexica.PYC, yytext());
    return actual.getClaseLexica(); 
}

"," { 
    System.out.println("Encontramos un signo de puntuación ','");
    actual = new Token(ClaseLexica.COMA, yytext());
    return actual.getClaseLexica(); 
}

{id} { 
    System.out.println("Encontramos un identificador: "+ yytext());
    actual = new Token(ClaseLexica.ID, yytext());
    return actual.getClaseLexica(); 
}

{num} { 
    System.out.println("Encontramos un número: "+yytext());
    actual = new Token(ClaseLexica.NUM, yytext());
    return actual.getClaseLexica(); 
}

<<EOF>> { 
    actual = new Token(0, yytext());
    return actual.getClaseLexica(); 
}

. { 
     actual = new Token(-1, yytext());
    return actual.getClaseLexica(); 
}
