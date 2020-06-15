package me.wollemat.sake.parser

data class AbstractSyntaxTree(val funcs: List<FunctionNode>)

data class FunctionNode(val id: String, val params: List<String>, val expr: ExpressionNode)

sealed class ExpressionNode

data class IfNode(val cond: ExpressionNode, val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class AddNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class SubNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class MultNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class DivNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class RemNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class NegNode(val expr: ExpressionNode) : ExpressionNode()
data class AndNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class OrNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class NotNode(val expr: ExpressionNode) : ExpressionNode()
data class GeNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class GtNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class LeNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class LtNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class EqNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class NeqNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode()
data class ArrayNode(val id: String, val index: ExpressionNode) : ExpressionNode()
data class AppendNode(val head: ExpressionNode, val tail: ExpressionNode) : ExpressionNode()
data class VariableNode(val id: String) : ExpressionNode()
data class ApplicationNode(val id: String, val args: List<ExpressionNode>) : ExpressionNode()
data class PrintNode(val msg: ExpressionNode) : ExpressionNode()
data class FailNode(val msg: ExpressionNode) : ExpressionNode()
data class StringNode(val str: String) : ExpressionNode()
data class FloatNode(val num: Double) : ExpressionNode()
data class IntegerNode(val num: Int) : ExpressionNode()

object TrueNode : ExpressionNode() {
    override fun toString(): String = "TrueNode"
}

object FalseNode : ExpressionNode() {
    override fun toString(): String = "FalseNode"
}

object NilNode : ExpressionNode() {
    override fun toString(): String = "NilNode"
}

object NullNode : ExpressionNode() {
    override fun toString(): String = "NullNode"
}
