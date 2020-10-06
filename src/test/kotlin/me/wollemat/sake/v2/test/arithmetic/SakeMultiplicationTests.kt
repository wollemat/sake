package me.wollemat.sake.v2.test.arithmetic

import me.wollemat.sake.v2.common.run
import me.wollemat.sake.v2.interpreter.NumValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SakeMultiplicationTests {
    @Test
    fun `multiplication of positives test`() {
        val src = "fun main() -> 1 * 2"
        assertEquals(NumValue(2.0), run(src))
    }

    @Test
    fun `multiplication of positive and negative test`() {
        val src = "fun main() -> 1 * -2"
        assertEquals(NumValue(-2.0), run(src))
    }

    @Test
    fun `multiplication of negative and positive test`() {
        val src = "fun main() -> -1 * 2"
        assertEquals(NumValue(-2.0), run(src))
    }

    @Test
    fun `multiplication of negatives test`() {
        val src = "fun main() -> -1 * -2"
        assertEquals(NumValue(2.0), run(src))
    }

    @Test
    fun `multiplication of grouping and number test`() {
        val src = "fun main() -> (1 * 2) * 3"
        assertEquals(NumValue(6.0), run(src))
    }

    @Test
    fun `multiplication of number and grouping test`() {
        val src = "fun main() -> 1 * (2 * 3)"
        assertEquals(NumValue(6.0), run(src))
    }

    @Test
    fun `multiplication invalid type and number test`() {
        val src = "fun main() -> true * 2"
        assertEquals(NumValue(-3.0), run(src))
    }

    @Test
    fun `multiplication number and invalid type test`() {
        val src = "fun main() -> 1 * false"
        assertEquals(NumValue(-3.0), run(src))
    }
}
