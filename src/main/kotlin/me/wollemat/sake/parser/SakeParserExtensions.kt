package me.wollemat.sake.parser

fun SakeParser.parse(): AbstractSyntaxTree = start().toAST()

fun SakeParser.StartContext.toAST(): AbstractSyntaxTree = AbstractSyntaxTree(function().map { it.toAST() })

fun SakeParser.FunctionContext.toAST(): FunctionNode = when (this) {
    is SakeParser.NoParamFunctionContext -> FunctionNode(ID().text, emptyList(), expr().toAST())
    is SakeParser.ParamFunctionContext -> FunctionNode(ID(0).text, ID().drop(1).map { it.text }, expr().toAST())
    else -> throw UnsupportedOperationException()
}

fun parseIfExpression(expressions: List<SakeParser.ExprContext>): ExpressionNode = when (expressions.size) {
    0 -> throw IllegalStateException()
    1 -> expressions[0].toAST()
    2 -> throw IllegalStateException()
    else -> IfNode(expressions[0].toAST(), expressions[1].toAST(), parseIfExpression(expressions.drop(2)))
}

fun SakeParser.ExprContext.toAST(): ExpressionNode = when (this) {
    is SakeParser.GroupingExpressionContext -> expr().toAST()
    is SakeParser.IfExpressionContext -> IfNode(expr(0).toAST(), expr(1).toAST(), parseIfExpression(expr().drop(2)))
    is SakeParser.AdditionExpressionContext -> AddNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.SubtractionExpressionContext -> SubNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.MultiplicationExpressionContext -> MultNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.DivisionExpressionContext -> DivNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.RemainderExpressionContext -> RemNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.NegationExpressionContext -> NegNode(expr().toAST())
    is SakeParser.AndExpressionContext -> AndNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.OrExpressionContext -> OrNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.NotExpressionContext -> NotNode(expr().toAST())
    is SakeParser.GreaterEqualsExpressionContext -> GeNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.GreaterThanExpressionContext -> GtNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.LessEqualsExpressionContext -> LeNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.LessThanExpressionContext -> LtNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.EqualsExpressionContext -> EqNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.NotEqualsExpressionContext -> NeqNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.AppendExpressionContext -> AppendNode(expr(0).toAST(), expr(1).toAST())
    is SakeParser.ArrayExpressionContext -> ArrayNode(ID().text, expr().toAST())
    is SakeParser.VariableExpressionContext -> VariableNode(ID().text)
    is SakeParser.StringExpressionContext -> StringNode(STRING().text.drop(1).dropLast(1))
    is SakeParser.IntegerExpressionContext -> IntegerNode(INTEGER().text.toInt())
    is SakeParser.FloatExpressionContext -> FloatNode(FLOAT().text.toDouble())
    is SakeParser.TrueExpressionContext -> TrueNode
    is SakeParser.FalseExpressionContext -> FalseNode
    is SakeParser.NilExpressionContext -> NilNode
    is SakeParser.NullExpressionContext -> NullNode
    is SakeParser.NoParamLambdaExpressionContext -> LambdaNode(emptyList(), expr().toAST())
    is SakeParser.ParamLambdaExpressionContext -> LambdaNode(ID().map { it.text }, expr().toAST())
    is SakeParser.PrintExpressionContext -> PrintNode(expr().toAST())
    is SakeParser.FailExpressionContext -> FailNode(expr().toAST())
    is SakeParser.NoArgumentApplicationExpressionContext -> ApplicationNode(expr().toAST(), emptyList())
    is SakeParser.ArgumentApplciationExpressionContext ->
        ApplicationNode(expr(0).toAST(), expr().drop(1).map { it.toAST() })
    else -> throw UnsupportedOperationException()
}
