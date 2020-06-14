package me.wollemat.sake.parser

fun SakeParser.parse(): AbstractSyntaxTree = start().toAST()

fun SakeParser.StartContext.toAST(): AbstractSyntaxTree = AbstractSyntaxTree(function().map { it.toAST() })

fun SakeParser.FunctionContext.toAST(): FunctionDeclaration = when (this) {
    is SakeParser.NoParameterFunctionContext -> FunctionDeclaration(ID().text, emptyList(), expression().toAST())
    is SakeParser.ParameterFunctionContext -> FunctionDeclaration(ID(0).text, listOf(ID(1).text), expression().toAST())
    is SakeParser.MultipleParameterFunctionContext ->
        FunctionDeclaration(ID(0).text, ID().drop(1).map { it.text }, expression().toAST())
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}

fun SakeParser.ExpressionContext.toAST(): ExpressionNode = when (this) {
    is SakeParser.GroupingExpressionContext -> expression().toAST()
    is SakeParser.IfExpressionContext -> IfNode(
        expression(0).toAST(),
        expression(1).toAST(),
        elif().map { it.toAST() },
        expression(2).toAST()
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
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}

fun SakeParser.ElifContext.toAST(): ElIfNode = when(this) {
    is SakeParser.ElIfExpressionContext -> ElIfNode(expression(0).toAST(), expression(1).toAST())
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}

fun SakeParser.ApplicationContext.toAST(): ApplicationNode = when (this) {
    is SakeParser.NoArgumentApplicationContext -> ApplicationNode(ID().text, emptyList())
    is SakeParser.ArgumentApplciationContext -> ApplicationNode(ID().text, listOf(expression().toAST()))
    is SakeParser.MultipleArgumentsApplicationContext -> ApplicationNode(ID().text, expression().map { it.toAST() })
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}

fun SakeParser.PrimitiveContext.toAST(): PrimitiveNode = when (this) {
    is SakeParser.StringPrimitiveContext -> StringNode(STRING().text.dropLast(1).drop(1))
    is SakeParser.FloatPrimitiveContext -> FloatNode(FLOAT().text.toFloat())
    is SakeParser.IntegerPrimitiveContext -> IntegerNode(INTEGER().text.toInt())
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}

fun SakeParser.ConstantContext.toAST(): ConstantNode = when (this) {
    is SakeParser.TrueConstantContext -> TrueNode
    is SakeParser.FalseConstantContext -> FalseNode
    is SakeParser.NilConstantContext -> NilNode
    is SakeParser.NullConstantContext -> NullNode
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}

fun SakeParser.BuiltinContext.toAST(): BuiltinNode = when (this) {
    is SakeParser.PrintBuiltinContext -> PrintNode(expression().toAST())
    is SakeParser.FailBuiltinContext -> FailNode(expression().toAST())
    else -> throw UnsupportedOperationException(this.javaClass.canonicalName)
}
