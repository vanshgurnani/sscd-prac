%{
#include <stdio.h>
#include "y.tab.h"
%}

%%

"do"        { return DO; }
"while"     { return WHILE; }
"("         { return LPAREN; }
")"         { return RPAREN; }
"{"         { return LBRACS; }
"}"         { return RBRACS; }
";"         { return SEMICOLON; }
"<"         { return LESS; }
[a-zA-Z_][a-zA-Z0-9_]* { return ID; }
[0-9]+      { yylval = atoi(yytext); return NUMBER; }

[ \t\n]+    ;  // ignore whitespace
.           { printf("invalid character: %c\n", yytext[0]); return -1; }

%%

int yywrap() { return 1; }
