package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SakeParserPrecedenceTests {
    @Test
    fun `grouping precedence test`() {
        val srcDefault = src("1 + 2 * 3")
        val srcGrouped = src("1 + (2 * 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "*",
        "/",
        "%",
        "+",
        "-",
        ">=",
        ">",
        "<=",
        "<",
        "==",
        "!=",
        "and",
        "or"
    )
    fun `negation precedence test`(op: String) {
        val srcDefault = src("1 $op -2")
        val srcGrouped = src("1 $op (-2)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "*",
        "/",
        "%",
        "+",
        "-",
        ">=",
        ">",
        "<=",
        "<",
        "==",
        "!=",
        "and",
        "or"
    )
    fun `not precedence test`(op: String) {
        val srcDefault = src("1 $op not 2")
        val srcGrouped = src("1 $op (not 2)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "+",
        "-",
        ">=",
        ">",
        "<=",
        "<",
        "==",
        "!=",
        "and",
        "or"
    )
    fun `multiplication precedence test`(op: String) {
        val srcDefault = src("1 $op 2 * 3")
        val srcGrouped = src("1 $op (2 * 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "+",
        "-",
        ">=",
        ">",
        "<=",
        "<",
        "==",
        "!=",
        "and",
        "or"
    )
    fun `division precedence test`(op: String) {
        val srcDefault = src("1 $op 2 / 3")
        val srcGrouped = src("1 $op (2 / 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "+",
        "-",
        ">=",
        ">",
        "<=",
        "<",
        "==",
        "!=",
        "and",
        "or"
    )
    fun `remainder precedence test`(op: String) {
        val srcDefault = src("1 $op 2 % 3")
        val srcGrouped = src("1 $op (2 % 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        ">=",
        ">",
        "<=",
        "<",
        "==",
        "!=",
        "and",
        "or"
    )
    fun `addition precedence test`(op: String) {
        val srcDefault = src("1 $op 2 + 3")
        val srcGrouped = src("1 $op (2 + 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        ">=",
        ">",
        "<=",
        "<",
        "==",
        "!=",
        "and",
        "or"
    )
    fun `subtraction precedence test`(op: String) {
        val srcDefault = src("1 $op 2 - 3")
        val srcGrouped = src("1 $op (2 - 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "==",
        "!=",
        "and",
        "or"
    )
    fun `ge precedence test`(op: String) {
        val srcDefault = src("1 $op 2 >= 3")
        val srcGrouped = src("1 $op (2 >= 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "==",
        "!=",
        "and",
        "or"
    )
    fun `gt precedence test`(op: String) {
        val srcDefault = src("1 $op 2 > 3")
        val srcGrouped = src("1 $op (2 > 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "==",
        "!=",
        "and",
        "or"
    )
    fun `le precedence test`(op: String) {
        val srcDefault = src("1 $op 2 <= 3")
        val srcGrouped = src("1 $op (2 <= 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "==",
        "!=",
        "and",
        "or"
    )
    fun `lt precedence test`(op: String) {
        val srcDefault = src("1 $op 2 < 3")
        val srcGrouped = src("1 $op (2 < 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "and",
        "or"
    )
    fun `eq precedence test`(op: String) {
        val srcDefault = src("1 $op 2 == 3")
        val srcGrouped = src("1 $op (2 == 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "and",
        "or"
    )
    fun `neq precedence test`(op: String) {
        val srcDefault = src("1 $op 2 != 3")
        val srcGrouped = src("1 $op (2 != 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @Test
    fun `and precedence test`() {
        val srcDefault = src("1 or 2 and 3")
        val srcGrouped = src("1 or (2 and 3)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }
}
