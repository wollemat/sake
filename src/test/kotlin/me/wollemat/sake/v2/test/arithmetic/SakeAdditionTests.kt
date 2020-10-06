package me.wollemat.sake.v2.test.arithmetic

import me.wollemat.sake.v2.common.run
import me.wollemat.sake.v2.interpreter.NumValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SakeAdditionTests {
    @Test
    fun `addition of positives test`() {
        val src = "fun main() -> 1 + 2"
        assertEquals(NumValue(3.0), run(src))
    }

    @Test
    fun `addition of positive and negative test`() {
        val src = "fun main() -> 1 + -2"
        assertEquals(NumValue(-1.0), run(src))
    }

    @Test
    fun `addition of negative and positive test`() {
        val src = "fun main() -> -1 + 2"
        assertEquals(NumValue(1.0), run(src))
    }

    @Test
    fun `addition of negatives test`() {
        val src = "fun main() -> -1 + -2"
        assertEquals(NumValue(-3.0), run(src))
    }

    @Test
    fun `addition of grouping and number test`() {
        val src = "fun main() -> (1 + 2) + 3"
        assertEquals(NumValue(6.0), run(src))
    }

    @Test
    fun `addition of number and grouping test`() {
        val src = "fun main() -> 1 + (2 + 3)"
        assertEquals(NumValue(6.0), run(src))
    }

    @Test
    fun `addition invalid type and number test`() {
        val src = "fun main() -> true + 2"
        assertEquals(NumValue(-3.0), run(src))
    }

    @Test
    fun `addition number and invalid type test`() {
        val src = "fun main() -> 1 + false"
        assertEquals(NumValue(-3.0), run(src))
    }
}
