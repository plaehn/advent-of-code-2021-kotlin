package org.plaehn.adventofcode.day12

import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder


class PassagePathing(
    private val graph: Graph<String>
) {

    fun countPaths(canVisitSingleSmallCaveTwice: Boolean = false) =
        countPaths("start", setOf(), if (canVisitSingleSmallCaveTwice) null else "")

    private fun countPaths(node: String, seenSmallCaves: Set<String>, smallCaveVisitedTwice: String?): Int =
        graph
            .adjacentNodes(node)
            .sumOf { adjacentCave ->
                when {
                    adjacentCave == "start" -> 0
                    adjacentCave == "end" -> 1
                    adjacentCave.isLarge() -> countPaths(adjacentCave, seenSmallCaves, smallCaveVisitedTwice)
                    seenSmallCaves.contains(adjacentCave) && smallCaveVisitedTwice == null -> {
                        countPaths(adjacentCave, seenSmallCaves, adjacentCave)
                    }
                    !seenSmallCaves.contains(adjacentCave) -> {
                        countPaths(adjacentCave, seenSmallCaves + adjacentCave, smallCaveVisitedTwice)
                    }
                    else -> 0
                }
            }

    private fun String.isLarge() = this.all { it.isUpperCase() }

    companion object {
        fun fromInputLines(inputLines: List<String>): PassagePathing {
            val graph = GraphBuilder.undirected().build<String>()
            inputLines.forEach { line ->
                val (start, end) = line.split("-")
                graph.addNode(start)
                graph.addNode(end)
                graph.putEdge(start, end)
            }
            return PassagePathing(graph)
        }
    }
}





