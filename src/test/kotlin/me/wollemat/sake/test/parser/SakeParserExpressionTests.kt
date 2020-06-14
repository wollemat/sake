package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
        "!=, NeqNode"
    )
    fun `binary expression parsing test`(op: String, node: String) {
        val src = "fun f() -> true $op false"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}$node(left=TrueNode, right=FalseNode)$END",
            ast.toString()
        )
    }

    @ParameterizedTest
    @CsvSource(
        "-, NegNode",
        "not, NotNode"
    )
    fun `unary expression parsing test`(op: String, node: String) {
        val src = "fun f() -> $op null"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}$node(expr=NullNode)$END",
            ast.toString()
        )
    }

    @Test
    fun `if expression parsing test`() {
        val src = "fun f() -> if (true) 1 else 2"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}IfNode(cond=TrueNode, left=IntegerNode(num=1), right=IntegerNode(num=2))$END",
            ast.toString()
        )
    }

    @Test
    fun `single elif expression parsing test`() {
        val src = "fun f() -> if (true) 1 elif (false) 2 else 3"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}IfNode(cond=TrueNode, left=IntegerNode(num=1), right=IfNode(cond=FalseNode, left=IntegerNode(num=2), right=IntegerNode(num=3)))$END",
            ast.toString()
        )
    }

    @Test
    fun `triple elif expression parsing test`() {
        val src = "fun f() -> if (true) 1 elif (false) 2 elif (true) 3 elif (false) 4 else 5"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}IfNode(cond=TrueNode, left=IntegerNode(num=1), right=IfNode(cond=FalseNode, left=IntegerNode(num=2), right=IfNode(cond=TrueNode, left=IntegerNode(num=3), right=IfNode(cond=FalseNode, left=IntegerNode(num=4), right=IntegerNode(num=5)))))$END",
            ast.toString()
        )
    }

    @Test
    fun `variable expression parsing test`() {
        val src = "fun f() -> x"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}VariableNode(id=x)$END",
            ast.toString()
        )
    }

    @Test
    fun `grouping expression parsing test`() {
        val src = "fun f() -> (null)"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}NullNode$END",
            ast.toString()
        )
    }

    @Test
    fun `array expression parsing test`() {
        val src = "fun f() -> x[1]"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}ArrayNode(id=x, index=IntegerNode(num=1))$END",
            ast.toString()
        )
    }

    @Test
    fun `append expression parsing test`() {
        val src = "fun f() -> true :: nil"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "${BASE}AppendNode(head=TrueNode, tail=NilNode)$END",
            ast.toString()
        )
    }
}
