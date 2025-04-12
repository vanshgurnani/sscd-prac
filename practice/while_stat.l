%{
#include <stdio.h>
#include "y.tab.h"
%}

%%

"while" {return WHILE;}
"(" {return LPAREN;}
")" {return RPAREN;}
"{" {return LBRACS;}
"}" {return RBRACS;}
";"   {return SEMICOLON;}
[ \t\n]+ ;
[a-zA-Z_][a-zA-Z0-9_]* {return ID;}

. { printf("invalid character: %c\n", yytext[0]); return -1; }

%%

int yywrap(){
    return 1;
}