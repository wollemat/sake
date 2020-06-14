package me.wollemat.sake.desugarer

import me.wollemat.sake.parser.AbstractSyntaxTree
import me.wollemat.sake.parser.AddNode
import me.wollemat.sake.parser.AndNode
import me.wollemat.sake.parser.AppendNode
import me.wollemat.sake.parser.ApplicationNode
import me.wollemat.sake.parser.ArrayNode
import me.wollemat.sake.parser.DivNode
import me.wollemat.sake.parser.EqNode
import me.wollemat.sake.parser.ExpressionNode
import me.wollemat.sake.parser.FailNode
import me.wollemat.sake.parser.FalseNode
import me.wollemat.sake.parser.FloatNode
import me.wollemat.sake.parser.GeNode
import me.wollemat.sake.parser.GtNode
import me.wollemat.sake.parser.IfNode
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

class SakeDesugarer(val ast: AbstractSyntaxTree) {
    fun desugar(): DesugaredAbstractSyntaxTree =
        DesugaredAbstractSyntaxTree(ast.funcs.map { FunctionCore(it.id, it.params, desugar(it.expr)) })

    private fun desugar(expr: ExpressionNode): ExpressionCore = when (expr) {
        is IfNode -> IfCore(desugar(expr.cond), desugar(expr.left), desugar(expr.right))
        is AddNode -> AddCore(desugar(expr.left), desugar(expr.right))
        is SubNode -> AddCore(desugar(expr.left), MultCore(IntegerCore(-1), desugar(expr.right)))
        is MultNode -> MultCore(desugar(expr.left), desugar(expr.right))
        is DivNode -> DivCore(desugar(expr.left), desugar(expr.right))
        is RemNode -> RemCore(desugar(expr.left), desugar(expr.right))
        is NegNode -> MultCore(IntegerCore(-1), desugar(expr.expr))
        is AndNode -> NandCore(
            NandCore(desugar(expr.left), desugar(expr.right)),
            NandCore(desugar(expr.left), desugar(expr.right))
        )
        is OrNode -> NandCore(
            NandCore(desugar(expr.left), desugar(expr.left)),
            NandCore(desugar(expr.right), desugar(expr.right))
        )
        is NotNode -> NandCore(desugar(expr.expr), desugar(expr.expr))
        is GeNode -> desugar(OrNode(GtNode(expr.left, expr.right), EqNode(expr.left, expr.right)))
        is GtNode -> LtCore(desugar(expr.right), desugar(expr.left))
        is LeNode -> desugar(OrNode(LtNode(expr.left, expr.right), EqNode(expr.left, expr.right)))
        is LtNode -> LtCore(desugar(expr.left), desugar(expr.right))
        is EqNode -> EqCore(desugar(expr.left), desugar(expr.right))
        is NeqNode -> desugar(NotNode(EqNode(expr.left, expr.right)))
        is ArrayNode -> ArrayCore(expr.id, desugar(expr.index))
        is AppendNode -> AppendCore(desugar(expr.head), desugar(expr.tail))
        is VariableNode -> VariableCore(expr.id)
        is ApplicationNode -> TODO()
        is PrintNode -> ApplicationCore("print", listOf(desugar(expr.msg)))
        is FailNode -> ApplicationCore("fail", listOf(desugar(expr.msg)))
        is StringNode -> StringCore(expr.str)
        is FloatNode -> FloatCore(expr.num)
        is IntegerNode -> IntegerCore(expr.num)
        TrueNode -> TrueCore
        FalseNode -> FalseCore
        NilNode -> NilCore
        NullNode -> NullCore
    }
}
