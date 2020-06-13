package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SakeParserExpressionTests {
    @ParameterizedTest
    @CsvSource(
        "+, AddNode",
        "-, SubNode",
        "*, MultNode",
        "/, DivNode",
        "%, RemNode",
        "and, AndNode",
        "or, OrNode",
        ">=, GeNode",
        ">, GtNode",
        "<=, LeNode",
        "<, LtNode",
        "==, EqNode",
        "!=, NeqNode",
        "::, AppendNode"
    )
    fun `binary expression test`(op: String, node: String) {
        val src = "fun f() -> null $op null"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=NullNode, expr2=NullNode))])",
            ast.toString()
        )
    }

    @ParameterizedTest
    @CsvSource(
        "-, NegNode",
        "not, NotNode"
    )
    fun `unary expression test`(op: String, node: String) {
        val src = "fun f() -> $op null"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr=NullNode))])",
            ast.toString()
        )
    }
}
