package me.wollemat.sake.desugarer

data class DesugaredAbstractSyntaxTree(val funcs: List<FunctionCore>)

data class FunctionCore(val id: String, val params: List<String>, val expr: ExpressionCore)

sealed class ExpressionCore

data class IfCore(val cond: ExpressionCore, val left: ExpressionCore, val right: ExpressionCore) : ExpressionCore()
data class AddCore(val left: ExpressionCore, val right: ExpressionCore) : ExpressionCore()
data class MultCore(val left: ExpressionCore, val right: ExpressionCore) : ExpressionCore()
data class DivCore(val left: ExpressionCore, val right: ExpressionCore) : ExpressionCore()
data class RemCore(val left: ExpressionCore, val right: ExpressionCore) : ExpressionCore()
data class NandCore(val left: ExpressionCore, val right: ExpressionCore) : ExpressionCore()
data class LtCore(val left: ExpressionCore, val right: ExpressionCore) : ExpressionCore()
data class EqCore(val left: ExpressionCore, val right: ExpressionCore) : ExpressionCore()
data class ArrayCore(val id: String, val index: ExpressionCore) : ExpressionCore()
data class AppendCore(val left: ExpressionCore, val right: ExpressionCore) : ExpressionCore()
data class VariableCore(val id: String) : ExpressionCore()
data class ApplicationCore(val id: String, val args: List<ExpressionCore>) : ExpressionCore()
data class StringCore(val str: String) : ExpressionCore()
data class FloatCore(val num: Double) : ExpressionCore()
data class IntegerCore(val num: Int) : ExpressionCore()

object TrueCore : ExpressionCore() {
    override fun toString(): String = "TrueCore"
}

object FalseCore : ExpressionCore() {
    override fun toString(): String = "FalseCore"
}

object NilCore : ExpressionCore() {
    override fun toString(): String = "NilCore"
}

object NullCore : ExpressionCore() {
    override fun toString(): String = "NullCore"
}
