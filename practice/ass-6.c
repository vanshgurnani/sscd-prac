// E → T E'
// E' → + T E' | ε
// T → F T'
// T' → * F T' | ε
// F → ( E ) | id

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int i=0;
char str[100] = "(id+id)*id";
char tp;

void advance();
void E();
void EPrime();
void T();
void TPrime();
void F();

void advance(){
    tp = str[i++];
}

void E(){
    T();
    EPrime();
}

void EPrime(){
    if(tp == '+'){
        advance();
        T();
        EPrime();
    }
}

void T(){
    F();
    TPrime();
}

void TPrime(){
    if(tp == '*'){
        advance();
        F();
        TPrime();
    }
}

void F(){
    if (tp == 'i'){
        advance();
        if (tp == 'd'){
            advance();
        }
        else {
            printf("String not Accepted.\n");
            exit(1);
        }
    }
    else if (tp == '(')
    {
        advance();
        E();
        if (tp == ')'){
            advance();
        }
        else {
            printf("String not Accepted.\n");
            exit(1);
        }
    }
    
    else {
        printf("String not Accepted.\n");
        exit(1);
    }
}

int main(){
    printf("Input: %s\n", str);
    advance();
    E();

    if (tp == '\0'){
        printf("String Accepted.\n");
    }
    else {
        printf("String not Accepted.\n");
        exit(1);
    }

    return 0;
}

