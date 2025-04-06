#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int i = 0;
char str[100], tp;

void advance() {
    tp = str[i++];
}

void E();
void F();
void T();
void TPrime();
void EPrime();

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

void T() {
    F();
    TPrime();
}

void TPrime() {
    if (tp == '*') {
        advance();
        F();
        TPrime();
    }
}

void E() {
    T();
    EPrime();
}

void EPrime() {
    if (tp == '+') {
        advance();
        T();
        EPrime();
    }
}

int main() {
    printf("Enter the string (infix expression): ");
    fgets(str, 100, stdin);

    // Remove newline character if present
    str[strcspn(str, "\n")] = '\0';

    advance();
    E();

    if (tp == '\0') {
        printf("String Accepted.\n");
    } else {
        printf("String not Accepted.\n");
    }

    return 0;
}
