import java.io.FileReader;
import java.io.IOException;

public class LectorArchivo {
    private static String yytext = ""; // Lexema actual

    public static char getChar(FileReader fileReader) throws IOException {
        int charCode = fileReader.read();
        return (charCode == -1) ? '\0' : (char) charCode; // Fin de archivo o el siguiente carácter
    }

    public static void yylex(FileReader fileReader) throws IOException {
        yytext = "";        // Inicializamos yytext
        Lexer.setEstadoActual(0);    // Inicializamos el estado actual

        char c = getChar(fileReader); // Obtenemos el primer carácter

        while (c != '\0') {  // Mientras no lleguemos al final del archivo
            int siguienteEstado = Lexer.obtenerSiguienteEstado(Lexer.getEstadoActual(), c);

            if (siguienteEstado != -1) {
                Lexer.setEstadoActual(siguienteEstado);
                yytext += c;
                c = getChar(fileReader); // Obtener el siguiente carácter
            } else {
                if (TokenHandler.esEstadoDeAceptacion(Lexer.getEstadoActual())) {
                    if (Lexer.getEstadoActual() != 4) {
                        TokenHandler.getToken(Lexer.getEstadoActual(), yytext); // Si es estado de aceptación, obtenemos el token
                    }
                    yytext = ""; // Reiniciar yytext después de obtener el token
                    Lexer.setEstadoActual(0); // Volver al estado inicial

                } else {
                    TokenHandler.reportarError(c); // Reportar error léxico
                    yytext = ""; // Reiniciar yytext
                    Lexer.setEstadoActual(0); // Volver al estado inicial
                    c = getChar(fileReader); // Obtener el siguiente carácter
                }
            }
        }
        fileReader.close();
    }
}
