package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SakeParserExpressionTests {
    @Test
    fun `constant expression parsing test`() =
        ConstantOperation.values().forEach {
            println(src(it.op))
            println(ast(expr(it)))
            assertEquals(ast(expr(it)), SakeStringParser(src(it.op)).parse())
        }

    @Test
    fun `builtin expression parsing test`() =
        BuiltinOperation.values().forEach {
            println(src(it.op))
            println(ast(expr(it)))
            assertEquals(ast(expr(it)), SakeStringParser(src(it.op)).parse())
        }

    @Test
    fun `primitive expression parsing test`() =
        PrimitiveOperation.values().forEach {
            println(src(it.op))
            println(ast(expr(it)))
            assertEquals(ast(expr(it)), SakeStringParser(src(it.op)).parse())
        }

    @Test
    fun `infix expression parsing test`() =
        InfixOperation.values().forEach {
            println(src(it.op))
            println(ast(expr(it)))
            assertEquals(ast(expr(it)), SakeStringParser(src(it.op)).parse())
        }

    @Test
    fun `unary expression parsing test`() =
        UnaryOperation.values().forEach {
            println(src(it.op))
            println(ast(expr(it)))
            assertEquals(ast(expr(it)), SakeStringParser(src(it.op)).parse())
        }

    // @Test
    // fun `if expression parsing test`() {
    //     val src = src("if (true) 1 else 2")
    //     val expr = IfNode(TrueNode, IntegerNode(1), IntegerNode(2))
    //
    //     val ast = SakeStringParser(src).parse()
    //
    //     assertEquals(ast(expr), ast)
    // }
    //
    // @Test
    // fun `single elif expression parsing test`() {
    //     val src = src("if (true) 1 elif (false) 2 else 3")
    //     val expr = IfNode(TrueNode, IntegerNode(1), IfNode(FalseNode, IntegerNode(2), IntegerNode(3)))
    //
    //     val ast = SakeStringParser(src).parse()
    //
    //     assertEquals(ast(expr), ast)
    // }
    //
    // @Test
    // fun `triple elif expression parsing test`() {
    //     val src = src("if (true) 1 elif (false) 2 elif (true) 3 elif (false) 4 else 5")
    //     val expr = IfNode(
    //         TrueNode,
    //         IntegerNode(1),
    //         IfNode(
    //             FalseNode,
    //             IntegerNode(2),
    //             IfNode(TrueNode, IntegerNode(3), IfNode(FalseNode, IntegerNode(4), IntegerNode(5)))
    //         )
    //     )
    //
    //     val ast = SakeStringParser(src).parse()
    //
    //     assertEquals(ast(expr), ast)
    // }
    //
    // @Test
    // fun `variable expression parsing test`() =
    //     assertEquals(ast(VARIABLE_NODE_X), SakeStringParser(src(VARIABLE_NODE_X.id)).parse())
    //
    // @Test
    // fun `grouping expression parsing test`() =
    //     assertEquals(ast(VARIABLE_NODE_X), SakeStringParser(src("(${VARIABLE_NODE_X.id})")).parse())
    //
    // @Test
    // fun `array expression parsing test`() =
    //     assertEquals(
    //         ast(ArrayNode(VARIABLE_NODE_X.id, INT_NODE)),
    //         SakeStringParser(src("${VARIABLE_NODE_X.id}[${INT_NODE.num}]")).parse()
    //     )
    //
    // @Test
    // fun `append expression parsing test`() =
    //     assertEquals(
    //         ast(AppendNode(VARIABLE_NODE_X, VARIABLE_NODE_Y)),
    //         SakeStringParser(src("${VARIABLE_NODE_X.id} :: ${VARIABLE_NODE_Y.id}")).parse()
    //     )
}
