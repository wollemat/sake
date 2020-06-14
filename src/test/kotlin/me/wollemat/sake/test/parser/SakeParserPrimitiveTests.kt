package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SakeParserPrimitiveTests {
    @ParameterizedTest
    @CsvSource(
        "12345, IntegerNode(num=12345)",
        "3.14, FloatNode(num=3.14)"
    )
    fun `primitive parsing test`(prim: String, node: String) {
        val src = "fun f() -> $prim"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node)])",
            ast.toString()
        )
    }

    @Test
    fun `string primitive parsing test`() {
        val src = "fun f() -> 'Hello, World!'"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=StringNode(str=Hello, World!))])",
            ast.toString()
        )
    }
}
