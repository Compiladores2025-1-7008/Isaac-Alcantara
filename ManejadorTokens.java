
//Clase que manejará los Tokens obtenidos a partir del análisis léxico
public class ManejadorTokens {
   
    //Se obtiene la clase léxica del token de acuerdo al estado de en el que terminó su procesamiento
    public static String obtenerClaseLexica(int estado) {
        switch (estado) {
            case 1:
                return "ID";
            case 2:
            case 3:
                return "INT";
            case 4:
                return "ESP";
            case 6:
                return "OP";
            case 7:
                return "FLOAT";
            default:
                return "UNKNOWN";
        }
    }

    //Para obtener el token en terminal, requerimos su clase léxica y el valor del mismo (el lexema asociado)
    public static void obtenerToken(int estado, String yytext) {
        String claseLexica = obtenerClaseLexica(estado);
        System.out.println("<" + claseLexica + ", " + yytext + ">");
    }

    //Si se halla un caracter que no forme parte del lenguaje, entonces se reporta un error
    public static void reportarError(char c) {
        System.out.println("Error léxico para el caracter: " + c);
    }
}
