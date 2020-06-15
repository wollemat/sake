package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.AppendNode
import me.wollemat.sake.parser.ArrayNode
import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SakeParserExpressionTests {
    @Test
    fun `constant expression parsing test`() =
        ConstantOperation.values().forEach { assertEquals(ast(expr(it)), SakeStringParser(src(it.src)).parse()) }

    @Test
    fun `builtin expression parsing test`() =
        BuiltinOperation.values().forEach { assertEquals(ast(expr(it)), SakeStringParser(src(it.src)).parse()) }

    @Test
    fun `primitive expression parsing test`() =
        PrimitiveOperation.values().forEach { assertEquals(ast(expr(it)), SakeStringParser(src(it.src)).parse()) }

    @Test
    fun `infix expression parsing test`() =
        InfixOperation.values().forEach { assertEquals(ast(expr(it)), SakeStringParser(src(it.src)).parse()) }

    @Test
    fun `unary expression parsing test`() =
        UnaryOperation.values().forEach { assertEquals(ast(expr(it)), SakeStringParser(src(it.src)).parse()) }

    @Test
    fun `if expression parsing test`() =
        IfOperation.values().forEach { assertEquals(ast(expr(it)), SakeStringParser(src(it.src)).parse()) }

    @Test
    fun `application expression parsing test`() =
        ApplicationOperation.values().forEach { assertEquals(ast(expr(it)), SakeStringParser(src(it.src)).parse()) }

    @Test
    fun `variable expression parsing test`() =
        assertEquals(ast(VARIABLE_NODE_X), SakeStringParser(src(VARIABLE_NODE_X.id)).parse())

    @Test
    fun `grouping expression parsing test`() =
        assertEquals(ast(VARIABLE_NODE_X), SakeStringParser(src("(${VARIABLE_NODE_X.id})")).parse())

    @Test
    fun `array expression parsing test`() =
        assertEquals(
            ast(ArrayNode(VARIABLE_NODE_X.id, INT_NODE)),
            SakeStringParser(src("${VARIABLE_NODE_X.id}[${INT_NODE.num}]")).parse()
        )

    @Test
    fun `append expression parsing test`() =
        assertEquals(
            ast(AppendNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)),
            SakeStringParser(src("${VARIABLE_NODE_X.id} :: ${VARIABLE_NODE_Y.id}")).parse()
        )
}
