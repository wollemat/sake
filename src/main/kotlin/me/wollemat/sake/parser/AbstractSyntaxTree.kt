package me.wollemat.sake.parser

data class AbstractSyntaxTree(val funcs: List<FunctionDeclaration>)

data class FunctionDeclaration(val id: String, val params: List<String>, val expr: ExpressionNode)

sealed class ExpressionNode

data class IfNode(
    val cond: ExpressionNode,
    val exprIf: ExpressionNode,
    val exprElif: List<ElIfNode>,
    val exprElse: ExpressionNode
) : ExpressionNode()

data class ElIfNode(val cond: ExpressionNode, val expr: ExpressionNode) : ExpressionNode()

data class AddNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class SubNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class MultNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class DivNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class RemNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class NegNode(val expr: ExpressionNode) : ExpressionNode()
data class AndNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class OrNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class NotNode(val expr: ExpressionNode) : ExpressionNode()
data class GeNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class GtNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class LeNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class LtNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class EqNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class NeqNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class ArrayNode(val id: String, val index: ExpressionNode) : ExpressionNode()
data class AppendNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class VariableNode(val id: String) : ExpressionNode()
data class ApplicationNode(val id: String, val args: List<ExpressionNode>) : ExpressionNode()

sealed class BuiltinNode : ExpressionNode()

data class PrintNode(val msg: ExpressionNode) : BuiltinNode()
data class FailNode(val msg: ExpressionNode) : BuiltinNode()

sealed class PrimitiveNode : ExpressionNode()

data class StringNode(val str: String) : PrimitiveNode()
data class FloatNode(val num: Float) : PrimitiveNode()
data class IntegerNode(val num: Int) : PrimitiveNode()

sealed class ConstantNode : ExpressionNode()

object TrueNode : ConstantNode() {
    override fun toString(): String = "TrueNode"
}

object FalseNode : ConstantNode() {
    override fun toString(): String = "FalseNode"
}

object NilNode : ConstantNode() {
    override fun toString(): String = "NilNode"
}

object NullNode : ConstantNode() {
    override fun toString(): String = "NullNode"
}
