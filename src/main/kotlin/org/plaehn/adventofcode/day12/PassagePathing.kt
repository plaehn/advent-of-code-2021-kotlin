package org.plaehn.adventofcode.day12

import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder


class PassagePathing(
    private val graph: Graph<String>
) {

    fun countPaths() = countPaths("start", setOf())

    private fun countPaths(node: String, seenSmallCaves: Set<String>): Int =
        graph
            .adjacentNodes(node)
            .sumOf { cave ->
                when {
                    cave == "start" -> 0
                    cave == "end" -> 1
                    cave.isLarge() -> countPaths(cave, seenSmallCaves)
                    !seenSmallCaves.contains(cave) -> countPaths(cave, seenSmallCaves + cave)
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





