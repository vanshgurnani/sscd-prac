%{
#include <stdio.h>
#include <stdlib.h>

void yyerror(const char *s);
int yylex(void);
%}

%token DO WHILE LPAREN RPAREN LBRACS RBRACS SEMICOLON ID NUMBER LESS

%%

program:
    do_while_loop {
        printf("Valid do-while loop\n");
    }
    ;

do_while_loop:
    DO compound_stmt WHILE LPAREN condition RPAREN SEMICOLON
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

condition:
    expression LESS expression
    ;

expression:
    ID
    | NUMBER
    ;

%%

void yyerror(const char *s) {
    printf("Syntax error: %s\n", s);
}

int main() {
    printf("Enter a do-while loop:\n");
    return yyparse();
}
