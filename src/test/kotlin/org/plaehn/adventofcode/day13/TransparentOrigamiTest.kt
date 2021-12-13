package org.plaehn.adventofcode.day13

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class TransparentOrigamiTest {

    @Test
    fun `Compute number of dots after first fold for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val transparentOrigami = TransparentOrigami.fromInput(input)

        val dotCount = transparentOrigami.computeNumberOfDotsAfterFirstFold()

        assertThat(dotCount).isEqualTo(17)
    }

    @Test
    fun `Compute number of dots after first fold for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val transparentOrigami = TransparentOrigami.fromInput(input)

        val dotCount = transparentOrigami.computeNumberOfDotsAfterFirstFold()

        assertThat(dotCount).isEqualTo(759)
    }

    @Test
    fun `Compute code after all folds for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val transparentOrigami = TransparentOrigami.fromInput(input)

        val code = transparentOrigami.computeCode()

        assertThat(code).isEqualTo(
            """
            XXXXX
            X...X
            X...X
            X...X
            XXXXX
            """.trimIndent()
        )
    }

    @Test
    fun `Compute code after all folds for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val transparentOrigami = TransparentOrigami.fromInput(input)

        val code = transparentOrigami.computeCode()

        // HECRZKPR
        assertThat(code).isEqualTo(
            """
            X..X.XXXX..XX..XXX..XXXX.X..X.XXX..XXX.
            X..X.X....X..X.X..X....X.X.X..X..X.X..X
            XXXX.XXX..X....X..X...X..XX...X..X.X..X
            X..X.X....X....XXX...X...X.X..XXX..XXX.
            X..X.X....X..X.X.X..X....X.X..X....X.X.
            X..X.XXXX..XX..X..X.XXXX.X..X.X....X..X
            """.trimIndent()
        )
    }
}
