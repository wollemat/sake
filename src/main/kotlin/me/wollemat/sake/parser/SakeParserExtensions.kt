package me.wollemat.sake.parser

fun SakeParser.parse(): AbstractSyntaxTree = start().toAST()

fun SakeParser.StartContext.toAST(): AbstractSyntaxTree = AbstractSyntaxTree(function().map { it.toAST() })

fun SakeParser.FunctionContext.toAST(): FunctionNode = when (this) {
    is SakeParser.NoParameterFunctionContext -> FunctionNode(ID().text, emptyList(), expression().toAST())
    is SakeParser.ParameterFunctionContext -> FunctionNode(ID(0).text, listOf(ID(1).text), expression().toAST())
    is SakeParser.MultipleParameterFunctionContext ->
        FunctionNode(ID(0).text, ID().drop(1).map { it.text }, expression().toAST())
    else -> throw UnsupportedOperationException()
}

fun parseIfExpression(expressions: List<SakeParser.ExpressionContext>): ExpressionNode = when (expressions.size) {
    0 -> throw IllegalStateException()
    1 -> expressions[0].toAST()
    2 -> throw IllegalStateException()
    else -> IfNode(expressions[0].toAST(), expressions[1].toAST(), parseIfExpression(expressions.drop(2)))
}

fun SakeParser.ExpressionContext.toAST(): ExpressionNode = when (this) {
    is SakeParser.GroupingExpressionContext -> expression().toAST()
    is SakeParser.IfExpressionContext -> IfNode(
        expression(0).toAST(),
        expression(1).toAST(),
        parseIfExpression(expression().drop(2))
    )
    is SakeParser.AdditionExpressionContext -> AddNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.SubtractionExpressionContext -> SubNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.MultiplicationExpressionContext -> MultNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.DivisionExpressionContext -> DivNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.RemainderExpressionContext -> RemNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.NegationExpressionContext -> NegNode(expression().toAST())
    is SakeParser.AndExpressionContext -> AndNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.OrExpressionContext -> OrNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.NotExpressionContext -> NotNode(expression().toAST())
    is SakeParser.GreaterEqualsExpressionContext -> GeNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.GreaterThanExpressionContext -> GtNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.LessEqualsExpressionContext -> LeNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.LessThanExpressionContext -> LtNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.EqualsExpressionContext -> EqNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.NotEqualsExpressionContext -> NeqNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.AppendExpressionContext -> AppendNode(expression(0).toAST(), expression(1).toAST())
    is SakeParser.ArrayExpressionContext -> ArrayNode(ID().text, expression().toAST())
    is SakeParser.VariableExpressionContext -> VariableNode(ID().text)
    is SakeParser.ApplicationExpressionContext -> application().toAST()
    is SakeParser.PrimitiveExpressionContext -> primitive().toAST()
    is SakeParser.ConstantExpressionContext -> constant().toAST()
    is SakeParser.BuiltinExpressionContext -> builtin().toAST()
    else -> throw UnsupportedOperationException()
}

fun SakeParser.ApplicationContext.toAST(): ApplicationNode = when (this) {
    is SakeParser.NoArgumentApplicationContext -> ApplicationNode(ID().text, emptyList())
    is SakeParser.ArgumentApplciationContext -> ApplicationNode(ID().text, listOf(expression().toAST()))
    is SakeParser.MultipleArgumentsApplicationContext -> ApplicationNode(ID().text, expression().map { it.toAST() })
    else -> throw UnsupportedOperationException()
}

fun SakeParser.PrimitiveContext.toAST(): ExpressionNode = when (this) {
    is SakeParser.StringPrimitiveContext -> StringNode(STRING().text.dropLast(1).drop(1))
    is SakeParser.FloatPrimitiveContext -> FloatNode(FLOAT().text.toDouble())
    is SakeParser.IntegerPrimitiveContext -> IntegerNode(INTEGER().text.toInt())
    else -> throw UnsupportedOperationException()
}

fun SakeParser.ConstantContext.toAST(): ExpressionNode = when (this) {
    is SakeParser.TrueConstantContext -> TrueNode
    is SakeParser.FalseConstantContext -> FalseNode
    is SakeParser.NilConstantContext -> NilNode
    is SakeParser.NullConstantContext -> NullNode
    else -> throw UnsupportedOperationException()
}

fun SakeParser.BuiltinContext.toAST(): ExpressionNode = when (this) {
    is SakeParser.PrintBuiltinContext -> PrintNode(expression().toAST())
    is SakeParser.FailBuiltinContext -> FailNode(expression().toAST())
    else -> throw UnsupportedOperationException()
}
