package org.plaehn.adventofcode.day17

import org.plaehn.adventofcode.common.Coord
import kotlin.math.sign

class TrickShot(private val targetArea: TargetArea) {

    fun computeHighestVerticalPositionOfAllSuccessfulShots() =
        computeHorizontalVelocityRange().map { horizontalVelocity ->
            computeVerticalVelocityRange().map { verticalVelocity ->
                shoot(initialVelocity = Coord(horizontalVelocity, verticalVelocity))
            }
        }.flatten().maxOrNull()!!

    private fun computeHorizontalVelocityRange() =
        (1..targetArea.lowerRight.x)

    private fun computeVerticalVelocityRange() =
        (targetArea.lowerRight.y..270)

    private fun shoot(initialVelocity: Coord): Int {
        var maxY = Int.MIN_VALUE
        var velocity = initialVelocity
        var pos = Coord(0, 0)
        do {
            maxY = if (pos.y > maxY) pos.y else maxY
            pos += velocity
            velocity = Coord(x = velocity.x - velocity.x.sign, y = velocity.y - 1)
            if (targetArea.contains(pos)) {
                return maxY
            }
        } while (!hasOvershot(pos, velocity))
        return Int.MIN_VALUE
    }

    private fun hasOvershot(pos: Coord, velocity: Coord) =
        pos.x > targetArea.lowerRight.x && velocity.x >= 0 || pos.y < targetArea.lowerRight.y

    companion object {
        fun fromInput(input: String): TrickShot {
            val (xRange, yRange) = input.split(": ")[1].split(", ")
            val (xStart, xEnd) = xRange.drop(2).split("..")
            val (yStart, yEnd) = yRange.drop(2).split("..")
            return TrickShot(
                TargetArea(
                    upperLeft = Coord(x = xStart.toInt(), y = yEnd.toInt()),
                    lowerRight = Coord(x = xEnd.toInt(), y = yStart.toInt())
                )
            )
        }
    }
}

data class TargetArea(val upperLeft: Coord, val lowerRight: Coord) {
    fun contains(pos: Coord) =
        pos.x in (upperLeft.x..lowerRight.x) && pos.y in (lowerRight.y..upperLeft.y)
}

