package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SakeParserConstantTests {
    @ParameterizedTest
    @CsvSource(
        "true, TrueNode",
        "false, FalseNode",
        "nil, NilNode",
        "null, NullNode"
    )
    fun `constant parsing test`(prim: String, node: String) {
        val src = "fun f() -> $prim"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=f, params=[], expr=$node)])",
            ast.toString()
        )
    }
}
