package main.java;

public class Token {
    private ClaseLexica clase;
    private String lexema;

    public Token(ClaseLexica clase, String lexema) {
        this.clase = clase;
        this.lexema = lexema;
    }
    
    public Token(ClaseLexica clase) {
        this.clase = clase;
        this.lexema = "";
    }


    @Override
    public String toString() {
    	if (this.lexema != ""){
        return "<" + this.clase + ", " + this.lexema + ">";
        }
         return "<" + this.clase+">";
    }
}
