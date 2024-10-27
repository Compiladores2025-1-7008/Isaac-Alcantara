%{
  import java.io.IOException;
  import main.jflex.Lexer;
%}

%token NUM VAR ASIG PLUS MINUS TIMES DIV LPAREN RPAREN

%start S

%%

S: Expr { System.out.println("Cadena aceptada: expresión válida"); }
 | Asig { System.out.println("Cadena aceptada: asignación válida"); }
 ;

Expr: Term
    | Expr_add
    | Expr_sub
    ;

Expr_add: Term PLUS Term
        | Term PLUS Expr_add
        ;

Expr_sub: Term MINUS Term
        | Term MINUS Expr_sub
        ;

Term: Factor
    | Term_mult
    | Term_div
    ;

Term_mult: Factor TIMES Factor
         | Factor TIMES Term_mult
         ;

Term_div: Factor DIV Factor
        | Factor DIV Term_div
        ;

Factor: NUM
      | VAR
      | LPAREN Expr RPAREN
      | MINUS Factor
      ;

Asig: VAR ASIG Expr ;

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
        // Imprime el token y su valor, si está disponible
        System.out.println("Token: " + tokenToString(yyl_return) + ", Valor: " + (yylval != null ? yylval.obj : "N/A"));
      }
    } catch (IOException e) {
      System.err.println("Error de E/S: " + e);
    }
    return yyl_return;
  }

private String tokenToString(int token) {
  switch (token) {
    case NUM: return "NUM";
    case VAR: return "VAR";
    case PLUS: return "PLUS";
    case MINUS: return "MINUS";
    case TIMES: return "TIMES";
    case DIV: return "DIV";
    case ASIG: return "ASIG";
    case LPAREN: return "LPAREN";
    case RPAREN: return "RPAREN";
    default: return "UNKNOWN";
  }
}
