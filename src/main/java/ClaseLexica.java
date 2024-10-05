package main.java;

// Clase que contiene las constantes para cada clase léxica (o token).
// Estas constantes son utilizadas por el analizador léxico (lexer) y el parser para identificar tipos de tokens.

public class ClaseLexica {
    
    // Constante que representa el token 'int', utilizado para declarar variables de tipo entero.
    public static final int INT = 1;
    
    // Constante que representa el token 'float', utilizado para declarar variables de tipo flotante.
    public static final int FLOAT = 2;
    
    // Constante que representa el token 'if', utilizado en estructuras de control condicional.
    public static final int IF = 3;
    
    // Constante que representa el token 'else', utilizado en conjunto con 'if' para estructuras de control condicional.
    public static final int ELSE = 4;
    
    // Constante que representa el token 'while', utilizado para declarar bucles condicionales.
    public static final int WHILE = 5;
    
    // Constante que representa el token '(' (paréntesis izquierdo), utilizado en expresiones y estructuras de control.
    public static final int LPAR = 6;
    
    // Constante que representa el token ')' (paréntesis derecho), utilizado en expresiones y estructuras de control.
    public static final int RPAR = 7;
    
    // Constante que representa el token '=' (asignación), utilizado para asignar valores a variables.
    public static final int ASIG = 8;
    
    // Constante que representa el token '+' (suma), utilizado en expresiones aritméticas.
    public static final int ADD = 9;
    
    // Constante que representa el token '-' (resta), utilizado en expresiones aritméticas.
    public static final int MINUS = 10;
    
    // Constante que representa el token '*' (multiplicación), utilizado en expresiones aritméticas.
    public static final int TIMES = 11;
    
    // Constante que representa el token '/' (división), utilizado en expresiones aritméticas.
    public static final int DIV = 12;
    
    // Constante que representa el token ';' (punto y coma), utilizado para terminar declaraciones en el código.
    public static final int PYC = 13;
    
    // Constante que representa el token ',' (coma), utilizado para separar elementos en listas o declaraciones múltiples.
    public static final int COMA = 14;
    
    // Constante que representa el token para identificadores, que pueden ser nombres de variables, funciones, etc.
    public static final int ID = 15;
    
    // Constante que representa el token para números, ya sea enteros o flotantes.
    public static final int NUM = 16;

}
