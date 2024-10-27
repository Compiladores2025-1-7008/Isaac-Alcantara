%{
  import java.io.IOException;
  import main.jflex.Lexer;
%}

%token NUM VAR VAR_KEYWORD ASIG PLUS MINUS TIMES DIV LPAREN RPAREN

%start S

%%

S: Expr { System.out.println("Cadena aceptada: expresión válida"); }
 | Asig { System.out.println("Cadena aceptada: asignación válida"); }
 ;

Expr: Term Expr_prima;

Expr_prima: PLUS Term Expr_prima
         | MINUS Term Expr_prima
         | /* epsilon */
         ;

Term: Factor Term_prima;

Term_prima: TIMES Factor Term_prima
         | DIV Factor Term_prima
         | /* epsilon */
         ;

Factor: NUM
      | VAR
      | LPAREN Expr RPAREN
      | MINUS Factor
      ;

Asig: VAR_KEYWORD VAR ASIG Expr;

%%

Lexer scanner;

public Parser(java.io.Reader r) {
  scanner = new Lexer(r, this);
}

public void setYylval(ParserVal yylval) {
  this.yylval = yylval;
}

public void parse() {
  this.yyparse();
}

void yyerror(String s) {
  System.err.println("Error de sintaxis: " + s);
}

int yylex() {
    int yyl_return = -1;
    try {
      yyl_return = scanner.yylex();
      if (yyl_return != -1) {
      }
    } catch (IOException e) {
      System.err.println("Error de E/S: " + e);
    }
    return yyl_return;
  }

