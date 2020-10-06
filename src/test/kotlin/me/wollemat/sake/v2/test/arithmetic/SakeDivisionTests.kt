package me.wollemat.sake.v2.test.arithmetic

import me.wollemat.sake.v2.common.run
import me.wollemat.sake.v2.interpreter.DivisionByZeroException
import me.wollemat.sake.v2.interpreter.NumValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class SakeDivisionTests {
    @Test
    fun `division of positives test`() {
        val src = "fun main() -> 1 / 2"
        assertEquals(NumValue(0.5), run(src))
    }

    @Test
    fun `division of positive and negative test`() {
        val src = "fun main() -> 1 / -2"
        assertEquals(NumValue(-0.5), run(src))
    }

    @Test
    fun `division of negative and positive test`() {
        val src = "fun main() -> -1 / 2"
        assertEquals(NumValue(-0.5), run(src))
    }

    @Test
    fun `division of negatives test`() {
        val src = "fun main() -> -1 / -2"
        assertEquals(NumValue(0.5), run(src))
    }

    @Test
    fun `division of grouping and number test`() {
        val src = "fun main() -> (20 / 2) / 5"
        assertEquals(NumValue(2.0), run(src))
    }

    @Test
    fun `division of number and grouping test`() {
        val src = "fun main() -> 20 / (2 / 5)"
        assertEquals(NumValue(50.0), run(src))
    }

    @Test
    fun `division invalid type and number test`() {
        val src = "fun main() -> true / 2"
        assertEquals(NumValue(-3.0), run(src))
    }

    @Test
    fun `division number and invalid type test`() {
        val src = "fun main() -> 1 / false"
        assertEquals(NumValue(-3.0), run(src))
    }

    @Test
    fun `division by zero test`() {
        val src = "fun main() -> 10 / 0"
        assertThrows(DivisionByZeroException.javaClass) { run(src) }
    }
}
