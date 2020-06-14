package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SakeParserBuiltinTests {
    @ParameterizedTest
    @CsvSource(
        "print, PrintNode",
        "fail, FailNode"
    )
    fun `single argument builtin function application parsing test`(func: String, node: String) {
        val src = "fun f() -> $func(null)"

        val ast = SakeStringParser(src).parse()

        Assertions.assertEquals(
            "${BASE}$node(msg=NullNode)$END",
            ast.toString()
        )
    }
}
