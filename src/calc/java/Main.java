package calc.java;

// Importaciones necesarias
import java.io.FileReader;            // Para leer desde un archivo
import java.io.InputStreamReader;     // Para leer desde la entrada estándar (terminal)
import java.io.Reader;                // Interfaz de lector de caracteres
import calc.jflex.Lexer;              // Importa el lexer
import calc.byacc.Parser;             // Importa el parser

public class Main {
    public static void main(String[] args) {
        try {
            // Determina el origen de la entrada (archivo o terminal)
            Reader reader;
            if (args.length > 0) {
                // Si se pasa un argumento, se asume que es un archivo
                System.out.println("Leyendo la entrada desde el archivo: " + args[0]);
                reader = new FileReader(args[0]);  // Lee desde el archivo especificado en args[0]
            } else {
                // Si no se pasan argumentos, se lee desde la entrada estándar (terminal)
                System.out.println("Leyendo la entrada desde la terminal. Escribe una expresión y presiona Enter:");
                reader = new InputStreamReader(System.in);  // Lee desde la terminal
            }
            
            // Crear una instancia del parser y ejecutar el análisis sintáctico
            Parser parser = new Parser(reader);  // Inicializa el parser con el origen de datos (archivo o terminal)
            parser.parse();                      // Inicia el proceso de parseo y evaluación de expresiones
            
        } catch (Exception e) {
            // Manejo de excepciones generales
            System.err.println("Error al ejecutar la calculadora: " + e.getMessage());
            e.printStackTrace();  // Imprime el stack trace para ayudar en la depuración del error
        }
    }
}

