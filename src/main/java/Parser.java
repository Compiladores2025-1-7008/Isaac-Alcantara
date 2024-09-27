package main.java;

import java.io.IOException;
import main.jflex.Lexer;

public class Parser implements ParserInterface {
    private Lexer lexer;
    private Token actual;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public void eat(Token siguiente) {
        System.out.println("TOKEN ACTUAL: "+ actual);
        if(actual.getClaseLexica() == siguiente.getClaseLexica()) {
            try {
                actual = new Token(lexer.yylex(), lexer.yytext());
            } catch (IOException ioe) {
                System.err.println("Failed to read next token");
            }
        } else if(actual.getClaseLexica()==-1) {
            error("cadena no perteneciente al lenguaje");
            
        }else{
            System.err.println("Se esperaba el token: "+ actual); 
        }
    }

    public void error(String msg) {
        System.err.println("ERROR DE SINTAXIS: " + msg + " en la línea " + lexer.getLine());
        System.exit(1); // Detenemos el programa en caso de error.
    }

    public void parse() {
        try {
            this.actual = new Token(lexer.yylex(), lexer.yytext()); // Obtener el primer token
            if (actual == null) {
                throw new Exception("No se pudo obtener el primer token.");
            }
        } catch (Exception ioe) {
            System.err.println("Error: No fue posible obtener el primer token de la entrada.");
            System.exit(1);
        }
        S();
        if (actual.getClaseLexica() == 0) { // Revisamos el fin de archivo (EOF)
            System.out.println("La cadena es aceptada");
        } else {
            System.out.println("La cadena no pertenece al lenguaje generado por la gramática");
        }
    }

    public void S() { // S -> declaraciones sentencias
        declaraciones();
        sentencias();
    }

    public void declaraciones() { // declaraciones -> declaracion declaracion_prima
        declaracion();
        declaracion_prima();
    }

    public void declaracion_prima() { // declaracion_prima -> declaracion declaracion_prima | epsilon
        if (actual.getClaseLexica() == ClaseLexica.INT || actual.getClaseLexica() == ClaseLexica.FLOAT) { // Si es un tipo, seguimos con otra declaración.
            declaracion();
            declaracion_prima();
        }
        // Si no es tipo, es epsilon, no hacemos nada.
    }

    public void declaracion() { // declaracion -> tipo lista_var ;
        tipo();
        lista_var();
        eat(new Token(ClaseLexica.PYC, ";")); // Se espera un punto y coma (;)
    }

    public void tipo() { // tipo -> int | float
        if (actual.getClaseLexica() == ClaseLexica.INT) {
            eat(new Token(ClaseLexica.INT, "int")); // Consumimos el token "int"
        } else if (actual.getClaseLexica() == ClaseLexica.FLOAT) {
            eat(new Token(ClaseLexica.FLOAT, "float")); // Consumimos el token "float"
        } 
    }

    public void lista_var() { // lista_var -> ID lista_var'
    if (actual.getClaseLexica() == ClaseLexica.ID) {
        eat(new Token(ClaseLexica.ID, lexer.yytext())); // Consumimos el ID
    }else{
        error("ERROR");
    }
       
        lista_var_prima(); // Procesamos el resto de la lista
    }

    public void lista_var_prima() { // lista_var' -> , ID lista_var' | epsilon
        if (actual.getClaseLexica() == ClaseLexica.COMA) { // Si hay una coma, continuamos con otro ID
            eat(new Token(ClaseLexica.COMA, ",")); // Consumimos la coma
            eat(new Token(ClaseLexica.ID, lexer.yytext())); // Consumimos el siguiente ID
            lista_var_prima(); // Llamada recursiva para continuar con la lista
        }
        // Si no hay coma, es epsilon (cadena vacía), no hacemos nada.
    }

    public void sentencias() { // sentencias -> sentencia sentencias'
        sentencia();
        sentencia_prima();
    }

    public void sentencia_prima() { // sentencias' -> sentencia sentencia' | epsilon
        if (actual.getClaseLexica() == ClaseLexica.ID || actual.getClaseLexica() == ClaseLexica.IF || actual.getClaseLexica() == ClaseLexica.WHILE) {
            sentencia();
            sentencia_prima();
        }
        // Si no es un token válido para 'sentencia', epsilon (no hacemos nada).
    }

