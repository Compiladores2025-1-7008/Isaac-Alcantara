/**
 * Escáner que detecta números y palabras
*/

%%

%public
%class Lexer
%standalone

reservada=( "int" | "do" | "if" | "private" | "while")
letra=[a-zA-Z]
digito=[0-9]
espacio=[ \t\n\v\r]
valido=({digito} | {letra} | \$ | _)
identificador=({letra}|_|\$)({valido}){0,31}


hexdigit=([a-fA-F] | {digito})
hexnumeral=(0[xX]) ({hexdigit}){1,16}





%%

{espacio} { /*No se imprimira nada para estos casos*/ }
{reservada} { System.out.print("Encontré una palabra reservada: "+yytext()+"\n"); }
{identificador} { System.out.print("Encontré un identificador: "+yytext()+"\n"); }
{hexnumeral} { System.out.print("Encontré un hexadecimal: "+yytext()+"\n"); }
