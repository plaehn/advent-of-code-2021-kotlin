package org.plaehn.adventofcode.day19

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.fromString
import org.plaehn.adventofcode.common.groupByBlankLines

class BeaconScanner(private val scanners: List<Scanner>) {

    fun computeNumberOfBeacons(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        fun fromInput(input: String) =
            BeaconScanner(input.groupByBlankLines().map { Scanner.fromInput(it) })
    }
}

data class Scanner(val beacons: List<Coord>) {
    companion object {
        fun fromInput(input: String) =
            Scanner(input.lines().drop(1).map { Coord.fromString(it) })
    }
}