import java.io.FileReader;
import java.io.IOException;

//Clase utilizada para leer el contenido del archivo caracter por caracter
public class LectorArchivo {
    private static String yytext = ""; // Cadena más larga que actúa como lexema actual

    //Función utilizada para leer el siguiente caracter del archivo hasta hallar el final del mismo
    public static char leerCaracter(FileReader fileReader) throws IOException {
        int charCode = fileReader.read();
        return (charCode == -1) ? '\0' : (char) charCode; // Fin de archivo o el siguiente carácter
    }

    //Función que almacena el la cadena coincidente con alguna expresión del lenguaje más larga (lexema) actual
    public static void yylex(FileReader fileReader) throws IOException {
        yytext = "";        // Inicializamos yytext (almacena el lexema actual)
        Automata.asignarEstadoActual(0);    // Inicializamos el estado actual en 0 (estado inicial del autómata)
    
        char c = leerCaracter(fileReader); // Leemos el primer carácter del archivo
    
        // Este proceso se ejecuta mientras no lleguemos al final del archivo 
        while (c != '\0') {  
            // Obtenemos el siguiente estado según el estado actual y el caracter leído
            int siguienteEstado = Automata.obtenerSiguienteEstado(Automata.obtenerEstadoActual(), c);
    
            // Si se encuentra un siguiente estado válido, se transiciona al mismo y se actualiza el lexema
            if (siguienteEstado != -1) {
                Automata.asignarEstadoActual(siguienteEstado);  
                yytext += c;  // Agregamos el carácter al lexema actual
                c = leerCaracter(fileReader);  
            } else {
               
                if (Automata.esEstadoDeAceptacion(Automata.obtenerEstadoActual())) {
                    //Mostramos el token en pantalla si no es un pesacio
                    if (Automata.obtenerEstadoActual() != 4) { 
                        ManejadorTokens.obtenerToken(Automata.obtenerEstadoActual(), yytext);  
                    }
                    //Reiniciamos el proceso volviendo al estado inicial y vaciando el lexema que se había almacenado
                    yytext = ""; 
                    Automata.asignarEstadoActual(0);  
                } else {
                    // Si el estado actual no es un estado de aceptación, reportamos un error léxico
                    ManejadorTokens.reportarError(c);  
                     //Reiniciamos el proceso volviendo al estado inicial y vaciando el lexema que se había almacenado
                    yytext = ""; 
                    Automata.asignarEstadoActual(0);  
                    c = leerCaracter(fileReader);  
                }
            }
        }
    
        fileReader.close();  // Cerramos el archivo después de terminar la lectura
    }
    
}
