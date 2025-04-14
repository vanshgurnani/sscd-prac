%{ 
#include <stdio.h>
#include "y.tab.h"
%}

%%
[0-9]+  {yylval = atoi(yytext); return NUM;}
[\n]    {return '\n';}
[+\-*/()] {return yytext[0];}
[ \t]       ;
.           {printf("Unknown Character\n");};

%%


int yywrap(){
    return 1;
}