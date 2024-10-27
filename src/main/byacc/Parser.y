%{
  import java.io.IOException;
  import main.jflex.Lexer; // Importa la clase Lexer desde el paquete main.jflex
%}

%token NUM VAR VAR_KEYWORD ASIG PLUS MINUS TIMES DIV LPAREN RPAREN // Define los tokens que usará el analizador sintáctico

%start S // Punto de entrada de la gramática

%%

S: Expr { System.out.println("Cadena aceptada: expresión válida"); } // La regla inicial puede ser una expresión (Expr)
 | Asig { System.out.println("Cadena aceptada: asignación válida"); } // O una asignación (Asig)
 ;

Expr: Term Expr_prima; // Una expresión consiste en un término seguido de una posible operación adicional (Expr_prima)

Expr_prima: PLUS Term Expr_prima // Define la parte recursiva para permitir operaciones de suma
         | MINUS Term Expr_prima // Define la parte recursiva para permitir operaciones de resta
         | /* epsilon */ // Permite que la expresión termine aquí (epsilon)
         ;

Term: Factor Term_prima; // Un término se define como un factor seguido de una posible multiplicación o división

Term_prima: TIMES Factor Term_prima // Parte recursiva que permite operaciones de multiplicación
         | DIV Factor Term_prima // Parte recursiva que permite operaciones de división
         | /* epsilon */ // Permite que el término termine aquí (epsilon)
         ;

Factor: NUM // Un factor puede ser un número
      | VAR // O una variable
      | LPAREN Expr RPAREN // O una expresión entre paréntesis
      | MINUS Factor // O una expresión negada
      ;

Asig: VAR_KEYWORD VAR ASIG Expr; // Una asignación consiste en una palabra clave (VAR_KEYWORD), una variable, un operador de asignación y una expresión

%%

Lexer scanner; // Declara el objeto del escáner

public Parser(java.io.Reader r) {
  scanner = new Lexer(r, this); // Inicializa el escáner con el lector de entrada y una referencia al parser
}

public void setYylval(ParserVal yylval) {
  this.yylval = yylval; // Método para establecer el valor de yylval
}

public void parse() {
  this.yyparse(); // Ejecuta el análisis sintáctico
}

void yyerror(String s) {
  System.err.println("Error de sintaxis: " + s); // Imprime un mensaje de error de sintaxis
}

int yylex() {
    int yyl_return = -1;
    try {
      yyl_return = scanner.yylex(); // Obtiene el siguiente token desde el escáner
    } catch (IOException e) {
      System.err.println("Error de E/S: " + e); // Captura errores de entrada/salida
    }
    return yyl_return; // Devuelve el token obtenido o -1 si no hay más tokens
}
