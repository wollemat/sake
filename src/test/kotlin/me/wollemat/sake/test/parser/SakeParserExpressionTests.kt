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
        "!=, NeqNode",
        "::, AppendNode"
    )
    fun `binary expression parsing test`(op: String, node: String) {
        val src = "fun f() -> null $op null"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=f, params=[], expr=$node(expr1=NullNode, expr2=NullNode))])",
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
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=f, params=[], expr=$node(expr=NullNode))])",
            ast.toString()
        )
    }

    @Test
    fun `if expression parsing test`() {
        val src = "fun f() -> if (true) true else false"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=f, params=[], expr=IfNode(cond=TrueNode, exprIf=TrueNode, exprElif=[], exprElse=FalseNode))])",
            ast.toString()
        )
    }

    @Test
    fun `single elif expression parsing test`() {
        val src = "fun f() -> if (true) 1elif (true) 2 else 3"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=f, params=[], expr=IfNode(cond=TrueNode, exprIf=IntegerNode(num=1), exprElif=[ElIfNode(cond=TrueNode, expr=IntegerNode(num=2))], exprElse=IntegerNode(num=3)))])",
            ast.toString()
        )
    }

    @Test
    fun `triple elif expression parsing test`() {
        val src = "fun f() -> if (true) 1 elif (true) 2 elif (true) 3 elif (true) 4 else 5"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=f, params=[], expr=IfNode(cond=TrueNode, exprIf=IntegerNode(num=1), exprElif=[ElIfNode(cond=TrueNode, expr=IntegerNode(num=2)), ElIfNode(cond=TrueNode, expr=IntegerNode(num=3)), ElIfNode(cond=TrueNode, expr=IntegerNode(num=4))], exprElse=IntegerNode(num=5)))])",
            ast.toString()
        )
    }

    @Test
    fun `variable expression parsing test`() {
        val src = "fun f() -> x"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=f, params=[], expr=VariableNode(id=x))])",
            ast.toString()
        )
    }

    @Test
    fun `grouping expression parsing test`() {
        val src = "fun f() -> (null)"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=f, params=[], expr=NullNode)])",
            ast.toString()
        )
    }

    @Test
    fun `array expression parsing test`() {
        val src = "fun f() -> x[1]"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=f, params=[], expr=ArrayNode(id=x, index=IntegerNode(num=1)))])",
            ast.toString()
        )
    }
}
