package main.java;

//Definiciones de las clases léxicas a las cuales puede pertenecer un token
public enum ClaseLexica {

    LPAR,    // Parentesis izquierdo
    RPAR,    // Parentesis derecho
    COMA,     // Coma
    PYC, // Punto y coma 
    ID,       // Identificador
    IF,       // Palabra reservada 'if'
    ELSE,     // Palabra reservada 'else'
    WHILE,    // Palabra reservada 'while'
    INT,      // Palabra reservada 'int'
    FLOAT,    // Palabra reservada 'float'
    NUMERO // Numero flotante (o en notación cientifica)


}