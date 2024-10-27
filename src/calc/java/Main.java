package calc.java;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import calc.jflex.Lexer;  
import calc.byacc.Parser;

public class Main {
    public static void main(String[] args) {
        try {
            // Determinar si se utiliza archivo o entrada estándar y mostrar mensaje adecuado
            Reader reader;
            if (args.length > 0) {
                System.out.println("Leyendo la entrada desde el archivo: " + args[0]);
                reader = new FileReader(args[0]);
            } else {
                System.out.println("Leyendo la entrada desde la terminal. Escribe una expresión y presiona Enter:");
                reader = new InputStreamReader(System.in);
            }
            
            // Crear y ejecutar el parser
            Parser parser = new Parser(reader);
            parser.parse();
            
        } catch (Exception e) {
            System.err.println("Error al ejecutar la calculadora: " + e.getMessage());
            e.printStackTrace(); // Para imprimir el stack trace y entender mejor el error
        }
    }
}

