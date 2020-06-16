package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SakeParserAssociativityTests {
    @ParameterizedTest
    @CsvSource(
        "*",
        "/",
        "%",
        "+",
        "-",
        "and",
        "or"
    )
    fun `infix associativity test`(op: String) {
        val srcDefault = src("x $op y $op z")
        val srcGrouped = src("(x $op y) $op z")

        Assertions.assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "-",
        "not"
    )
    fun `prefix associativity test`(op: String) {
        val srcDefault = src("$op x")
        val srcGrouped = src("($op x)")

        Assertions.assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @Test
    fun `application associativity test`() {
        val srcDefault = src("foo(a)(b)(c)")
        val srcGrouped = src("((foo(a))(b))(c)")

        Assertions.assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }
}
