import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static HashMap<String, Integer> transiciones = new HashMap<>(); // Tabla de transiciones
    private static int estadoActual = 0; // Estado inicial

    public static void agregarTransicion(int estado, String regex, int siguienteEstado) {
        String clave = estado + "," + regex;
        transiciones.put(clave, siguienteEstado);
    }

    public static int obtenerSiguienteEstado(int estadoActual, char caracter) {
        for (String clave : transiciones.keySet()) {
            String[] partes = clave.split(",");
            int estado = Integer.parseInt(partes[0]);
            String regex = partes[1];
            Pattern patron = Pattern.compile(regex);
            Matcher matcher = patron.matcher(String.valueOf(caracter));

            if (estado == estadoActual && matcher.matches()) {
                return transiciones.get(clave);
            }
        }
        return -1; // Si no encuentra transición válida
    }

    public static int getEstadoActual() {
        return estadoActual;
    }

    public static void setEstadoActual(int nuevoEstado) {
        estadoActual = nuevoEstado;
    }
}
