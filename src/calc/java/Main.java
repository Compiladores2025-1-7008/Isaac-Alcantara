package calc.java;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import calc.jflex.Lexer;  
import calc.byacc.Parser;

public class Main {
    public static void main(String[] args) {
        try {
            // Selecciona el lector: archivo si se proporciona, o entrada estándar
            Reader reader = (args.length > 0) ? new FileReader(args[0]) : new InputStreamReader(System.in);
            
            // Crear y ejecutar el parser
            Parser parser = new Parser(reader);
            parser.parse();
            
        } catch (Exception e) {
            System.err.println("Error al ejecutar la calculadora: " + e.getMessage());
        }
    }
}
