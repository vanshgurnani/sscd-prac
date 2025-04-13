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
    | input line
    ;

line: expr '\n' {printf("Result is: %d\n", $1);}
    ;


expr: expr '+' expr {$$ = $1 + $3;}
    | expr '-' expr {$$ = $1 - $3;}
    | expr '*' expr {$$ = $1 * $3;}
    | '(' expr ')'  {$$ = $2;}
    | NUM           {$$ = $1;}
    ;

%%

int main(){
    printf("Enter an arithmetic epression: \n");
    return yyparse();
}

void yyerror(char *s){
    printf("ERROR: %s\n", s);
}