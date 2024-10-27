%{
  // Importaciones necesarias para las funciones matemáticas y las clases auxiliares
  import java.lang.Math;
  import java.io.Reader;
  import java.io.IOException;
  import calc.jflex.Lexer;  // Importa el lexer para usarlo en el parser
%}

/* Declaraciones de tokens (YACC) */
%token NUM NEWLINE        // Token para números y salto de línea
%token PLUS MINUS TIMES DIV POW NEG LPAREN RPAREN  // Tokens para operadores y paréntesis

// Precedencias y asociatividades de operadores
%left MINUS PLUS          // Suma y resta tienen precedencia y asociatividad de izquierda
%left TIMES DIV           // Multiplicación y división tienen precedencia superior a suma/resta
%right POW                // Exponenciación tiene asociatividad a la derecha
%left NEG                 // Negación unaria
%nonassoc LPAREN RPAREN   // Paréntesis son no asociativos para controlar precedencia

/* Gramática */
%%
input:
    /* Cadena vacía */
  | input line             // Permite que la entrada sea una o varias líneas
;

line:
    NEWLINE                // Línea vacía (solo salto de línea)
  | exp NEWLINE { System.out.println("Resultado: " + $1.dval); }  // Muestra el resultado de una expresión
;

exp:
    NUM                  { $$ = new ParserVal($1.dval); }        // Valor numérico
  | exp PLUS exp         { $$ = new ParserVal($1.dval + $3.dval); }  // Suma de dos expresiones
  | exp MINUS exp        { $$ = new ParserVal($1.dval - $3.dval); }  // Resta de dos expresiones
  | exp TIMES exp        { $$ = new ParserVal($1.dval * $3.dval); }  // Multiplicación de dos expresiones
  | exp DIV exp          { $$ = new ParserVal($1.dval / $3.dval); }  // División de dos expresiones
  | MINUS exp %prec NEG  { $$ = new ParserVal(-$2.dval); }       // Negación unaria
  | exp POW exp          { $$ = new ParserVal(Math.pow($1.dval, $3.dval)); }  // Exponenciación
  | LPAREN exp RPAREN    { $$ = $2; }                            // Agrupación con paréntesis
;

%%

/* Instancia del lexer */
Lexer scanner;

/* Constructor del parser */
public Parser(Reader r) {
  this.scanner = new Lexer(r, this);  // Inicializa el lexer con el lector de entrada
}

/* Método para establecer yylval, que es el valor del token actual */
public void setYylval(ParserVal yylval) {
  this.yylval = yylval;  // Asigna el valor del token al parser
}

/* Método de parseo */
public void parse() {
  this.yyparse();  // Inicia el análisis sintáctico
}

/* Manejo de errores sintácticos */
void yyerror(String s) {
  System.out.println("Error sintáctico: " + s);  // Muestra un mensaje de error si hay problemas de sintaxis
}

/* Método para obtener el token actual */
int yylex() {
  int yyl_return = -1;
  try {
    yyl_return = scanner.yylex();  // Obtiene el siguiente token del lexer
  } catch (IOException e) {
    System.err.println("Error de E/S: " + e.getMessage());  // Manejo de errores de entrada/salida
  }
  return yyl_return;  // Retorna el token obtenido
}

