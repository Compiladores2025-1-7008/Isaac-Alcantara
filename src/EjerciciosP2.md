<p  align="center">
  <img  width="200"  src="https://www.fciencias.unam.mx/sites/default/files/logoFC_2.png"  alt="">  <br>Compiladores  2025-1 <br>
  Práctica 1: Analizadores léxicos con Lex (JFlex) <br> Profesora: Ariel Adara Ulises Mercado Martínez
</p>

# Alumno

Alcántara Estrada Kevin Isaac 

## Ejercicios 
1. ¿Qué ocurre si en la primera sección se quitan las llaves al nombre de la macro letra? (0.5 pts)

Lo que ocurre es que el programa de flex, al eliminar las llaves, no la detecta como macro, sino más bien como un patrón que intentará buscar. Es decir, pasa de tomarlo como la macro previamente definida a tomarla como el patrón "letra".

2. ¿Qué ocurre si en la segunda sección se quitan las llaves a las macros? (0.5 pts)

Ocurre algo similar, al momento de que se eliminan las llaves, el programa ya no detectará que se hace referencia a una macro previamente definida, sino que lo tomará como un patrón. Por ejemplo, si eliminamos las llaves de ```\{palabra\}```, la acción léxica asignada no se ejecutará cuando se halle un elemento definido en la macro *palabra* (porque flex ya no le percibe como dicha macro debido a la ausencia de las llaves) sino que lo tomará como el patrón "palabra", así pues, la acción léxica definida se llevará a cabo cada que encuentre una cadena que coincida con dicho patrón.

3. ¿Cómo se escribe un comentario en flex? (0.5 pts)

Existen dos tipos de comentarios en flex, los de una línea y los de múltiples líneas. Los una línea se escriben colocano // antes del contenido del comentario, mientras que los de múltiple línea se abren con el par de símbolos "/\*" y se cierran con el par de símbolos "\*/". Dentro de éstos comentarios se pueden incluir cualquier caracter (excepto aquellos que marcan el inicio o el final de un comentario).

4. ¿Qué se guarda en yytext? (0.5 pts)

Dado que *yytext* es una variable interna que utiliza flex para almacenar la secuencia de caracteres qye coincide con el patrón de una expresión regular que el escáner ha reconocido. Es decir, *yytext* almacena la cadena más reciente de caracteres de entrada  más larga que coincide con alguna expresión regular.

5. ¿Qué pasa al ejecutar el programa se introducen cadenas de caracteres y de dígitos sin espacios? (0.5 pts)

Lo que ocurre es que las cadenas sí son reconocidas de acuerdo a las expresiones regulares, es decir, si tenemos una cadena como "aabb91ab" será reconocidas 3 cadenas con alguna expresión regular, "aabb" como palabra, "91" como número (una o más apariciones de un dígito) y "ab" como palabra, esto porque flex analiza la cadena de entrada en partes, aplicando las reglas en el orden en que están definidas. La cadena dada se divide en tres segmentosporque cada segmento coincide con una de las reglas definidas. En conclusión, flex divide las cadena en los segmentos más largos posibles que coincidan con alguna de las reglas definidas hasta hallar un caracter que, de agregarlo a la cadena reconocida almacenada, ya no coincidiría con alguna de las reglas definidas y por ende comienza a escánear los siguientes caracteres como una cadena a reconocer distinta.


6. ¿Qué ocurre si introducimos caracteres como "\*" en la consola? (0.5 pts)

Sucede que el escáner de flex "ignora" el caracter, es decir, no genera alguna especie de error, sólo los imprime en la terminal; sin embargo, no realiza ninguna acción léxica dado que el caracter no coincide con ninguna de las expresiones regulares que disparan la misma. Ocurre de manera análoga con el resto de caracteres que no coincide con alguna expresión regular definida en el programa.

7. Modificar al código anterior en un archivo nuevo, de tal manera que reconozca lo siguiente: (2 pts)
    1. La expresión regular para los hexadecimales en lenguaje Java.
    	
	Se tomó como referencia que los hexadecimales en Java pueden almacenar datos que entran en el rango del tipo de dato *long* de java, por ello se consideró que pueden tener hasta 16 dígitos hexadecimales después del prefijo "0x" ó "0X".

    2. 5 palabras reservadas del lenguaje Java.
    
    	Las palabras reservadas seleccionadas para este ejercicio son: *int*, *do*, *if*, *private* y *while*
    
    
    3. Los identificadores válidos del lenguaje Java, con longitud máxima de 32 caracteres (**Sugerencia**: use el operador {m,n}).
    
    	Aunque en versiones más nuevas de Java el caracter "_" por sí solo no se considera un identificador válido, en otras versiones del lenguaje de programación sí se permite, por lo cual se decidió para este programa tomar ese caracter (sin la presencia de otros concatenados al mismo) como un identificador básico.
    	
    4. Los espacios en blanco.
    
    Sí se reconocen los espacios en blanco, pero tras hablarlo con el ayudante, se determinó que no se realizará ninguna acción léxcica al identificarlos.

El programa creado lleva el nombre de *ejercicio.flex* y se encuentra dentro de esta misma carpeta. Se creó además otro archivo para poder probar el programa al usar dicho archivo como entrada, este archivo tiene el nombre de *prueba.txt*.
