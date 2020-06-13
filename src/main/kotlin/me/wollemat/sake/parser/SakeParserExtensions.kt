package me.wollemat.sake.parser

fun SakeParser.parse(): AbstractSyntaxTree = start().toAST()

fun SakeParser.StartContext.toAST(): AbstractSyntaxTree = AbstractSyntaxTree(function().map { it.toAST() })

fun SakeParser.FunctionContext.toAST(): FunctionNode = when (this) {
    is SakeParser.NoParameterFunctionContext -> FunctionNode(ID().text, emptyList(), expression().toAST())
    is SakeParser.ParameterFunctionContext -> FunctionNode(ID(0).text, listOf(ID(1).text), expression().toAST())
    is SakeParser.MultipleParameterFunctionContext ->
        FunctionNode(ID(0).text, ID().drop(1).map { it.text }, expression().toAST())
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}

fun SakeParser.ExpressionContext.toAST(): ExpressionNode = when (this) {
    is SakeParser.IfExpressionContext -> IfNode(expression(0).toAST(), expression(1).toAST(), expression(2).toAST())
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
    is SakeParser.VariableExpressionContext -> VariableNode(ID().text)
    is SakeParser.ApplicationExpressionContext -> application().toAST()
    is SakeParser.PrimitiveExpressionContext -> primitive().toAST()
    is SakeParser.ConstantExpressionContext -> constant().toAST()
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}

fun SakeParser.ApplicationContext.toAST(): ExpressionNode = when (this) {
    is SakeParser.NoArgumentApplicationContext -> FunctionApplicationNode(ID().text, emptyList())
    is SakeParser.ArgumentApplciationContext -> FunctionApplicationNode(ID().text, listOf(expression().toAST()))
    is SakeParser.MultipleArgumentsApplicationContext ->
        FunctionApplicationNode(ID().text, expression().map { it.toAST() })
    is SakeParser.PrintApplicationContext -> PrintNode(expression().toAST())
    is SakeParser.HeadApplicationContext -> HeadNode(expression().toAST())
    is SakeParser.TailApplicationContext -> TailNode(expression().toAST())
    is SakeParser.FailApplicationContext -> FailNode
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}

fun SakeParser.PrimitiveContext.toAST(): ExpressionNode = when (this) {
    is SakeParser.StringPrimitiveContext -> StringNode(STRING().text)
    is SakeParser.FloatPrimitiveContext -> FloatNode(FLOAT().text.toFloat())
    is SakeParser.IntegerPrimitiveContext -> IntegerNode(INTEGER().text.toInt())
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}

fun SakeParser.ConstantContext.toAST(): ExpressionNode = when (this) {
    is SakeParser.TrueConstantContext -> TrueNode
    is SakeParser.FalseConstantContext -> FalseNode
    is SakeParser.NilConstantContext -> NilNode
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}
