package main.jflex;

import main.byacc.Parser;
import main.byacc.ParserVal;
import java.io.Reader;

%%

%{
  private Parser yyparser;

  public Lexer(Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }

  public int getLine() { return yyline; }
%}

%public
%class Lexer
%unicode
%standalone
%line

// Definiciones de patrones
digit = [0-9]
letter = [_a-zA-Z]
num = {digit}+"."{digit}+ | {digit}+
var = {letter}({letter}|{digit})*

%%

{digit}+           { 
                      double value = Double.parseDouble(yytext());
                      System.out.println("Token: NUM, Valor: " + value);
                      yyparser.setYylval(new ParserVal(value)); 
                      return Parser.NUM; 
                   }
{digit}+"."{digit}+ { 
                      double value = Double.parseDouble(yytext());
                      System.out.println("Token: NUM, Valor: " + value);
                      yyparser.setYylval(new ParserVal(value)); 
                      return Parser.NUM; 
                   }
{letter}({letter}|{digit})* { 
                      String value = yytext();
                      System.out.println("Token: VAR, Valor: " + value);
                      yyparser.setYylval(new ParserVal(value)); 
                      return Parser.VAR; 
                   }
"="                 { 
                      System.out.println("Token: ASIG");
                      return Parser.ASIG; 
                   }
"+"                 { 
                      System.out.println("Token: PLUS");
                      return Parser.PLUS; 
                   }
"-"                 { 
                      System.out.println("Token: MINUS");
                      return Parser.MINUS; 
                   }
"*"                 { 
                      System.out.println("Token: TIMES");
                      return Parser.TIMES; 
                   }
"/"                 { 
                      System.out.println("Token: DIV");
                      return Parser.DIV; 
                   }
"("                 { 
                      System.out.println("Token: LPAREN");
                      return Parser.LPAREN; 
                   }
")"                 { 
                      System.out.println("Token: RPAREN");
                      return Parser.RPAREN; 
                   }
[ \t\n\r\f]+        { /* Ignora espacios en blanco */ }
<<EOF>>             { return 0; }
.                   { return -1; }

