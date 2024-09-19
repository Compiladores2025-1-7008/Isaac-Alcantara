<p  align="center">
  <img  width="200"  src="https://www.fciencias.unam.mx/sites/default/files/logoFC_2.png"  alt="">  <br>Compiladores  2025-1 <br>
  Práctica 1: Analizadores léxicos con Lex (JFlex) <br> Profesora: Ariel Adara Ulises Mercado Martínez
</p>

## Analizador léxico en Java


### Introducción
Se desarrolló un analizador léxico en Java de acuerdo a un lenguaje dado por la profesora que se compone de las siguientes expresiones regulares.

-id -> ([a-z])⁺

-ent -> ([1-9](0-9)^* | 0)

-real -> ([1-9](0-9)^* | 0).([0-9])^*

-esp -> ([ \t\n\v\r])⁺

-op -> ::= | +

#### Estructura del Analizador Léxico
El analizador léxico que presento se divide en 4 archivos, de la siguiente manera:

-Automata.java: este archivo simula el comportamiento de un autómata que procesará las cadenas dadas por el usuario para determinar si pertenecen al lenguaje o no.

-LectorArchivo.java: este archivo, como su nombre lo dice, contiene un programa que toma como parámetro el nombre del archivo dado por el usuario para contener el contenido del mismo un caracter a la vez hasta llegar al final del archivo.

-ManejadorTokens.java: este archivo se encarga de tomar el estado en el cual terminó el procesamiento del lexema actual, si resulta ser un estado de aceptación, entonces dará un token con la clase léxica y el valor adecuado. De no ser un estado de aceptación, mandará un error léxico.

-AnalizadorLexico.java: este archivo funciona como nuestro programa principal, que usa en conjunto los 3 anteriores, definiendo un autómata correspondiente para procesar nuestro lenguaje, leyendo el contenido del archivo dado como input por el usuario y aplicando el manejador de tokens para mostrar al usuario los resultados del análisis léxico.


#### Compilación y ejecución del programa

Para compilar el programa, basta con posicionarse en el repositorio raíz y ```javac *.java```

Para la ejecución, se usa la instrucción ```java AnalizadorLexico <nombre_archivo>``` donde <nombre_archivo> corresponde al nombre del archivo con extensión .txt a utilizar como entrada para el analizador léxico.
