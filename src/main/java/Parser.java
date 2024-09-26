package main.java;

import java.io.IOException;
import main.jflex.Lexer;

public class Parser implements ParserInterface {
    private Lexer lexer;
    private int actual;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public void eat(int claseLexica) {
        if(actual == claseLexica) {
            try {
                actual = lexer.yylex();
            } catch (IOException ioe) {
                System.err.println("Failed to read next token");
            }
        }
        else
            System.err.println("Se esperaba el token: "+ actual); 
    }

    public void error(String msg) {
        System.err.println("ERROR DE SINTAXIS: "+msg+" en la línea "+lexer.getLine());
    }

    public void parse() {
        try {
            this.actual = lexer.yylex();
        } catch (IOException ioe) {
            System.err.println("Error: No fue posible obtener el primer token de la entrada.");
            System.exit(1);
        }
        S();
        if(actual == 0) //llegamos al EOF sin error
            System.out.println("La cadena es aceptada");
        else 
            System.out.println("La cadena no pertenece al lenguaje generado TIMES la gramática");
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
        if (actual == ClaseLexica.INT || actual == ClaseLexica.FLOAT) { // Si es un tipo, seguimos con otra declaración.
            declaracion();
            declaracion_prima();
        } 
        // Si no es tipo, epsilon (cadena vacía), no hacemos nada.
    }

    public void declaracion() { // declaracion -> tipo lista ;
        tipo();
        lista();
        eat(ClaseLexica.PYC); // Se espera un punto y coma (;)
    }

    public void tipo() { // tipo -> int | float
        if (actual == ClaseLexica.INT) {
            eat(ClaseLexica.INT); // Consumimos el token "int"
        } else if (actual == ClaseLexica.FLOAT) {
            eat(ClaseLexica.FLOAT); // Consumimos el token "float"
        } else {
            error("Se esperaba un tipo 'int' o 'float'.");
        }
    }

    public void lista() { // lista -> ID lista_prima
        eat(ClaseLexica.ID); // Consumimos el ID
        lista_prima(); // Procesamos el resto de la lista
    }

    public void lista_prima() { // lista_prima -> , ID lista_prima | epsilon
        if (actual == ClaseLexica.COMA) { // Si hay una coma, continuamos con otro ID
            eat(ClaseLexica.COMA); // Consumimos la coma
            eat(ClaseLexica.ID); // Consumimos el siguiente ID
            lista_prima(); // Llamada recursiva para continuar con la lista
        }
        // Si no hay coma, epsilon (cadena vacía), no hacemos nada.
    }


    public void sentencias() { // sentencias -> sentencia sentencia_prima
        sentencia();
        sentencia_prima();
    }
    
    public void sentencia_prima() { // sentencia_prima -> sentencia sentencia_prima | epsilon
        if (actual == ClaseLexica.ID || actual == ClaseLexica.IF || actual == ClaseLexica.WHILE) {
            sentencia();
            sentencia_prima();
        }
        // Si no es un token válido para 'sentencia', epsilon (no hacemos nada).
    }
    
    public void sentencia() { 
        if (actual == ClaseLexica.ID) { // sentencia -> ID = expresion ;
            eat(ClaseLexica.ID); // Consumimos el ID
            eat(ClaseLexica.ASIG); // Consumimos el '='
            expresion(); // Procesamos la expresión
            eat(ClaseLexica.PYC); // Consumimos el ';'
        } else if (actual == ClaseLexica.IF) { // sentencia -> if ( expresion ) sentencias else sentencias
            eat(ClaseLexica.IF); // Consumimos 'if'
            eat(ClaseLexica.LPAR); // Consumimos '('
            expresion(); // Procesamos la condición
            eat(ClaseLexica.RPAR); // Consumimos ')'
            sentencias(); // Procesamos las sentencias del 'if'
            eat(ClaseLexica.ELSE); // Consumimos 'else'
            sentencias(); // Procesamos las sentencias del 'else'
        } else if (actual == ClaseLexica.WHILE) { // sentencia -> while ( expresion ) sentencias
            eat(ClaseLexica.WHILE); // Consumimos 'while'
            eat(ClaseLexica.LPAR); // Consumimos '('
            expresion(); // Procesamos la condición
            eat(ClaseLexica.RPAR); // Consumimos ')'
            sentencias(); // Procesamos el bloque de sentencias dentro del 'while'
        } else {
            error("Se esperaba una sentencia válida");
        }
    }
    
    public void expresion() { // expresion -> expresion_1 expresion_prima
        expresion_1(); 
        expresion_prima(); 
    }
    
    public void expresion_prima() { // expresion_prima -> + expresion_1 expresion_prima | - expresion_1 expresion_prima | epsilon
        if (actual == ClaseLexica.ADD) { // Si hay un '+'
            eat(ClaseLexica.ADD); // Consumimos el '+'
            expresion_1(); // Procesamos la siguiente expresión
            expresion_prima(); // Verificamos si hay más operadores
        } else if (actual == ClaseLexica.MINUS) { // Si hay un '-'
            eat(ClaseLexica.MINUS); // Consumimos el '-'
            expresion_1(); // Procesamos la siguiente expresión
            expresion_prima(); // Verificamos si hay más operadores
        }
        // Si no hay '+', '-' corresponde a epsilon, no hacemos nada.
    }
    
    public void expresion_1() { // expresion_1 -> expresion_2 expresion_doble_prima
        expresion_2(); 
        expresion_doble_prima(); 
    }
    
    public void expresion_doble_prima() { // expresion_doble_prima -> * expresion_2 expresion_doble_prima | / expresion_2 expresion_doble_prima | epsilon
        if (actual == ClaseLexica.TIMES) { // Si hay un '*'
            eat(ClaseLexica.TIMES); // Consumimos el '*'
            expresion_2(); // Procesamos la siguiente expresión
            expresion_doble_prima(); // Verificamos si hay más operadores
        } else if (actual == ClaseLexica.DIV) { // Si hay un '/'
            eat(ClaseLexica.DIV); // Consumimos el '/'
            expresion_2(); // Procesamos la siguiente expresión
            expresion_doble_prima(); // Verificamos si hay más operadores
        }
        // Si no hay '*', '/' corresponde a epsilon, no hacemos nada.
    }
    
    public void expresion_2() { // expresion_2 -> ( expresion ) | ID | numero
        if (actual == ClaseLexica.LPAR) { // Si hay un '('
            eat(ClaseLexica.LPAR); // Consumimos '('
            expresion(); // Procesamos la expresión dentro de los paréntesis
            eat(ClaseLexica.RPAR); // Consumimos ')'
        } else if (actual == ClaseLexica.ID) { // Si es un ID
            eat(ClaseLexica.ID); // Consumimos el ID
        } else if (actual == ClaseLexica.NUM) { // Si es un número
            eat(ClaseLexica.NUM); // Consumimos el número
        } else {
            error("Se esperaba una expresión válida");
        }
    }
    
    /************************************************************************/
    /**                                                                    **/
    /**                       Funciones TIMES cada NT                        **/
    /**                                                                    **/
    /************************************************************************/

}