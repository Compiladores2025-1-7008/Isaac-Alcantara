%%

%{
import main.byacc.Parser; // Asegúrate de que el parser esté importado
import main.byacc.ParserVal;
private Parser parser;
%}

%class Lexer
%standalone
%unicode
%line

%{
  public Lexer(java.io.Reader r, Parser parser) {
    this.yyreader = r;
    this.parser = parser;
  }

  public int yylex() throws java.io.IOException {
    int token = yylexInternal(); // Método para generar y devolver los tokens de BYACC
    if (token != -1) {
      parser.yylval = new ParserVal(yytext()); // Establece el valor de yylval en parser
    }
    return token;
  }
%}

// Definiciones de tokens
NUM = [0-9]+
VAR = [a-zA-Z_][a-zA-Z_0-9]*

// Definición de reglas de tokens
%%

"+"        { return Parser.ADD; }
"-"        { return Parser.SUB; }
"*"        { return Parser.MUL; }
"/"        { return Parser.DIV; }
{NUM}      { parser.yylval = new ParserVal(Double.parseDouble(yytext())); return Parser.NUM; }
{VAR}      { parser.yylval = new ParserVal(yytext()); return Parser.VAR; }
"\n"       { return Parser.NL; }
.          { System.out.println("Caracter no reconocido: " + yytext()); }
<<EOF>>    { return 0; }

