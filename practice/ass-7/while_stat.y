%{ 
#include <stdio.h>
#include <stdlib.h>

void yyerror(const char*s);
int yylex(void);
%}

%token WHILE LPAREN RPAREN LBRACS RBRACS SEMICOLON ID

%%

program:
    stmt{
        printf("Valid statement\n");
    }
    ;

stmt:
    WHILE LPAREN condition RPAREN compound_stmt
    ;


condition:
    ID
    ;

compound_stmt:
    LBRACS stmt_list RBRACS
    ;

stmt_list:
    stmt_line
    | stmt_list stmt_line
    ;

stmt_line:
    ID SEMICOLON
    ;


%%

void yyerror(const char*s){
    printf("syntax error: %s\n", s);
}

int main(){
    printf("Enter the While statement: \n");
    return yyparse();
}