%{ 
#include <stdio.h>
#include "y.tab.h"
%}

%%
[0-9]+  {yylval = atoi(yytext); return NUM;}
['\n']  {return '\n';}
[+\*-/()]  {return yytext[0];}
[ \t]  ;
.  { printf("Invalid character: %s\n", yytext); }
%%
int yywrap(){
    return 1;
}