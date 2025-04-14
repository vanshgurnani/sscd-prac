%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX 100

struct symtab {
    char name[100];
    char type[50];
} sym[MAX];

int count = 0;

// Helper to check if identifier already exists
int exists_in_symtab(const char* name) {
    for (int i = 0; i < count; i++) {
        if (strcmp(sym[i].name, name) == 0)
            return 1;
    }
    return 0;
}

// Add new identifier
void add_to_symtab(const char* name) {
    if (!exists_in_symtab(name)) {
        strcpy(sym[count].name, name);
        strcpy(sym[count].type, "IDENTIFIER");
        count++;
    }
}
%}

%option noyywrap

%%

"int"         { printf("KEYWORD: int\n"); }
"float"       { printf("KEYWORD: float\n"); }
"char"        { printf("KEYWORD: char\n"); }
"return"      { printf("KEYWORD: return\n"); }
"if"          { printf("KEYWORD: if\n"); }
"else"        { printf("KEYWORD: else\n"); }

[0-9]+        { printf("NUMBER: %s\n", yytext); }

[a-zA-Z_][a-zA-Z0-9_]* {
    printf("IDENTIFIER: %s\n", yytext);
    add_to_symtab(yytext);
}

"+"           { printf("OPERATOR: +\n"); }
"-"           { printf("OPERATOR: -\n"); }
"*"           { printf("OPERATOR: *\n"); }
"/"           { printf("OPERATOR: /\n"); }

"="           { printf("ASSIGNMENT: =\n"); }

"("           { printf("PARENTHESIS: (\n"); }
")"           { printf("PARENTHESIS: )\n"); }
"{"           { printf("BRACE: {\n"); }
"}"           { printf("BRACE: }\n"); }
";"           { printf("SEMICOLON: ;\n"); }

[ \t\n]       { /* ignore whitespace */ }

.             { printf("UNKNOWN CHARACTER: %s\n", yytext); }

%%

int main(int argc, char **argv) {
    printf("Lexical Analysis Output:\n");
    yylex();

    printf("\nSymbol Table:\n");
    for (int i = 0; i < count; i++) {
        printf("Name: %s\tType: %s\n", sym[i].name, sym[i].type);
    }

    return 0;
}
