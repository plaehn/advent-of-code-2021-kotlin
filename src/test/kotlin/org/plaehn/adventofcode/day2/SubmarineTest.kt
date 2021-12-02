package org.plaehn.adventofcode.day2

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class SubmarineTest {

    @Test
    fun `Compute course on test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val submarine = Submarine.fromLines(lines)

        val position = submarine.computeCourse()

        assertThat(position.horizontalPosition * position.depth).isEqualTo(150)
    }

    @Test
    fun `Compute course on puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val submarine = Submarine.fromLines(lines)

        val position = submarine.computeCourse()

        assertThat(position.horizontalPosition * position.depth).isEqualTo(2027977)
    }

    @Test
    fun `Compute course using aim on test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val submarine = Submarine.fromLines(lines)

        val position = submarine.computeCourse(useAim = true)

        assertThat(position.horizontalPosition * position.depth).isEqualTo(900)
    }

    @Test
    fun `Compute course using aim on puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val submarine = Submarine.fromLines(lines)

        val position = submarine.computeCourse(useAim = true)

        assertThat(position.horizontalPosition * position.depth).isEqualTo(1903644897)
    }
}


