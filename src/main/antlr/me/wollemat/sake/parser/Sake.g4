grammar Sake;

@header {
package me.wollemat.sake.parser;
}

// Parser rules

start       :   function+ EOF
            ;

function    :   FUN identifier OPEN CLOSE ARROW expression
            |   FUN identifier OPEN params CLOSE ARROW expression
            ;



expression  :   IF OPEN condition CLOSE expression ELSE expression
            |   expression PLUS expression
            |   expression SUB expression
            |   expression MULT expression
            |   expression DIV expression
            |   expression REM expression
            |   SUB expression
            |   expression APPEND expression
            |   PRINT OPEN STRING CLOSE
            |   HEAD OPEN expression CLOSE
            |   TAIL OPEN expression CLOSE
            |   FAIL OPEN CLOSE
            |   STRING
            |   NUMBER
            |   TRUE
            |   FALSE
            |   NIL
            |   identifier
            |   identifier OPEN CLOSE
            |   identifier OPEN arguments CLOSE
            ;

condition   :   expression AND expression
            |   expression OR expression
            |   NOT expression
            |   expression GE expression
            |   expression GT expression
            |   expression LE expression
            |   expression LT expression
            |   expression EQ expression
            |   expression NEQ expression
            |   TRUE
            |   FALSE
            ;

params      :   (identifier COMMA)* identifier
            ;

arguments   :   (expression COMMA)* expression
            ;

identifier  :   ID
            ;

// Lexer fragments

fragment DIGIT      :   [0-9];
fragment LOWERCASE  :   [a-z];
fragment UPPERCASE  :   [A-Z];

//  Reserved keywords

FUN         :   'fun';
ARROW       :   '->';

IF          :   'if';
ELSE        :   'else';

TRUE        :   'true';
FALSE       :   'false';
NIL         :   'nil';

AND         :   'and';
OR          :   'or';
NOT         :   'not';

PRINT       :   'print';
HEAD        :   'head';
TAIL        :   'tail';
FAIL        :   'fail';

//  Special characters

COMMA       :   ',';
OPEN        :   '(';
CLOSE       :   ')';
APPEND      :   '::';

PLUS        :   '+';
SUB         :   '-';
MULT        :   '*';
DIV         :   '/';
REM         :   '%';

GE          :   '>=';
GT          :   '>';
LE          :   '<=';
LT          :   '<';
EQ          :   '==';
NEQ         :   '!=';

//  Lexer rules

STRING      :   '\'' (~'\''|'\'')* '\'';
ID          :   LOWERCASE+;
NUMBER      :   DIGIT+;

WHITESPACE  :   (' '|'\t')+ -> skip;
NEWLINE     :   ('\r'?'\n'|'\r')+ -> skip;
