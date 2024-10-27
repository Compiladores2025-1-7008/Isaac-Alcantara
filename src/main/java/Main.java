package main.java;

import main.byacc.Parser; // Importa la clase Parser para realizar el análisis sintáctico
import java.io.FileReader; // Importa FileReader para leer archivos de texto
import java.io.Reader; // Importa Reader, clase padre de FileReader
import java.io.IOException; // Importa IOException para manejar errores de entrada/salida

public class Main {
    public static void main(String[] args) {
        // Verifica que se haya proporcionado un argumento (nombre de archivo)
        if (args.length == 0) {
            System.out.println("Uso: java Main <nombre_archivo>"); // Mensaje de uso
            return; // Finaliza el programa si no se proporciona archivo
        }

        String archivoEntrada = args[0]; // Asigna el primer argumento a archivoEntrada

        // Intenta abrir y leer el archivo especificado
        try (Reader reader = new FileReader(archivoEntrada)) {
            Parser parser = new Parser(reader); // Crea una instancia de Parser con el lector del archivo
            parser.parse(); // Llama al método parse para iniciar el análisis sintáctico
        } catch (IOException e) { // Captura excepciones de entrada/salida
            System.err.println("Error de lectura del archivo: " + e.getMessage());
        } catch (Exception e) { // Captura otras excepciones durante el análisis sintáctico
            System.err.println("Error durante el análisis sintáctico: " + e.getMessage());
        }
    }
}


