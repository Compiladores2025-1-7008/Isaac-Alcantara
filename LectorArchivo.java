import java.io.FileReader;
import java.io.IOException;

public class LectorArchivo {
    private static String yytext = ""; // Lexema actual

    public static char leerCaracter(FileReader fileReader) throws IOException {
        int charCode = fileReader.read();
        return (charCode == -1) ? '\0' : (char) charCode; // Fin de archivo o el siguiente carácter
    }

    public static void yylex(FileReader fileReader) throws IOException {
        yytext = "";        // Inicializamos yytext
        Automata.asignarEstadoActual(0);    // Inicializamos el estado actual

        char c = leerCaracter(fileReader); // Obtenemos el primer carácter

        while (c != '\0') {  // Mientras no lleguemos al final del archivo
            int siguienteEstado = Automata.obtenerSiguienteEstado(Automata.obtenerEstadoActual(), c);

            if (siguienteEstado != -1) {
                Automata.asignarEstadoActual(siguienteEstado);
                yytext += c;
                c = leerCaracter(fileReader); // Obtener el siguiente carácter
            } else {
                if (Automata.esEstadoDeAceptacion(Automata.obtenerEstadoActual())) {
                    if (Automata.obtenerEstadoActual() != 4) {
                        ManejadorTokens.obtenerToken(Automata.obtenerEstadoActual(), yytext); // Si es estado de aceptación, obtenemos el token
                    }
                    yytext = ""; // Reiniciar yytext después de obtener el token
                    Automata.asignarEstadoActual(0); // Volver al estado inicial

                } else {
                    ManejadorTokens.reportarError(c); // Reportar error léxico
                    yytext = ""; // Reiniciar yytext
                    Automata.asignarEstadoActual(0); // Volver al estado inicial
                    c = leerCaracter(fileReader); // Obtener el siguiente carácter
                }
            }
        }
        fileReader.close();
    }
}
