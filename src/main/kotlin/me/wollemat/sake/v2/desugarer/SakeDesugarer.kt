package me.wollemat.sake.v2.desugarer

import me.wollemat.sake.v2.parser.AbstractSyntaxTree
import me.wollemat.sake.v2.parser.AddNode
import me.wollemat.sake.v2.parser.AndNode
import me.wollemat.sake.v2.parser.AppendNode
import me.wollemat.sake.v2.parser.ApplicationNode
import me.wollemat.sake.v2.parser.ArrayNode
import me.wollemat.sake.v2.parser.ConcatNode
import me.wollemat.sake.v2.parser.DivNode
import me.wollemat.sake.v2.parser.EqNode
import me.wollemat.sake.v2.parser.ExpressionNode
import me.wollemat.sake.v2.parser.FailNode
import me.wollemat.sake.v2.parser.FalseNode
import me.wollemat.sake.v2.parser.FloatNode
import me.wollemat.sake.v2.parser.GeNode
import me.wollemat.sake.v2.parser.GtNode
import me.wollemat.sake.v2.parser.IfNode
import me.wollemat.sake.v2.parser.IntegerNode
import me.wollemat.sake.v2.parser.LambdaNode
import me.wollemat.sake.v2.parser.LeNode
import me.wollemat.sake.v2.parser.LtNode
import me.wollemat.sake.v2.parser.MultNode
import me.wollemat.sake.v2.parser.NegNode
import me.wollemat.sake.v2.parser.NeqNode
import me.wollemat.sake.v2.parser.NilNode
import me.wollemat.sake.v2.parser.NotNode
import me.wollemat.sake.v2.parser.NullNode
import me.wollemat.sake.v2.parser.OrNode
import me.wollemat.sake.v2.parser.PrintNode
import me.wollemat.sake.v2.parser.RemNode
import me.wollemat.sake.v2.parser.StringNode
import me.wollemat.sake.v2.parser.SubNode
import me.wollemat.sake.v2.parser.TrueNode
import me.wollemat.sake.v2.parser.VariableNode

class SakeDesugarer(val ast: AbstractSyntaxTree) {
    fun desugar(): DesugaredAbstractSyntaxTree =
        DesugaredAbstractSyntaxTree(ast.funcs.map { FunctionCore(it.id, it.params, desugar(it.expr)) })

    private fun desugar(expr: ExpressionNode): ExpressionCore = when (expr) {
        is IfNode -> IfCore(desugar(expr.cond), desugar(expr.left), desugar(expr.right))
        is VariableNode -> VariableCore(expr.id)

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

        is ArrayNode -> ArrayCore(desugar(expr.list), desugar(expr.index))
        is ConcatNode -> ConcatCore(desugar(expr.left), desugar(expr.right))
        is AppendNode -> AppendCore(desugar(expr.head), desugar(expr.tail))

        is ApplicationNode -> ApplicationCore(desugar(expr.func), expr.args.map { desugar(it) })
        is LambdaNode -> LambdaCore(expr.params, desugar(expr.expr))
        is PrintNode -> PrintCore(desugar(expr.msg))
        is FailNode -> FailCore(desugar(expr.msg))

        is StringNode -> StringCore(expr.str)
        is FloatNode -> FloatCore(expr.num)
        is IntegerNode -> IntegerCore(expr.num)

        TrueNode -> TrueCore
        FalseNode -> FalseCore
        NilNode -> NilCore
        NullNode -> NullCore
    }
}
