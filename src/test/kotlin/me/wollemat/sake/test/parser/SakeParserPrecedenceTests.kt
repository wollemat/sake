package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SakeParserPrecedenceTests {
    @ParameterizedTest
    @CsvSource(
        "* : MultNode",
        "/ : DivNode",
        "% : RemNode",
        "+ : AddNode",
        "- : SubNode",
        ">= : GeNode",
        "> : GtNode",
        "<= : LeNode",
        "< : LtNode",
        "== :EqNode",
        "!= : NeqNode",
        "and : AndNode",
        "or : OrNode", delimiter = ':'
    )
    fun `grouping is higher than binary operator`(op: String, node: String) {
        val src = "fun f() -> x $op (y or z)"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=OrNode(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])",
            ast.toString()
        )
    }

    @ParameterizedTest
    @CsvSource(
        "- : NegNode",
        "not : NotNode", delimiter = ':'
    )
    fun `grouping is higher than unary operator`(op: String, node: String) {
        val src = "fun f() -> $op (x or y)"

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr=OrNode(expr1=VariableNode(id=x), expr2=VariableNode(id=y))))])",
            ast.toString()
        )
    }

    @ParameterizedTest
    @CsvSource(
        "+ : AddNode",
        "- : SubNode",
        ">= : GeNode",
        "> : GtNode",
        "<= : LeNode",
        "< : LtNode",
        "== :EqNode",
        "!= : NeqNode",
        "and : AndNode",
        "or : OrNode", delimiter = ':'
    )
    fun `multiplication, division and remainder are higher than binary operator`(op: String, node: String) {
        val srcMult = "fun f() -> x $op y * z"
        val srcDiv = "fun f() -> x $op y / z"
        val srcRem = "fun f() -> x $op y % z"

        val astMult = SakeStringParser(srcMult).parse()
        val astDiv = SakeStringParser(srcDiv).parse()
        val astRem = SakeStringParser(srcRem).parse()

        val expectedMult = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"MultNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"
        val expectedDiv = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"DivNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"
        val expectedRem = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"RemNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"

        assertEquals(expectedMult, astMult.toString())
        assertEquals(expectedDiv, astDiv.toString())
        assertEquals(expectedRem, astRem.toString())
    }

    @ParameterizedTest
    @CsvSource(
        ">= : GeNode",
        "> : GtNode",
        "<= : LeNode",
        "< : LtNode",
        "== :EqNode",
        "!= : NeqNode",
        "and : AndNode",
        "or : OrNode", delimiter = ':'
    )
    fun `addition and subtraction are higher than binary operator`(op: String, node: String) {
        val srcAdd = "fun f() -> x $op y + z"
        val srcSub = "fun f() -> x $op y - z"

        val astAdd = SakeStringParser(srcAdd).parse()
        val astSub = SakeStringParser(srcSub).parse()

        val expectedAdd = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"AddNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"
        val expectedSub = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"SubNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"

        assertEquals(expectedAdd, astAdd.toString())
        assertEquals(expectedSub, astSub.toString())
    }

    @ParameterizedTest
    @CsvSource(
        "== :EqNode",
        "!= : NeqNode",
        "and : AndNode",
        "or : OrNode", delimiter = ':'
    )
    fun `ge, gt, le and lt are higher than binary operator`(op: String, node: String) {
        val srcGe = "fun f() -> x $op y >= z"
        val srcGt = "fun f() -> x $op y > z"
        val srcLe = "fun f() -> x $op y <= z"
        val srcLt = "fun f() -> x $op y < z"

        val astGe = SakeStringParser(srcGe).parse()
        val astGt = SakeStringParser(srcGt).parse()
        val astLe = SakeStringParser(srcLe).parse()
        val astLt = SakeStringParser(srcLt).parse()

        val expectedGe = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"GeNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"
        val expectedGt = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"GtNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"
        val expectedLe = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"LeNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"
        val expectedLt = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"LtNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"

        assertEquals(expectedGe, astGe.toString())
        assertEquals(expectedGt, astGt.toString())
        assertEquals(expectedLe, astLe.toString())
        assertEquals(expectedLt, astLt.toString())
    }

    @ParameterizedTest
    @CsvSource(
        "and : AndNode",
        "or : OrNode", delimiter = ':'
    )
    fun `equality and inequality are higher than binary operator`(op: String, node: String) {
        val srcEq = "fun f() -> x $op y == z"
        val srcNeq = "fun f() -> x $op y != z"

        val astEq = SakeStringParser(srcEq).parse()
        val astNeq = SakeStringParser(srcNeq).parse()

        val expectedEq = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"EqNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"
        val expectedNeq = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"NeqNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"

        assertEquals(expectedEq, astEq.toString())
        assertEquals(expectedNeq, astNeq.toString())
    }

    @ParameterizedTest
    @CsvSource(
        "or : OrNode", delimiter = ':'
    )
    fun `and is higher than binary operator`(op: String, node: String) {
        val srcAnd = "fun f() -> x $op y and z"

        val astAnd = SakeStringParser(srcAnd).parse()

        val expectedAnd = "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(expr1=VariableNode(id=x), expr2=${"AndNode"}(expr1=VariableNode(id=y), expr2=VariableNode(id=z))))])"

        assertEquals(expectedAnd, astAnd.toString())
    }
}
