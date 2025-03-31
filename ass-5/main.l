%{
#include <stdio.h>
#include <stdlib.h>
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
[a-zA-Z_][a-zA-Z0-9_]* { printf("IDENTIFIER: %s\n", yytext); }

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
    yylex();
    return 0;
}
