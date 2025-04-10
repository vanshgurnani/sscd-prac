%{
#include <stdio.h>
#include <stdlib.h>
%}

%token NUM
%left '+' '-'
%left '*' '/'

%%
input: /* empty */
     | input line
     ;

line: expr '\n'   { printf("Result = %d\n", $1); }
    ;

expr: expr '+' expr   { $$ = $1 + $3; }
    | expr '-' expr   { $$ = $1 - $3; }
    | expr '*' expr   { $$ = $1 * $3; }
    | expr '/' expr   { 
                        if ($3 == 0) { printf("Divide by zero error!\n"); exit(1); }
                        else $$ = $1 / $3;
                      }
    | '(' expr ')'     { $$ = $2; }
    | NUM              { $$ = $1; }
    ;
%%
int main() {
    printf("Enter an arithmetic expression:\n");
    return yyparse();
}

void yyerror(const char *s) {
    fprintf(stderr, "Error: %s\n", s);
}
