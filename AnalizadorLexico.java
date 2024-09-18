
import java.io.FileReader;
import java.io.IOException;

public class AnalizadorLexico{
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Debe proporcionar el nombre del archivo como argumento.");
            return;
        }

        try {
            // Inicialización de estados de aceptación
            Automata.agregarEstadoDeAceptacion(1);
            Automata.agregarEstadoDeAceptacion(2);
            Automata.agregarEstadoDeAceptacion(3);
            Automata.agregarEstadoDeAceptacion(4);
            Automata.agregarEstadoDeAceptacion(6);
            Automata.agregarEstadoDeAceptacion(7);

            // Definir las transiciones con expresiones regulares
            Automata.agregarTransicion(0, "[a-z]", 1); // Estado 0 a 1 con letras minúsculas
            Automata.agregarTransicion(0, "[ \\t\\n\\r]", 4); // Estado 0 a 4 con tabulaciones o saltos de línea
            Automata.agregarTransicion(0, "\\+", 6);
            Automata.agregarTransicion(0, ":", 5);
            Automata.agregarTransicion(0, "0", 3);
            Automata.agregarTransicion(0, "[1-9]", 2);
            Automata.agregarTransicion(1, "[a-z]", 1); // Estado 1 a 1 con letras minúsculas
            Automata.agregarTransicion(2, "\\.", 7); // Estado 2 a 7 con cualquier carácter
            Automata.agregarTransicion(2, "[0-9]", 2); // Estado 7 a 7 con dígitos
            Automata.agregarTransicion(3, "\\.", 7); // Estado 3 a 7 con cualquier carácter
            Automata.agregarTransicion(4, "[ \\t\\n\\r]", 4); // Estado 4 a 4 con tabulaciones o saltos de línea
            Automata.agregarTransicion(5, ":", 8); // Estado 5 a 8 con el carácter ':'
            Automata.agregarTransicion(7, "[0-9]", 7); // Estado 7 a 7 con dígitos
            Automata.agregarTransicion(8, "=", 6); // Estado 8 a 6 con el carácter '='

            // Abrir el archivo pasado como argumento y ejecutar la función yylex
            FileReader fileReader = new FileReader(args[0]);
            LectorArchivo.yylex(fileReader);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
