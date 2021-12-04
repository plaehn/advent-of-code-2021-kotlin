package org.plaehn.adventofcode.day4

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class BingoTest {

    @Test
    fun `Compute score of winning board on test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val bingo = Bingo.fromLines(input)

        val scoreOfWinningBoard = bingo.computeScoreOfWinningBoard()

        assertThat(scoreOfWinningBoard).isEqualTo(4512)
    }

    @Test
    fun `Compute score of winning board on puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val bingo = Bingo.fromLines(input)

        val scoreOfWinningBoard = bingo.computeScoreOfWinningBoard()

        assertThat(scoreOfWinningBoard).isEqualTo(71708)
    }
}


