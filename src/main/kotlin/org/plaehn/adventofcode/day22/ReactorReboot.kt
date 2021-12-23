package org.plaehn.adventofcode.day22

import org.plaehn.adventofcode.common.Coord

class ReactorReboot(private val rebootSteps: List<RebootStep>) {

    private val cubesThatAreOn: MutableSet<Coord> = mutableSetOf()

    fun computeNumberOfCubesThatAreOnAfterReboot(): Int {
        rebootSteps.forEach { rebootStep ->
            rebootStep.cuboid.forEach { coord ->
                if (rebootStep.on) {
                    cubesThatAreOn.add(coord)
                } else {
                    cubesThatAreOn.remove(coord)
                }
            }
        }
        return cubesThatAreOn.size
    }

    companion object {
        fun fromInputLines(inputList: List<String>, limit: IntRange) =
            ReactorReboot(inputList
                              .map { RebootStep.fromInputLine(it) }
                              .filter { it.cuboid.isWithin(limit) }
            )
    }
}

data class RebootStep(val on: Boolean, val cuboid: Cuboid) {

    companion object {
        fun fromInputLine(line: String) =
            line.split(" ").let { (onOrOffStr, cuboidStr) ->
                RebootStep(onOrOffStr == "on", Cuboid.fromInputString(cuboidStr))
            }
    }
}

data class Cuboid(val from: Coord, val to: Coord) {

    fun forEach(action: ((Coord) -> Unit)) {
        (from.x..to.x).forEach { x ->
            (from.y..to.y).forEach { y ->
                (from.z..to.z).forEach { z ->
                    action(Coord(x, y, z))
                }
            }
        }
    }

    fun contains(coord: Coord) =
        coord.x in from.x..to.x && coord.y in from.y..to.y && coord.z in from.z..to.z

    fun isWithin(limit: IntRange) =
        limit.contains(from.x) && limit.contains(from.y) && limit.contains(from.z) &&
                limit.contains(to.x) && limit.contains(to.y) && limit.contains(to.z)

    companion object {
        fun fromInputString(input: String): Cuboid {
            val startEndPairs = input
                .split(",")
                .map {
                    it.split("=")[1].split("..").let { (start, end) -> start.toInt() to end.toInt() }
                }
            val upperLeft = Coord.fromList(startEndPairs.map { it.first })
            val lowerRight = Coord.fromList(startEndPairs.map { it.second })
            return Cuboid(upperLeft, lowerRight)
        }
    }
}

