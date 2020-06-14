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
            "${BASE}$node(left=VariableNode(id=x), right=OrNode(left=VariableNode(id=y), right=VariableNode(id=z)))$END",
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
            "${BASE}$node(expr=OrNode(left=VariableNode(id=x), right=VariableNode(id=y)))$END",
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

        val expectedMult =
            "${BASE}$node(left=VariableNode(id=x), right=${"MultNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"
        val expectedDiv =
            "${BASE}$node(left=VariableNode(id=x), right=${"DivNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"
        val expectedRem =
            "${BASE}$node(left=VariableNode(id=x), right=${"RemNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"

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

        val expectedAdd =
            "${BASE}$node(left=VariableNode(id=x), right=${"AddNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"
        val expectedSub =
            "${BASE}$node(left=VariableNode(id=x), right=${"SubNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"

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

        val expectedGe =
            "${BASE}$node(left=VariableNode(id=x), right=${"GeNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"
        val expectedGt =
            "${BASE}$node(left=VariableNode(id=x), right=${"GtNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"
        val expectedLe =
            "${BASE}$node(left=VariableNode(id=x), right=${"LeNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"
        val expectedLt =
            "${BASE}$node(left=VariableNode(id=x), right=${"LtNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"

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

        val expectedEq =
            "${BASE}$node(left=VariableNode(id=x), right=${"EqNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"
        val expectedNeq =
            "${BASE}$node(left=VariableNode(id=x), right=${"NeqNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"

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

        val expectedAnd =
            "${BASE}$node(left=VariableNode(id=x), right=${"AndNode"}(left=VariableNode(id=y), right=VariableNode(id=z)))$END"

        assertEquals(expectedAnd, astAnd.toString())
    }
}
