// Archivo Parser.y

%{
import java.io.IOException;
import main.jflex.Lexer;
import java.io.Reader;
%}

%token NUM VAR ADD SUB MUL DIV NL
%left ADD SUB
%left MUL DIV
%right NEG

%start input

%%

input: /* empty */
     | input line
     ;

line: VAR ASIG Expr NL { System.out.println("Asignación válida"); }
    | Expr NL { System.out.println("Expresión válida"); }
    ;

Expr: Term
    | Expr ADD Term { $$ = new ParserVal($1.dval + $3.dval); }
    | Expr SUB Term { $$ = new ParserVal($1.dval - $3.dval); }
    ;

Term: Factor
    | Term MUL Factor { $$ = new ParserVal($1.dval * $3.dval); }
    | Term DIV Factor { $$ = new ParserVal($1.dval / $3.dval); }
    ;

Factor: NUM { $$ = $1; }
      | VAR { $$ = $1; }
      | ADD Factor { $$ = $2; }
      | SUB Factor { $$ = new ParserVal(-$2.dval); }
      | '(' Expr ')' { $$ = $2; }
      ;
%%

private Lexer lexer;

public Parser(Reader r) {
    lexer = new Lexer(r, this); // Usamos el lexer generado por JFlex
}

private int yylex() {
    int yyl_return = -1;
    try {
        yyl_return = lexer.yylex();  // Usamos el método yylex() del lexer
    } catch (IOException e) {
        System.err.println("Error de I/O: " + e);
    }
    return yyl_return;
}

void yyerror(String s) {
    System.out.println("Error de sintaxis: " + s);
}

public static void main(String[] args) throws IOException {
    Parser parser;
    if (args.length > 0) {
        parser = new Parser(new FileReader(args[0]));
    } else {
        parser = new Parser(new InputStreamReader(System.in));
    }
    parser.yyparse();
}

