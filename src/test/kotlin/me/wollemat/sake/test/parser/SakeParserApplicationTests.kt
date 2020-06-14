package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SakeParserApplicationTests {
    @ParameterizedTest
    @CsvSource(
        "foo",
        "bar",
        "baz"
    )
    fun `no argument user defined function application parsing test`(id: String) {
        val src = "fun f() -> $id()"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}ApplicationNode(id=$id, args=[])$END",
            ast.toString()
        )
    }

    @ParameterizedTest
    @CsvSource(
        "foo : null : NullNode",
        "bar : x : VariableNode(id=x)",
        "baz : null + null : AddNode(left=NullNode, right=NullNode)", delimiter = ':'
    )
    fun `single argument user defined function application parsing test`(id: String, arg: String, node: String) {
        val src = "fun f() -> $id($arg)"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}ApplicationNode(id=$id, args=[$node])$END",
            ast.toString()
        )
    }

    @Test
    fun `multiple argument user defined function application parsing test`() {
        val src = "fun f() -> foo(1, x, null)"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}ApplicationNode(id=foo, args=[IntegerNode(num=1), VariableNode(id=x), NullNode])$END",
            ast.toString()
        )
    }
}
