package org.plaehn.adventofcode.day10

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class SyntaxScorerTest {

    @Test
    fun `Compute total syntax error score on test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val scorer = SyntaxScorer(inputLines)

        val totalScore = scorer.computeTotalSyntaxErrorScore()

        assertThat(totalScore).isEqualTo(26397)
    }

    @Test
    fun `Compute total syntax error score on puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val scorer = SyntaxScorer(inputLines)

        val totalScore = scorer.computeTotalSyntaxErrorScore()

        assertThat(totalScore).isEqualTo(265527)
    }
}
