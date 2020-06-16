grammar Sake;

@header { package me.wollemat.sake.parser; }

start       :   function+ EOF
            ;

function    :   FUN ID OPEN CLOSE ARROW expr                                    # NoParamFunction
            |   FUN ID OPEN ID (COMMA ID)* CLOSE ARROW expr                     # ParamFunction
            ;

// ORDER is important for precedence!

expr        :   OPEN expr CLOSE                                                 # GroupingExpression
            |   ID SQUAREOPEN expr SQUARECLOSE                                  # ArrayExpression
            |   SUB expr                                                        # NegationExpression
            |   NOT expr                                                        # NotExpression
            |   expr MULT expr                                                  # MultiplicationExpression
            |   expr DIV expr                                                   # DivisionExpression
            |   expr REM expr                                                   # RemainderExpression
            |   expr PLUS expr                                                  # AdditionExpression
            |   expr SUB expr                                                   # SubtractionExpression
            |   expr GE expr                                                    # GreaterEqualsExpression
            |   expr GT expr                                                    # GreaterThanExpression
            |   expr LE expr                                                    # LessEqualsExpression
            |   expr LT expr                                                    # LessThanExpression
            |   expr EQ expr                                                    # EqualsExpression
            |   expr NEQ expr                                                   # NotEqualsExpression
            |   expr AND expr                                                   # AndExpression
            |   expr OR expr                                                    # OrExpression
            |   expr APPEND expr                                                # AppendExpression
            |   expr OPEN CLOSE                                                 # NoArgumentApplicationExpression
            |   expr OPEN expr (COMMA expr)* CLOSE                              # ArgumentApplciationExpression
            |   LAMBDA OPEN CLOSE ARROW expr                                    # NoParamLambdaExpression
            |   LAMBDA OPEN ID (COMMA ID)* CLOSE ARROW expr                     # ParamLambdaExpression
            |   PRINT OPEN expr CLOSE                                           # PrintExpression
            |   FAIL OPEN expr CLOSE                                            # FailExpression
            |   IF OPEN expr CLOSE expr (ELIF OPEN expr CLOSE expr)* ELSE expr  # IfExpression
            |   ID                                                              # VariableExpression
            |   STRING                                                          # StringExpression
            |   FLOAT                                                           # FloatExpression
            |   INTEGER                                                         # IntegerExpression
            |   TRUE                                                            # TrueExpression
            |   FALSE                                                           # FalseExpression
            |   NIL                                                             # NilExpression
            |   NULL                                                            # NullExpression
            ;

// Lexer fragments (NOT RULES!)

fragment DIGIT      :   [0-9];
fragment LOWERCASE  :   [a-z];
fragment UPPERCASE  :   [A-Z];

//  Reserved keywords

FUN         :   'fun';
LAMBDA      :   'lambda';
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

//  Literal rules

STRING      :   QUOTE (~'\''|'\\\'')* QUOTE;
FLOAT       :   DIGIT+ DOT DIGIT+;
INTEGER     :   DIGIT+;

// Identifier rule

ID          :   LOWERCASE+;

// Ignored characters

WHITESPACE  :   (' '|'\t')+ -> skip;
NEWLINE     :   ('\r'?'\n'|'\r')+ -> skip;

