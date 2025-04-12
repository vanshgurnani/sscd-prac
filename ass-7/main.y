%{
#include <stdio.h>
#include <stdlib.h>

void yyerror(const char *s);
%}

%token IF LPAREN RPAREN LBRACE RBRACE SEMICOLON ID

%%

program:
    stmt
    ;

stmt:
    IF LPAREN condition RPAREN compound_stmt
    ;

condition:
    ID // Just a placeholder for a simple condition
    ;

compound_stmt:
    LBRACE stmt_list RBRACE
    ;

stmt_list:
    stmt_line
    | stmt_list stmt_line
    ;

stmt_line:
    ID SEMICOLON
    ;

%%

void yyerror(const char *s) {
    printf("Syntax Error: %s\n", s);
}

int main() {
    printf("Enter an if-statement:\n");
    return yyparse();
}
