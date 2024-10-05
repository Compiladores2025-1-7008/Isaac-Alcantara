package main.java;

// Clase que representa un token, el cual tiene una clase léxica y un lexema asociado.

public class Token {
    
    // Atributo que almacena la clase léxica del token, representada por un número entero.
    private int clase;
    
    // Atributo que almacena el lexema del token, es decir, la secuencia de caracteres que constituye el token.
    private String lexema;

    // Constructor de la clase Token. Recibe la clase léxica y el lexema como parámetros.
    public Token(int clase, String lexema) {
        this.clase = clase;
        this.lexema = lexema;
    }

    // Método para obtener la clase léxica del token.
    public int getClaseLexica() {
        return clase;
    }

    // Método para obtener el lexema del token.
    public String getLexema() {
        return lexema;
    }

    // Método sobreescrito para representar el token como una cadena en formato <clase,lexema>.
    @Override
    public String toString() {
        return "<" + this.clase + "," + this.lexema + ">";
    }
}
