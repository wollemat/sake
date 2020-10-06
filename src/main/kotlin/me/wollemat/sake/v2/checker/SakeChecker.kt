package me.wollemat.sake.v2.checker

import me.wollemat.sake.v2.desugarer.AddCore
import me.wollemat.sake.v2.desugarer.AppendCore
import me.wollemat.sake.v2.desugarer.ApplicationCore
import me.wollemat.sake.v2.desugarer.ArrayCore
import me.wollemat.sake.v2.desugarer.ConcatCore
import me.wollemat.sake.v2.desugarer.DesugaredAbstractSyntaxTree
import me.wollemat.sake.v2.desugarer.DivCore
import me.wollemat.sake.v2.desugarer.EqCore
import me.wollemat.sake.v2.desugarer.ExpressionCore
import me.wollemat.sake.v2.desugarer.FailCore
import me.wollemat.sake.v2.desugarer.FalseCore
import me.wollemat.sake.v2.desugarer.FloatCore
import me.wollemat.sake.v2.desugarer.FunctionCore
import me.wollemat.sake.v2.desugarer.IfCore
import me.wollemat.sake.v2.desugarer.IntegerCore
import me.wollemat.sake.v2.desugarer.LambdaCore
import me.wollemat.sake.v2.desugarer.LtCore
import me.wollemat.sake.v2.desugarer.MultCore
import me.wollemat.sake.v2.desugarer.NandCore
import me.wollemat.sake.v2.desugarer.NilCore
import me.wollemat.sake.v2.desugarer.NullCore
import me.wollemat.sake.v2.desugarer.PrintCore
import me.wollemat.sake.v2.desugarer.RemCore
import me.wollemat.sake.v2.desugarer.StringCore
import me.wollemat.sake.v2.desugarer.TrueCore
import me.wollemat.sake.v2.desugarer.VariableCore
import me.wollemat.sake.v2.interpreter.BoolType

class SakeChecker(val dast: DesugaredAbstractSyntaxTree) {
    fun check(): Boolean = dast.funcs.all { check(it) }

    private fun check(func: FunctionCore): Boolean = check(func.expr, func.params.map { it to BoolType })

    private fun check(expr: ExpressionCore, scope: TypeScope): Boolean = when (expr) {
        is IfCore -> TODO()
        is AddCore -> TODO()
        is MultCore -> TODO()
        is DivCore -> TODO()
        is RemCore -> TODO()
        is NandCore -> TODO()
        is LtCore -> TODO()
        is EqCore -> TODO()
        is ArrayCore -> TODO()
        is ConcatCore -> TODO()
        is AppendCore -> TODO()
        is VariableCore -> TODO()
        is ApplicationCore -> TODO()
        is PrintCore -> TODO()
        is FailCore -> TODO()
        is StringCore -> TODO()
        is FloatCore -> TODO()
        is IntegerCore -> TODO()
        is LambdaCore -> TODO()
        TrueCore -> TODO()
        FalseCore -> TODO()
        NilCore -> TODO()
        NullCore -> TODO()
    }
}
