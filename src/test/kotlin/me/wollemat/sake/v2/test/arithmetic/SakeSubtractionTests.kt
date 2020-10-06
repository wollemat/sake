package me.wollemat.sake.v2.test.arithmetic

import me.wollemat.sake.v2.common.run
import me.wollemat.sake.v2.interpreter.NumValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SakeSubtractionTests {
    @Test
    fun `subtraction of positives test`() {
        val src = "fun main() -> 1 - 2"
        assertEquals(NumValue(-1.0), run(src))
    }

    @Test
    fun `subtraction of positive and negative test`() {
        val src = "fun main() -> 1 - -2"
        assertEquals(NumValue(3.0), run(src))
    }

    @Test
    fun `subtraction of negative and positive test`() {
        val src = "fun main() -> -1 - 2"
        assertEquals(NumValue(-3.0), run(src))
    }

    @Test
    fun `subtraction of negatives test`() {
        val src = "fun main() -> -1 - -2"
        assertEquals(NumValue(1.0), run(src))
    }

    @Test
    fun `subtraction of grouping and number test`() {
        val src = "fun main() -> (1 - 2) - 3"
        assertEquals(NumValue(-4.0), run(src))
    }

    @Test
    fun `subtraction of number and grouping test`() {
        val src = "fun main() -> 1 - (2 - 3)"
        assertEquals(NumValue(2.0), run(src))
    }

    @Test
    fun `subtraction invalid type and number test`() {
        val src = "fun main() -> true - 2"
        assertEquals(NumValue(-3.0), run(src))
    }

    @Test
    fun `subtraction number and invalid type test`() {
        val src = "fun main() -> 1 - false"
        assertEquals(NumValue(-3.0), run(src))
    }
}
