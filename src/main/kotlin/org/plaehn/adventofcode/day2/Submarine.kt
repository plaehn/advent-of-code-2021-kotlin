package org.plaehn.adventofcode.day2

import org.plaehn.adventofcode.common.tokenize
import org.plaehn.adventofcode.day2.Direction.*

class Submarine(private val steps: List<Step>) {

    fun computeCourse(useAim: Boolean = false) =
        steps.fold(Position(horizontalPosition = 0, depth = 0, aim = 0)) { position, step ->
            position.apply(step, useAim)
        }

    companion object {
        fun fromLines(lines: List<String>) = Submarine(lines.map { Step.fromLine(it) })
    }
}

data class Position(
    val horizontalPosition: Int,
    val depth: Int,
    val aim: Int
) {
    fun apply(step: Step, useAim: Boolean) =
        if (useAim) {
            when (step.direction) {
                FORWARD -> Position(horizontalPosition + step.amount, depth + aim * step.amount, aim)
                DOWN -> Position(horizontalPosition, depth, aim + step.amount)
                UP -> Position(horizontalPosition, depth, aim - step.amount)
            }
        } else {
            when (step.direction) {
                FORWARD -> Position(horizontalPosition + step.amount, depth, aim)
                DOWN -> Position(horizontalPosition, depth + step.amount, aim)
                UP -> Position(horizontalPosition, depth - step.amount, aim)
            }
        }
}

data class Step(
    val direction: Direction,
    val amount: Int
) {
    companion object {
        fun fromLine(line: String): Step {
            val (direction, amount) = line.tokenize()
            return Step(Direction.valueOf(direction.toUpperCase()), amount.toInt())
        }
    }
}

enum class Direction {
    FORWARD,
    DOWN,
    UP
}

