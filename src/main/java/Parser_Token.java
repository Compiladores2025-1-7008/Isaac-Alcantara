package main.java;

import java.io.IOException;
import main.jflex.Lexer;


public class Parser_Token implements ParserInterface {
    private Lexer lexer;

    public Parser_Token(Lexer lexer) {
        this.lexer = lexer;
    }

    public void eat(int claseLexica) {
        if (lexer.actual.getClaseLexica() == claseLexica) { // Comparamos la clase léxica del token actual
            try {
                lexer.yylex(); // Obtenemos el siguiente token, que actualizará lexer.actual.getClaseLexica()
            } catch (IOException ioe) {
                System.err.println("Failed to read next token");
            }
        } else {
            error("Se esperaba el token: " + claseLexica + " pero se encontró: " + lexer.actual.getClaseLexica() + " (" + lexer.actual.getLexema() + ")");
        }
    }

    public void error(String msg) {
        System.err.println("ERROR SINTÁCTICO: " + msg + " en la línea " + lexer.getLine());
        System.exit(1); // Para detener el proceso en caso de error de sintaxis
    }

    public void parse() {
        try {
            lexer.yylex(); // Obtenemos el primer token, esto actualiza lexer.actual.getClaseLexica()
        } catch (IOException ioe) {
            System.err.println("Error: No fue posible obtener el primer token de la entrada.");
            System.exit(1);
        }
        S(); // Inicia el análisis sintáctico
        if (lexer.actual.getClaseLexica() == 0) { // Si llegamos al EOF sin error
            System.out.println("La cadena es aceptada");
        } else {
            error("Se esperaba el final del archivo");
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
        if (lexer.actual.getClaseLexica() == ClaseLexica.INT || lexer.actual.getClaseLexica() == ClaseLexica.FLOAT) { // Si es un tipo, seguimos con otra declaración
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
        if (lexer.actual.getClaseLexica() == ClaseLexica.INT) {
            eat(ClaseLexica.INT); // Consumimos el token "int"
        } else if (lexer.actual.getClaseLexica() == ClaseLexica.FLOAT) {
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
        if (lexer.actual.getClaseLexica() == ClaseLexica.COMA) { // Si hay una coma, continuamos con otro ID
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
        if (lexer.actual.getClaseLexica() == ClaseLexica.ID || lexer.actual.getClaseLexica() == ClaseLexica.IF || lexer.actual.getClaseLexica() == ClaseLexica.WHILE) {
            sentencia();
            sentencia_prima();
        }
        // Si no es un token válido para 'sentencia', epsilon (no hacemos nada).
    }

    public void sentencia() { 
        if (lexer.actual.getClaseLexica() == ClaseLexica.ID) { // sentencia -> ID = expresion ;
            eat(ClaseLexica.ID); // Consumimos el ID
            eat(ClaseLexica.ASIG); // Consumimos el '='
            expresion(); // Procesamos la expresión
            eat(ClaseLexica.PYC); // Consumimos el ';'
        } else if (lexer.actual.getClaseLexica() == ClaseLexica.IF) { // sentencia -> if ( expresion ) sentencias else sentencias
            eat(ClaseLexica.IF); // Consumimos 'if'
            eat(ClaseLexica.LPAR); // Consumimos '('
            expresion(); // Procesamos la condición
            eat(ClaseLexica.RPAR); // Consumimos ')'
            sentencias(); // Procesamos las sentencias del 'if'
            eat(ClaseLexica.ELSE); // Consumimos 'else'
            sentencias(); // Procesamos las sentencias del 'else'
        } else if (lexer.actual.getClaseLexica() == ClaseLexica.WHILE) { // sentencia -> while ( expresion ) sentencias
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
        if (lexer.actual.getClaseLexica() == ClaseLexica.ADD) { // Si hay un '+'
            eat(ClaseLexica.ADD); // Consumimos el '+'
            expresion_1(); // Procesamos la siguiente expresión
            expresion_prima(); // Verificamos si hay más operadores
        } else if (lexer.actual.getClaseLexica() == ClaseLexica.MINUS) { // Si hay un '-'
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
        if (lexer.actual.getClaseLexica() == ClaseLexica.TIMES) { // Si hay un '*'
            eat(ClaseLexica.TIMES); // Consumimos el '*'
            expresion_2(); // Procesamos la siguiente expresión
            expresion_doble_prima(); // Verificamos si hay más operadores
        } else if (lexer.actual.getClaseLexica() == ClaseLexica.DIV) { // Si hay un '/'
            eat(ClaseLexica.DIV); // Consumimos el '/'
            expresion_2(); // Procesamos la siguiente expresión
            expresion_doble_prima(); // Verificamos si hay más operadores
        }
        // Si no hay '*', '/' corresponde a epsilon, no hacemos nada.
    }

    public void expresion_2() { // expresion_2 -> ( expresion ) | ID | numero
        if (lexer.actual.getClaseLexica() == ClaseLexica.LPAR) { // Si hay un '('
            eat(ClaseLexica.LPAR); // Consumimos '('
            expresion(); // Procesamos la expresión dentro de los paréntesis
            eat(ClaseLexica.RPAR); // Consumimos ')'
        } else if (lexer.actual.getClaseLexica() == ClaseLexica.ID) { // Si es un ID
            eat(ClaseLexica.ID); // Consumimos el ID
        } else if (lexer.actual.getClaseLexica() == ClaseLexica.NUM) { // Si es un número
            eat(ClaseLexica.NUM); // Consumimos el número
        } else {
            error("Se esperaba una expresión válida");
        }
    }
}
