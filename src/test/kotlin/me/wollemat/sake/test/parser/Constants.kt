package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.AbstractSyntaxTree
import me.wollemat.sake.parser.AddNode
import me.wollemat.sake.parser.AndNode
import me.wollemat.sake.parser.AppendNode
import me.wollemat.sake.parser.DivNode
import me.wollemat.sake.parser.EqNode
import me.wollemat.sake.parser.ExpressionNode
import me.wollemat.sake.parser.FailNode
import me.wollemat.sake.parser.FalseNode
import me.wollemat.sake.parser.FloatNode
import me.wollemat.sake.parser.FunctionNode
import me.wollemat.sake.parser.GeNode
import me.wollemat.sake.parser.GtNode
import me.wollemat.sake.parser.IntegerNode
import me.wollemat.sake.parser.LeNode
import me.wollemat.sake.parser.LtNode
import me.wollemat.sake.parser.MultNode
import me.wollemat.sake.parser.NegNode
import me.wollemat.sake.parser.NeqNode
import me.wollemat.sake.parser.NilNode
import me.wollemat.sake.parser.NotNode
import me.wollemat.sake.parser.NullNode
import me.wollemat.sake.parser.OrNode
import me.wollemat.sake.parser.PrintNode
import me.wollemat.sake.parser.RemNode
import me.wollemat.sake.parser.StringNode
import me.wollemat.sake.parser.SubNode
import me.wollemat.sake.parser.TrueNode
import me.wollemat.sake.parser.VariableNode

const val PRIMARY_FUNCTION_NAME = "foo"

val STRING_NODE = StringNode("Hello, World!")
val FLOAT_NODE = FloatNode(3.14)
val INT_NODE = IntegerNode(1965)
val VARIABLE_NODE_X = VariableNode("x")
val VARIABLE_NODE_Y = VariableNode("y")

fun src(expr: String) = "fun $PRIMARY_FUNCTION_NAME() -> $expr"

fun ast(expr: ExpressionNode): AbstractSyntaxTree =
    AbstractSyntaxTree(listOf(FunctionNode(PRIMARY_FUNCTION_NAME, emptyList(), expr)))

enum class ConstantOperation(val op: String) {
    TRUE("true"),
    FALSE("false"),
    NIL("nil"),
    NULL("null")
}

enum class PrimitiveOperation(val op: String) {
    STRING("'${STRING_NODE.str}'"),
    INT(INT_NODE.num.toString()),
    FLOAT(FLOAT_NODE.num.toString())
}

enum class BuiltinOperation(val op: String) {
    PRINT("print('${STRING_NODE.str}')"),
    FAIL("fail('${STRING_NODE.str}')")
}

enum class InfixOperation(val op: String) {
    ADD("${VARIABLE_NODE_X.id} + ${VARIABLE_NODE_Y.id}"),
    SUB("${VARIABLE_NODE_X.id} - ${VARIABLE_NODE_Y.id}"),
    MULT("${VARIABLE_NODE_X.id} * ${VARIABLE_NODE_Y.id}"),
    DIV("${VARIABLE_NODE_X.id} / ${VARIABLE_NODE_Y.id}"),
    REM("${VARIABLE_NODE_X.id} % ${VARIABLE_NODE_Y.id}"),
    GE("${VARIABLE_NODE_X.id} >= ${VARIABLE_NODE_Y.id}"),
    GT("${VARIABLE_NODE_X.id} > ${VARIABLE_NODE_Y.id}"),
    LE("${VARIABLE_NODE_X.id} <= ${VARIABLE_NODE_Y.id}"),
    LT("${VARIABLE_NODE_X.id} < ${VARIABLE_NODE_Y.id}"),
    EQ("${VARIABLE_NODE_X.id} == ${VARIABLE_NODE_Y.id}"),
    NEQ("${VARIABLE_NODE_X.id} != ${VARIABLE_NODE_Y.id}"),
    AND("${VARIABLE_NODE_X.id} and ${VARIABLE_NODE_Y.id}"),
    OR("${VARIABLE_NODE_X.id} or ${VARIABLE_NODE_Y.id}"),
    APPEND("${VARIABLE_NODE_X.id} :: ${VARIABLE_NODE_Y.id}")
}

enum class UnaryOperation(val op: String) {
    NEG("- ${VARIABLE_NODE_X.id}"),
    NOT("not ${VARIABLE_NODE_X.id}")
}

fun expr(op: ConstantOperation): ExpressionNode = when (op) {
    ConstantOperation.TRUE -> TrueNode
    ConstantOperation.FALSE -> FalseNode
    ConstantOperation.NIL -> NilNode
    ConstantOperation.NULL -> NullNode
}

fun expr(op: PrimitiveOperation): ExpressionNode = when (op) {
    PrimitiveOperation.STRING -> STRING_NODE
    PrimitiveOperation.INT -> INT_NODE
    PrimitiveOperation.FLOAT -> FLOAT_NODE
}

fun expr(op: BuiltinOperation): ExpressionNode = when (op) {
    BuiltinOperation.PRINT -> PrintNode(STRING_NODE)
    BuiltinOperation.FAIL -> FailNode(STRING_NODE)
}

fun expr(op: InfixOperation): ExpressionNode = when (op) {
    InfixOperation.ADD -> AddNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.SUB -> SubNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.MULT -> MultNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.DIV -> DivNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.REM -> RemNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.AND -> AndNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.OR -> OrNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.GE -> GeNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.GT -> GtNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.LE -> LeNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.LT -> LtNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.EQ -> EqNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.NEQ -> NeqNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
    InfixOperation.APPEND -> AppendNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)
}

fun expr(op: UnaryOperation): ExpressionNode = when (op) {
    UnaryOperation.NOT -> NotNode(VARIABLE_NODE_X)
    UnaryOperation.NEG -> NegNode(VARIABLE_NODE_X)
}
