package me.wollemat.sake.v2.interpreter

import kotlin.math.abs
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

object DivisionByZeroException : Exception()

class SakeInterpreter(val dast: DesugaredAbstractSyntaxTree) {
    fun interp(): Value = interp(dast.funcs.find { it.id == "main" }!!.expr)

    private fun interp(expr: ExpressionCore): Value = when (expr) {
        is IfCore -> TODO()
        is AddCore -> NumValue((interp(expr.left) as NumValue).num + (interp(expr.right) as NumValue).num)
        is MultCore -> NumValue((interp(expr.left) as NumValue).num * (interp(expr.right) as NumValue).num)
        is DivCore -> {
            val dividend = (interp(expr.left) as NumValue).num
            val divisor = (interp(expr.right) as NumValue).num
            if (abs(divisor) < 0.001) throw DivisionByZeroException
            NumValue(dividend / divisor)
        }
        is RemCore -> NumValue((interp(expr.left) as NumValue).num % (interp(expr.right) as NumValue).num)
        is NandCore -> BoolValue(!((interp(expr.left) as BoolValue).bool && (interp(expr.right) as BoolValue).bool))
        is LtCore -> BoolValue((interp(expr.left) as NumValue).num < (interp(expr.right) as NumValue).num)
        is EqCore -> BoolValue((interp(expr.left) as NumValue).num == (interp(expr.right) as NumValue).num) // TODO fix floating point accuracy error
        is ArrayCore -> TODO()
        is ConcatCore -> TODO()
        is AppendCore -> TODO()
        is VariableCore -> TODO()
        is ApplicationCore -> TODO()
        is PrintCore -> TODO()
        is FailCore -> TODO()
        is StringCore -> StringValue(expr.str)
        is FloatCore -> NumValue(expr.num)
        is IntegerCore -> NumValue(expr.num.toDouble())
        is LambdaCore -> TODO()
        TrueCore -> BoolValue(true)
        FalseCore -> BoolValue(false)
        NilCore -> TODO()
        NullCore -> TODO()
    }
}
