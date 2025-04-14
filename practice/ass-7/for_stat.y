%{
#include <stdio.h>
#include <stdlib.h>

void yyerror(const char *s);
int yylex(void);
%}

%token FOR LPAREN RPAREN LBRACS RBRACS SEMICOLON ASSIGN LESS ID NUMBER

%%

program:
    for_loop {
        printf("Valid for-loop syntax\n");
    }
    ;

for_loop:
    FOR LPAREN assignment SEMICOLON condition SEMICOLON assignment RPAREN compound_stmt
    ;

assignment:
    ID ASSIGN expression
    ;

condition:
    expression LESS expression
    ;

expression:
    ID
    | NUMBER
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

void yyerror(const char *s) {
    printf("Syntax error: %s\n", s);
}

int main() {
    printf("Enter a for loop:\n");
    return yyparse();
}
