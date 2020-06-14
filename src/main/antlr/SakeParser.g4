parser grammar SakeParser;

options { tokenVocab = SakeLexer; }

// Parser rules

start       :   function+ EOF
            ;

function    :   FUN ID OPEN CLOSE ARROW expression                                          # NoParameterFunction
            |   FUN ID OPEN ID CLOSE ARROW expression                                       # ParameterFunction
            |   FUN ID OPEN (ID COMMA)* ID  CLOSE ARROW expression                          # MultipleParameterFunction
            ;

// ORDER is important for precedence!

expression  :   OPEN expression CLOSE                                                       # GroupingExpression
            |   ID SQUAREOPEN expression SQUARECLOSE                                        # ArrayExpression
            |   SUB expression                                                              # NegationExpression
            |   NOT expression                                                              # NotExpression
            |   expression MULT expression                                                  # MultiplicationExpression
            |   expression DIV expression                                                   # DivisionExpression
            |   expression REM expression                                                   # RemainderExpression
            |   expression PLUS expression                                                  # AdditionExpression
            |   expression SUB expression                                                   # SubtractionExpression
            |   expression GE expression                                                    # GreaterEqualsExpression
            |   expression GT expression                                                    # GreaterThanExpression
            |   expression LE expression                                                    # LessEqualsExpression
            |   expression LT expression                                                    # LessThanExpression
            |   expression EQ expression                                                    # EqualsExpression
            |   expression NEQ expression                                                   # NotEqualsExpression
            |   expression AND expression                                                   # AndExpression
            |   expression OR expression                                                    # OrExpression
            |   IF OPEN expression CLOSE expression (ELIF OPEN expression CLOSE expression)* ELSE expression # IfExpression
            |   expression APPEND expression                                                # AppendExpression
            |   builtin                                                                     # BuiltinExpression
            |   application                                                                 # ApplicationExpression
            |   primitive                                                                   # PrimitiveExpression
            |   constant                                                                    # ConstantExpression
            |   ID                                                                          # VariableExpression
            ;

application :   ID OPEN CLOSE                                                               # NoArgumentApplication
            |   ID OPEN expression CLOSE                                                    # ArgumentApplciation
            |   ID OPEN (expression COMMA)* expression CLOSE                                # MultipleArgumentsApplication
            ;

builtin     :   PRINT OPEN expression CLOSE                                                 # PrintBuiltin
            |   FAIL OPEN expression CLOSE                                                  # FailBuiltin
            ;

primitive   :   STRING                                                                      # StringPrimitive
            |   FLOAT                                                                       # FloatPrimitive
            |   INTEGER                                                                     # IntegerPrimitive
            ;

constant    :   TRUE                                                                        # TrueConstant
            |   FALSE                                                                       # FalseConstant
            |   NIL                                                                         # NilConstant
            |   NULL                                                                        # NullConstant
            ;
