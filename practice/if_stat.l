%{
#include "y.tab.h"
#include <stdio.h>
%}

%%
"if"  {return IF;}
"("   {return LPAREN;}
")"   {return RPAREN;}
"{"   {return LBRACS;}
"}"   {return RBRACS;}
";"   {return SEMICOLON;}
[ \t\n]+  ;
[a-zA-Z_][a-zA-Z0-9_]*  {return ID;}
.      {printf("INVALID CHARACTER: %s\n", yytext); return -1;}

%%

int yywrap(){
    return 1;
}