%{
#include <stdio.h>
#include "y.tab.h"
%}

%%

"for"       { return FOR; }
"("         { return LPAREN; }
")"         { return RPAREN; }
"{"         { return LBRACS; }
"}"         { return RBRACS; }
";"         { return SEMICOLON; }
"="         { return ASSIGN; }
"<"         { return LESS; }
[0-9]+      { yylval = atoi(yytext); return NUMBER; }
[a-zA-Z_][a-zA-Z0-9_]* { return ID; }

[ \t\n]+    ;
.           { printf("invalid character: %c\n", yytext[0]); return -1; }

%%

int yywrap() { return 1; }
