package me.wollemat.sake.parser

data class AbstractSyntaxTree(val funcs: List<FunctionNode>)

sealed class Node

data class FunctionNode(val id: String, val params: List<String>, val expr: ExpressionNode) : Node()
sealed class ExpressionNode : Node()

sealed class ApplicationNode : ExpressionNode()
sealed class PrimitiveNode : ExpressionNode()
sealed class ConstantNode : ExpressionNode()

data class IfNode(val cond: ExpressionNode, val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
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
data class AppendNode(val expr1: ExpressionNode, val expr2: ExpressionNode) : ExpressionNode()
data class VariableNode(val id: String) : ExpressionNode()

data class FunctionApplicationNode(val id: String, val args: List<ExpressionNode>) : ApplicationNode()
data class PrintNode(val arg: ExpressionNode) : ApplicationNode()
data class HeadNode(val arg: ExpressionNode) : ApplicationNode()
data class TailNode(val arg: ExpressionNode) : ApplicationNode()
object FailNode : ApplicationNode() {
    override fun toString(): String = "FailNode"
}

data class StringNode(val str: String) : PrimitiveNode()
data class FloatNode(val num: Float) : PrimitiveNode()
data class IntegerNode(val num: Int) : PrimitiveNode()

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
