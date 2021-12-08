package org.plaehn.adventofcode.day8

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class SevenSegmentSearchTest {

    @Test
    fun `Count easy digits on test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val sevenSegmentSearch = SevenSegmentSearch.fromInputLines(inputLines)

        assertThat(sevenSegmentSearch.countEasyDigits()).isEqualTo(26)
    }

    @Test
    fun `Count easy digits on puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val sevenSegmentSearch = SevenSegmentSearch.fromInputLines(inputLines)

        assertThat(sevenSegmentSearch.countEasyDigits()).isEqualTo(476)
    }

    @Test
    fun `Compute sum of output numbers on small input`() {
        val inputLines = listOf(
            "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | " +
                "cdfeb fcadb cdfeb cdbaf"
        )

        val sevenSegmentSearch = SevenSegmentSearch.fromInputLines(inputLines)

        assertThat(sevenSegmentSearch.computeSumOfOutputNumbers()).isEqualTo(5353)
    }

    @Test
    fun `Compute sum of output numbers on test input`() {
        val inputLines = this::class.java.readLines("test_input.txt")

        val sevenSegmentSearch = SevenSegmentSearch.fromInputLines(inputLines)

        assertThat(sevenSegmentSearch.computeSumOfOutputNumbers()).isEqualTo(61229)
    }

    @Test
    fun `Compute sum of output numbers on puzzle input`() {
        val inputLines = this::class.java.readLines("puzzle_input.txt")

        val sevenSegmentSearch = SevenSegmentSearch.fromInputLines(inputLines)

        assertThat(sevenSegmentSearch.computeSumOfOutputNumbers()).isEqualTo(1011823)
    }

}


