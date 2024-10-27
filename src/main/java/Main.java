package main.java;

import main.byacc.Parser;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java Main <nombre_archivo>");
            return;
        }

        String archivoEntrada = args[0];
        
        try (Reader reader = new FileReader(archivoEntrada)) {
            Parser parser = new Parser(reader);
            parser.parse();
        } catch (IOException e) {
            System.err.println("Error de lectura del archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error durante el análisis sintáctico: " + e.getMessage());
        }
    }
}

