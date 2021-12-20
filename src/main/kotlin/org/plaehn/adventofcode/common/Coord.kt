package org.plaehn.adventofcode.common

import kotlin.math.absoluteValue

data class Coord(val x: Int, val y: Int, val z: Int = 0) {

    operator fun plus(summand: Coord) = Coord(x + summand.x, y + summand.y, z + summand.z)

    operator fun minus(subtrahend: Coord) = Coord(x - subtrahend.x, y - subtrahend.y, z - subtrahend.z)

    operator fun times(factor: Coord): Coord = Coord(x * factor.x, y * factor.y, z * factor.z)

    fun isCenter() = x == 0 && y == 0 && z == 0

    override fun toString() = "$x,$y,$z"
    
    fun manhattanDistanceTo(other: Coord) =
        (x - other.x).absoluteValue + (y - other.y).absoluteValue + (z - other.z).absoluteValue

    companion object {
        fun Companion.fromString(input: String) =
            input.split(",").map { it.toInt() }.run {
                Coord(this[0], this[1], this.getOrElse(2) { 0 })
            }
    }
}