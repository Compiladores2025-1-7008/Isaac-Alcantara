import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

//Clase que representa el autómata utilizado para saber si una cadena pertenece al lenguaje o no
public class Automata {
    private static HashMap<String, Integer> transiciones = new HashMap<>(); // Tabla de transiciones 
    private static int estadoActual = 0; // Estado inicial
    private static ArrayList<Integer> estadosDeAceptacion = new ArrayList<>(); // Estados de aceptación

    //Función para agregar a la lista aquellos estados que sean de aceptación
    public static void agregarEstadoDeAceptacion(int estado) {
        estadosDeAceptacion.add(estado);
    }

    //Funciṕn para saber si un estado es de aceptación
    public static boolean esEstadoDeAceptacion(int estado) {
        return estadosDeAceptacion.contains(estado);
    }

    //Función para agregar una transición al autómata, recibe una expresión regular en vez de un caracter 
    //debido a que se usan rangos de caracteres para algunas transiciones
    public static void agregarTransicion(int estado, String regex, int siguienteEstado) {
        String clave = estado + "," + regex;
        transiciones.put(clave, siguienteEstado);
    }

    // Función para obtener el siguiente estado desde la tabla de transiciones utilizando el estado actual y el carácter actual
// Si no hay una transición válida, devuelve -1
public static int obtenerSiguienteEstado(int estadoActual, char caracter) {

    // Recorremos todas las claves de la tabla de transiciones (las claves tienen el formato "estado,regex")
    for (String clave : transiciones.keySet()) {

        // Separamos la clave en dos partes: el estado y la expresión regular
        String[] partes = clave.split(",");
        int estado = Integer.parseInt(partes[0]); 
        String regex = partes[1]; 

        // Volvemos la expresión regular en un patrón
        Pattern patron = Pattern.compile(regex);
        // Comparamos el caracter actual con la expresión regular usando un Matcher
        Matcher matcher = patron.matcher(String.valueOf(caracter));

        // Si el estado actual coincide con el estado de la clave y el carácter cumple con la expresión regular,
        // entonces retornamos el siguiente estado almacenado en la tabla de transiciones
        if (estado == estadoActual && matcher.matches()) {
            return transiciones.get(clave); 
        }
    }

    // Si no se encuentra una transición válida, devolvemos -1 como indicador de error
    return -1; 
}

    
    public static int obtenerEstadoActual() {
        return estadoActual;
    }

    public static void asignarEstadoActual(int nuevoEstado) {
        estadoActual = nuevoEstado;
    }
}
