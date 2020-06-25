package me.wollemat.sake.v2.test

import me.wollemat.sake.v2.desugarer.SakeDesugarer
import me.wollemat.sake.v2.interpreter.NumValue
import me.wollemat.sake.v2.interpreter.SakeInterpreter
import me.wollemat.sake.v2.interpreter.Value
import me.wollemat.sake.v2.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SakeSmokeTests {
    fun interp(src: String): Value {
        val ast = SakeStringParser(src).parse()
        val dast = SakeDesugarer(ast).desugar()
        return SakeInterpreter(dast).interp()
    }

    @Test
    fun `addition smoke test`() {
        val src = "fun main() -> 1 + 2"

        assertEquals(NumValue(3.0), interp(src))
    }
}
