%{  
#include <stdio.h>
#include <stdlib.h>
void yyerror(char *s);
int yylex(void);    
%}

%token NUM

%left '+' '-'
%left '*' '/'

%%
input: 
    | input line ;


line: expr '\n' {printf("Result is: %d\n", $1);}
    ;


expr: expr '+' expr {$$ = $1 + $3;}
    | expr '-' expr {$$ = $1 - $3;}
    | expr '*' expr {$$ = $1 * $3;}
    | NUM;
%%


void yyerror(char *s){
    printf("syntax error: \n");
}

int main(){
    printf("arithmetic Grammer is: \n");
    return yyparse();
}