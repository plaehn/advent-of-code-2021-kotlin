package org.plaehn.adventofcode.day19

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.fromString
import org.plaehn.adventofcode.common.combinations
import org.plaehn.adventofcode.common.groupByBlankLines
import kotlin.math.absoluteValue

class BeaconScanner(private val scanners: List<Set<Coord>>) {

    fun computeMaximumManhattanDistance() =
        reassembleMap()
            .foundScannerPositions
            .combinations(ofSize = 2)
            .maxOfOrNull { computeManhattanDistance(it.first(), it.last()) }!!

    private fun computeManhattanDistance(first: Coord, second: Coord) =
        (first.x - second.x).absoluteValue + (first.y - second.y).absoluteValue + (first.z - second.z).absoluteValue

    fun computeNumberOfBeacons() = reassembleMap().foundBeacons.size

    private fun reassembleMap(): ReassembledMap {
        val foundBeacons = scanners.first().toMutableSet()
        val foundScannerPositions = mutableSetOf(Coord(0, 0, 0))

        val remaining = ArrayDeque<Set<Coord>>().apply { addAll(scanners.drop(1)) }
        while (remaining.isNotEmpty()) {
            val candidate = remaining.removeFirst()
            val transformed = findFirstOverlappingAndTransformedOrNull(foundBeacons, candidate)
            if (transformed != null) {
                foundBeacons.addAll(transformed.beacons)
                foundScannerPositions.add(transformed.position)
            } else {
                remaining.add(candidate)
            }
        }
        return ReassembledMap(foundBeacons, foundScannerPositions)
    }

    private fun findFirstOverlappingAndTransformedOrNull(foundBeacons: Set<Coord>, candidate: Set<Coord>) =
        (0..5).firstNotNullOfOrNull { face ->
            (0..3).firstNotNullOfOrNull { rotation ->
                val reorientedCandidate = candidate.map { it.face(face).rotate(rotation) }.toSet()
                foundBeacons.firstNotNullOfOrNull { b1 ->
                    reorientedCandidate.firstNotNullOfOrNull { b2 ->
                        val offset = b1 - b2
                        val movedCandidate = reorientedCandidate.map { it + offset }.toSet()
                        if (movedCandidate.intersect(foundBeacons).size >= 12) {
                            TransformedScanner(offset, movedCandidate)
                        } else {
                            null
                        }
                    }
                }
            }
        }

    private fun Coord.face(facing: Int): Coord =
        when (facing) {
            0 -> this
            1 -> Coord(x, -y, -z)
            2 -> Coord(x, -z, y)
            3 -> Coord(-y, -z, x)
            4 -> Coord(y, -z, -x)
            5 -> Coord(-x, -z, -y)
            else -> error("Invalid facing")
        }


    private fun Coord.rotate(rotation: Int): Coord =
        when (rotation) {
            0 -> this
            1 -> Coord(-y, x, z)
            2 -> Coord(-x, -y, z)
            3 -> Coord(y, -x, z)
            else -> error("Invalid rotation")
        }

    companion object {
        fun fromInput(input: String) =
            BeaconScanner(input.groupByBlankLines().map { scanner ->
                scanner.lines().drop(1).map { Coord.fromString(it) }.toSet()
            })
    }
}

data class TransformedScanner(val position: Coord, val beacons: Set<Coord>)

data class ReassembledMap(val foundBeacons: Set<Coord>, val foundScannerPositions: Set<Coord>)