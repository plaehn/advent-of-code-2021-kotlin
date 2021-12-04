package org.plaehn.adventofcode.day4

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class BingoTest {

    @Test
    fun `Compute score of first winning board on test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val bingo = Bingo.fromLines(input)

        val scoreOfWinningBoard = bingo.computeScoreOfFirstWinningBoard()

        assertThat(scoreOfWinningBoard).isEqualTo(4512)
    }

    @Test
    fun `Compute score of first winning board on puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val bingo = Bingo.fromLines(input)

        val scoreOfWinningBoard = bingo.computeScoreOfFirstWinningBoard()

        assertThat(scoreOfWinningBoard).isEqualTo(71708)
    }

    @Test
    fun `Compute score of last winning board on test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val bingo = Bingo.fromLines(input)

        val scoreOfWinningBoard = bingo.computeScoreOfLastWinningBoard()

        assertThat(scoreOfWinningBoard).isEqualTo(1924)
    }

    @Test
    fun `Compute score of last winning board on puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val bingo = Bingo.fromLines(input)

        val scoreOfWinningBoard = bingo.computeScoreOfLastWinningBoard()

        assertThat(scoreOfWinningBoard).isEqualTo(71708)
    }
}


