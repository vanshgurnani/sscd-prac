%{ 
#include <stdio.h>
#include <stdlib.h>
%}

%%

"int" {printf("KEYWORD IS: INT\n");}
. { printf("UNKNOWN CHARACTER: %s\n", yytext); }

%%

int yywrap(){
  return 1;
}

int main(){

  yylex();
  return 0;

}
