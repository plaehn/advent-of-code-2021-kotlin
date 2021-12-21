package org.plaehn.adventofcode.day19

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.fromString
import org.plaehn.adventofcode.common.combinations
import org.plaehn.adventofcode.common.groupByBlankLines
import java.util.*

class BeaconScanner(private val scanners: List<Scanner>) {

    fun computeMaximumManhattanDistance() =
        reassembleMap()
            .foundScannerPositions
            .combinations(ofSize = 2)
            .maxOfOrNull { it.first().manhattanDistanceTo(it.last()) }!!

    fun computeNumberOfBeacons() = reassembleMap().foundBeacons.size

    private fun reassembleMap(): ReassembledMap {
        val foundBeacons = scanners.first().beacons.toMutableSet()
        val foundFingerprints = scanners.first().fingerprints.toMutableSet()
        val foundScannerPositions = mutableSetOf(Coord(0, 0, 0))

        scanners.drop(1).forEach { it.computeOverlapSizeWith(foundFingerprints) }
        val remaining = PriorityQueue<Scanner>().apply { addAll(scanners.drop(1)) }
        while (remaining.isNotEmpty()) {
            val candidate = remaining.remove()

            val transformed = findFirstOverlappingAndTransformedOrNull(foundBeacons, candidate)
            if (transformed != null) {
                foundBeacons.addAll(transformed.beacons)
                foundFingerprints.addAll(transformed.fingerprints)
                foundScannerPositions.add(transformed.position)
                remaining.forEach { it.computeOverlapSizeWith(foundFingerprints) }
            } else {
                candidate.distance = 0
                remaining.add(candidate)
            }
        }
        return ReassembledMap(foundBeacons, foundScannerPositions)
    }

    private fun findFirstOverlappingAndTransformedOrNull(foundBeacons: Set<Coord>, candidate: Scanner) =
        (0..5).firstNotNullOfOrNull { face ->
            (0..3).firstNotNullOfOrNull { rotation ->
                val reorientedCandidate = candidate.beacons.map { it.face(face).rotate(rotation) }.toSet()
                foundBeacons.firstNotNullOfOrNull { b1 ->
                    reorientedCandidate.firstNotNullOfOrNull { b2 ->
                        val offset = b1 - b2
                        val movedCandidate = reorientedCandidate.map { it + offset }.toSet()
                        if (movedCandidate.intersect(foundBeacons).size >= 12) {
                            TransformedScanner(offset, movedCandidate, candidate.fingerprints)
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
                Scanner.fromInput(scanner)
            })
    }
}

data class Scanner(val beacons: Set<Coord>) : Comparable<Scanner> {
    var distance: Int = Int.MAX_VALUE

    val fingerprints = beacons.combinations(ofSize = 2).map { coordPair ->
        coordPair.first().manhattanDistanceTo(coordPair.last())
    }.toSet()

    fun computeOverlapSizeWith(foundFingerprints: Set<Int>) {
        distance = (foundFingerprints intersect fingerprints).size
    }

    companion object {
        fun fromInput(input: String) = Scanner(input.lines().drop(1).map { Coord.fromString(it) }.toSet())
    }

    override fun compareTo(other: Scanner) = other.distance - this.distance
}

data class TransformedScanner(val position: Coord, val beacons: Set<Coord>, val fingerprints: Set<Int>)

data class ReassembledMap(val foundBeacons: Set<Coord>, val foundScannerPositions: Set<Coord>)