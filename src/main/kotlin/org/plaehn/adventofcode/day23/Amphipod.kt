package org.plaehn.adventofcode.day23

import java.util.*

class Amphipod(private val initialBurrow: Burrow) {

    fun computeLeastEnergyToOrganizeAmphipods(): Int {
        val candidates = PriorityQueue<BurrowWithCost>().apply { add(BurrowWithCost(initialBurrow, 0)) }
        val visited = mutableSetOf<BurrowWithCost>()
        val currentCosts = mutableMapOf<Burrow, Int>().withDefault { Int.MAX_VALUE }

        while (candidates.isNotEmpty()) {
            val current = candidates.poll()
            visited.add(current)
            current.burrow
                .enumerateLegalMoves()
                .filter { !visited.contains(it) }
                .forEach { next ->
                    val newCost = current.cost + next.cost
                    if (newCost < currentCosts.getValue(next.burrow)) {
                        currentCosts[next.burrow] = newCost
                        candidates.add(BurrowWithCost(next.burrow, newCost))
                    }
                }
        }
        return currentCosts.entries.first { it.key.isSolution() }.value
    }

    companion object {
        fun fromInputLines(inputList: List<String>) = Amphipod(Burrow.fromInputLines(inputList))
    }
}

data class BurrowWithCost(val burrow: Burrow, val cost: Int) : Comparable<BurrowWithCost> {
    override fun compareTo(other: BurrowWithCost) = cost.compareTo(other.cost)
}

internal fun Char.isOpenSpace(): Boolean = this == '.'

internal fun Char.isAmphipod(): Boolean = this == 'A' || this == 'B' || this == 'C' || this == 'D'

internal fun Int.isHallwayIndexAboveRoom() = this == 2 || this == 4 || this == 6 || this == 8