    public void sentencia() {
        if (actual.getClaseLexica() == ClaseLexica.ID) { // sentencia -> ID = expresion ;
            eat(new Token(ClaseLexica.ID, lexer.yytext())); // Consumimos el ID
            eat(new Token(ClaseLexica.ASIG, "=")); // Consumimos el '='
            expresion(); // Procesamos la expresión
            eat(new Token(ClaseLexica.PYC, ";")); // Consumimos el ';'
        } else if (actual.getClaseLexica() == ClaseLexica.IF) { // sentencia -> if ( expresion ) sentencias else sentencias
            eat(new Token(ClaseLexica.IF, "if")); // Consumimos 'if'
            eat(new Token(ClaseLexica.LPAR, "(")); // Consumimos '('
            expresion(); // Procesamos la condición
            eat(new Token(ClaseLexica.RPAR, ")")); // Consumimos ')'
            sentencias(); // Procesamos las sentencias del 'if'
            eat(new Token(ClaseLexica.ELSE, "else")); // Consumimos 'else'
            sentencias(); // Procesamos las sentencias del 'else'
        } else if (actual.getClaseLexica() == ClaseLexica.WHILE) { // sentencia -> while ( expresion ) sentencias
            eat(new Token(ClaseLexica.WHILE, "while")); // Consumimos 'while'
            eat(new Token(ClaseLexica.LPAR, "(")); // Consumimos '('
            expresion(); // Procesamos la condición
            eat(new Token(ClaseLexica.RPAR, ")")); // Consumimos ')'
            sentencias(); // Procesamos el bloque de sentencias dentro del 'while'
        } 
    }

    public void expresion() { // expresion -> expresion_1 expresion'
        expresion_1();
        expresion_prima();
    }

    public void expresion_prima() { // expresion' -> + expresion_1 expresion' | - expresion_1 expresion' | epsilon
        if (actual.getClaseLexica() == ClaseLexica.ADD) {
            eat(new Token(ClaseLexica.ADD, "+")); // Consumimos '+'
            expresion_1();
            expresion_prima();
        } else if (actual.getClaseLexica() == ClaseLexica.MINUS) {
            eat(new Token(ClaseLexica.MINUS, "-")); // Consumimos '-'
            expresion_1();
            expresion_prima();
        }
        // Si no hay '+', '-' corresponde a epsilon, no hacemos nada.
    }

    public void expresion_1() { // expresion_1 -> expresion_2 expresion''
        expresion_2();
        expresion_doble_prima();
    }

    public void expresion_doble_prima() { // expresion'' -> * expresion_2 expresion'' | / expresion_2 expresion'' | epsilon
        if (actual.getClaseLexica() == ClaseLexica.TIMES) {
            eat(new Token(ClaseLexica.TIMES, "*")); // Consumimos '*'
            expresion_2();
            expresion_doble_prima();
        } else if (actual.getClaseLexica() == ClaseLexica.DIV) {
            eat(new Token(ClaseLexica.DIV, "/")); // Consumimos '/'
            expresion_2();
            expresion_doble_prima();
        }
        // Si no hay '*', '/', es epsilon.
    }

    public void expresion_2() { // expresion_2 -> ( expresion ) | ID | numero
        if (actual.getClaseLexica() == ClaseLexica.LPAR) {
            eat(new Token(ClaseLexica.LPAR, "(")); // Consumimos '('
            expresion(); // Procesamos la expresión dentro de los paréntesis
            eat(new Token(ClaseLexica.RPAR, ")")); // Consumimos ')'
        } else if (actual.getClaseLexica() == ClaseLexica.ID) {
            eat(new Token(ClaseLexica.ID, lexer.yytext())); // Consumimos el ID
        } else if (actual.getClaseLexica() == ClaseLexica.NUM) {
            eat(new Token(ClaseLexica.NUM, lexer.yytext())); // Consumimos el número
        } 
    }
}
