package main.java;

//Clase que representa un Token, es decir
public class Token {
    private ClaseLexica clase; //atributo para la clase léxica del Token
    private String lexema; //atributo para la cadena de caracteres (lexema) del Token

    //Constructor para la clase Token, particularmente para aquellos tokens que no son únicos en su clase léxica
    public Token(ClaseLexica clase, String lexema) {
        this.clase = clase; // clase léxica del token
        this.lexema = lexema; //lexema correspondiente al token
    }
    
     //Constructor para la clase Token, particularmente para aquellos tokens que son únicos en su clase léxica
    public Token(ClaseLexica clase) {
        this.clase = clase;// clase léxica del token
        
    }

    //Sobreescritura del método toString para que se muestre la clase léxica y el lexema asociado
    @Override
    public String toString() {
        //se el lexema es distinto de null, quiere decir que es un token que no resulta único en su clase léxica
    	if (this.lexema != null){
        return "<" + this.clase + ", " + this.lexema + ">";
        }
         return "<" + this.clase+">";
    }
}
