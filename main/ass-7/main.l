%{
#include "y.tab.h"
#include <stdio.h>
%}

%%
"if"            { return IF; }
"("             { return LPAREN; }
")"             { return RPAREN; }
"{"             { return LBRACE; }
"}"             { return RBRACE; }
";"             { return SEMICOLON; }
[ \t\n]+        ; // Ignore whitespace
[a-zA-Z_][a-zA-Z0-9_]*    { return ID; } // Identifiers
.               { printf("Invalid character: %s\n", yytext); return -1; }
%%

int yywrap() {
    return 1;
}
