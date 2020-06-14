package me.wollemat.sake.test.desugarer

import me.wollemat.sake.desugarer.SakeDesugarer
import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SakeDesugarerExpressionTests {
    @ParameterizedTest
    @CsvSource(
        "+, AddCore",
        "*, MultCore",
        "/, DivCore",
        "%, RemCore"
    )
    fun `core binary arithmetic expression desugar test`(op: String, core: String) {
        val src = "fun f() -> 1 $op 2"

        val dast = SakeDesugarer(SakeStringParser(src).parse()).desugar()

        Assertions.assertEquals(
            "${BASE}$core(left=IntegerCore(num=1), right=IntegerCore(num=2))$END",
            dast.toString()
        )
    }

    @Test
    fun `subtraction expression desugar test`() {
        val src = "fun f() -> 1 - 2"

        val dast = SakeDesugarer(SakeStringParser(src).parse()).desugar()

        Assertions.assertEquals(
            "${BASE}AddCore(left=IntegerCore(num=1), right=MultCore(left=IntegerCore(num=-1), right=IntegerCore(num=2)))$END",
            dast.toString()
        )
    }

    @Test
    fun `negation expression desugar test`() {
        val src = "fun f() -> -2"

        val dast = SakeDesugarer(SakeStringParser(src).parse()).desugar()

        Assertions.assertEquals(
            "${BASE}MultCore(left=IntegerCore(num=-1), right=IntegerCore(num=2))$END",
            dast.toString()
        )
    }

    @Test
    fun `ge expression desugar test`() {
        val src = "fun f() -> 1 >= 2"

        val dast = SakeDesugarer(SakeStringParser(src).parse()).desugar()

        Assertions.assertEquals(
            "${BASE}NandCore(left=NandCore(left=LtCore(left=IntegerCore(num=2), right=IntegerCore(num=1)), right=LtCore(left=IntegerCore(num=2), right=IntegerCore(num=1))), right=NandCore(left=EqCore(left=IntegerCore(num=1), right=IntegerCore(num=2)), right=EqCore(left=IntegerCore(num=1), right=IntegerCore(num=2))))$END",
            dast.toString()
        )
    }

    @Test
    fun `gt expression desugar test`() {
        val src = "fun f() -> 1 > 2"

        val dast = SakeDesugarer(SakeStringParser(src).parse()).desugar()

        Assertions.assertEquals(
            "${BASE}LtCore(left=IntegerCore(num=2), right=IntegerCore(num=1))$END",
            dast.toString()
        )
    }

    @Test
    fun `le expression desugar test`() {
        val src = "fun f() -> 1 <= 2"

        val dast = SakeDesugarer(SakeStringParser(src).parse()).desugar()

        Assertions.assertEquals(
            "${BASE}NandCore(left=NandCore(left=LtCore(left=IntegerCore(num=1), right=IntegerCore(num=2)), right=LtCore(left=IntegerCore(num=1), right=IntegerCore(num=2))), right=NandCore(left=EqCore(left=IntegerCore(num=1), right=IntegerCore(num=2)), right=EqCore(left=IntegerCore(num=1), right=IntegerCore(num=2))))$END",
            dast.toString()
        )
    }

    @Test
    fun `lt expression desugar test`() {
        val src = "fun f() -> 1 < 2"

        val dast = SakeDesugarer(SakeStringParser(src).parse()).desugar()

        Assertions.assertEquals(
            "${BASE}LtCore(left=IntegerCore(num=1), right=IntegerCore(num=2))$END",
            dast.toString()
        )
    }

    @Test
    fun `eq expression desugar test`() {
        val src = "fun f() -> 1 == 2"

        val dast = SakeDesugarer(SakeStringParser(src).parse()).desugar()

        Assertions.assertEquals(
            "${BASE}EqCore(left=IntegerCore(num=1), right=IntegerCore(num=2))$END",
            dast.toString()
        )
    }

    @Test
    fun `neq expression desugar test`() {
        val src = "fun f() -> 1 != 2"

        val dast = SakeDesugarer(SakeStringParser(src).parse()).desugar()

        Assertions.assertEquals(
            "${BASE}NandCore(left=EqCore(left=IntegerCore(num=1), right=IntegerCore(num=2)), right=EqCore(left=IntegerCore(num=1), right=IntegerCore(num=2)))$END",
            dast.toString()
        )
    }
}
