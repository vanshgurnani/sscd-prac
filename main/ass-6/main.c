#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Global variables
int i = 0;
char str[100] = "(id+id)*id";  // Hardcoded expression
char tp;

// E → T E'

// E' → + T E' | ε

// T → F T'

// T' → * F T' | ε

// F → ( E ) | id

// Function prototypes
void advance();
void E();
void EPrime();
void T();
void TPrime();
void F();

// Advance to the next character in the input string
void advance() {
    tp = str[i++];
}

// F → id | (E)
void F() {
    if (tp == 'i') {
        advance();
        if (tp == 'd') {
            advance();
        } else {
            printf("String not Accepted.\n");
            exit(1);
        }
    } else if (tp == '(') {
        advance();
        E();
        if (tp == ')') {
            advance();
        } else {
            printf("String not Accepted.\n");
            exit(1);
        }
    } else {
        printf("String not Accepted.\n");
        exit(1);
    }
}

// T → F T'
void T() {
    F();
    TPrime();
}

// T' → * F T' | ε
void TPrime() {
    if (tp == '*') {
        advance();
        F();
        TPrime();
    }
}

// E → T E'
void E() {
    T();
    EPrime();
}

// E' → + T E' | ε
void EPrime() {
    if (tp == '+') {
        advance();
        T();
        EPrime();
    }
}

// Main function
int main() {
    printf("Input string: %s\n", str);

    advance();  // Start parsing
    E();

    if (tp == '\0') {
        printf("String Accepted.\n");
    } else {
        printf("String not Accepted.\n");
    }

    return 0;
}
