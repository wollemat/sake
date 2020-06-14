lexer grammar SakeLexer;

// Lexer fragments (NOT RULES!)

fragment DIGIT      :   [0-9];
fragment LOWERCASE  :   [a-z];
fragment UPPERCASE  :   [A-Z];

//  Reserved keywords

FUN         :   'fun';
ARROW       :   '->';

IF          :   'if';
ELIF        :   'elif';
ELSE        :   'else';

TRUE        :   'true';
FALSE       :   'false';
NIL         :   'nil';
NULL        :   'null';

AND         :   'and';
OR          :   'or';
NOT         :   'not';

PRINT       :   'print';
FAIL        :   'fail';

//  Special characters

QUOTE       :   '\'';
DOT         :   '.';
COMMA       :   ',';
OPEN        :   '(';
CLOSE       :   ')';
SQUAREOPEN  :   '[';
SQUARECLOSE :   ']';

//  Reserved operators

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

//  Primitive rules

STRING      :   QUOTE (~'\''|'\\\'')* QUOTE;
FLOAT       :   DIGIT+ DOT DIGIT+;
INTEGER     :   DIGIT+;

// Identifier rule

ID          :   LOWERCASE+;

// Ignored characters

WHITESPACE  :   (' '|'\t')+ -> skip;
NEWLINE     :   ('\r'?'\n'|'\r')+ -> skip;
