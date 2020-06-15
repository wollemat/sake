package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SakeParserPrecedenceTests {
    @Test
    fun `grouping precedence test`() {
        val srcDefault = src("x + y * z")
        val srcGrouped = src("x + (y * z)")

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
        val srcDefault = src("x $op -y")
        val srcGrouped = src("x $op (-y)")

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
        val srcDefault = src("x $op not y")
        val srcGrouped = src("x $op (not y)")

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
        val srcDefault = src("x $op y * z")
        val srcGrouped = src("x $op (y * z)")

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
        val srcDefault = src("x $op y / z")
        val srcGrouped = src("x $op (y / z)")

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
        val srcDefault = src("x $op y % z")
        val srcGrouped = src("x $op (y % z)")

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
        val srcDefault = src("x $op y + z")
        val srcGrouped = src("x $op (y + z)")

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
        val srcDefault = src("x $op y - z")
        val srcGrouped = src("x $op (y - z)")

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
        val srcDefault = src("x $op y >= z")
        val srcGrouped = src("x $op (y >= z)")

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
        val srcDefault = src("x $op y > z")
        val srcGrouped = src("x $op (y > z)")

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
        val srcDefault = src("x $op y <= z")
        val srcGrouped = src("x $op (y <= z)")

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
        val srcDefault = src("x $op y < z")
        val srcGrouped = src("x $op (y < z)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "and",
        "or"
    )
    fun `eq precedence test`(op: String) {
        val srcDefault = src("x $op y == z")
        val srcGrouped = src("x $op (y == z)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @ParameterizedTest
    @CsvSource(
        "and",
        "or"
    )
    fun `neq precedence test`(op: String) {
        val srcDefault = src("x $op y != z")
        val srcGrouped = src("x $op (y != z)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }

    @Test
    fun `and precedence test`() {
        val srcDefault = src("x or y and z")
        val srcGrouped = src("x or (y and z)")

        assertEquals(SakeStringParser(srcGrouped).parse(), SakeStringParser(srcDefault).parse())
    }
}
