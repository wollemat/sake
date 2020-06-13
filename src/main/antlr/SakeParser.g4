parser grammar SakeParser;

options { tokenVocab = SakeLexer; }

// Parser rules

start       :   function+ EOF
            ;

function    :   FUN ID OPEN CLOSE ARROW expression                      # NoParameterFunction
            |   FUN ID OPEN ID CLOSE ARROW expression                   # ParameterFunction
            |   FUN ID OPEN (ID COMMA)* ID  CLOSE ARROW expression      # MultipleParameterFunction
            ;

expression  :   IF OPEN expression CLOSE expression ELSE expression     # IfExpression
            |   expression PLUS expression                              # AdditionExpression
            |   expression SUB expression                               # SubtractionExpression
            |   expression MULT expression                              # MultiplicationExpression
            |   expression DIV expression                               # DivisionExpression
            |   expression REM expression                               # RemainderExpression
            |   SUB expression                                          # NegationExpression
            |   expression AND expression                               # AndExpression
            |   expression OR expression                                # OrExpression
            |   NOT expression                                          # NotExpression
            |   expression GE expression                                # GreaterEqualsExpression
            |   expression GT expression                                # GreaterThanExpression
            |   expression LE expression                                # LessEqualsExpression
            |   expression LT expression                                # LessThanExpression
            |   expression EQ expression                                # EqualsExpression
            |   expression NEQ expression                               # NotEqualsExpression
            |   expression APPEND expression                            # AppendExpression
            |   application                                             # ApplicationExpression
            |   ID                                                      # VariableExpression
            |   primitive                                               # PrimitiveExpression
            |   constant                                                # ConstantExpression
            ;

application :   PRINT OPEN expression CLOSE                             # PrintApplication
            |   HEAD OPEN expression CLOSE                              # HeadApplication
            |   TAIL OPEN expression CLOSE                              # TailApplication
            |   FAIL OPEN CLOSE                                         # FailApplication
            |   ID OPEN CLOSE                                           # NoArgumentApplication
            |   ID OPEN expression CLOSE                                # ArgumentApplciation
            |   ID OPEN (expression COMMA)* expression CLOSE            # MultipleArgumentsApplication
            ;

primitive   :   STRING                                                  # StringPrimitive
            |   FLOAT                                                   # FloatPrimitive
            |   INTEGER                                                 # IntegerPrimitive
            ;

constant    :   TRUE                                                    # TrueConstant
            |   FALSE                                                   # FalseConstant
            |   NIL                                                     # NilConstant
            |   NULL                                                    # NullConstant
            ;
