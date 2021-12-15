package org.plaehn.adventofcode.day15

import com.google.common.graph.ImmutableValueGraph
import com.google.common.graph.MutableValueGraph
import com.google.common.graph.ValueGraph
import com.google.common.graph.ValueGraphBuilder
import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix


class Chiton(private val riskLevelMap: Matrix<Int>) {

    private val graph: ValueGraph<Coord, Int>

    init {
        val mutableGraph: MutableValueGraph<Coord, Int> = ValueGraphBuilder
            .directed()
            .expectedNodeCount(riskLevelMap.height() * riskLevelMap.width())
            .build()
        riskLevelMap.toMap().forEach { (coord, risk) ->
            mutableGraph.addNode(coord)
            riskLevelMap.neighbors(coord).forEach { neighbor ->
                mutableGraph.addNode(neighbor)
                mutableGraph.putEdgeValue(coord, neighbor, risk)
            }
        }
        graph = ImmutableValueGraph.copyOf(mutableGraph)
    }

    fun computeLowestTotalRisk() =
        shortestPath(
            shortestPathTree = dijkstra(graph, Coord(0, 0)),
            start = Coord(0, 0),
            end = Coord(riskLevelMap.width() - 1, riskLevelMap.height() - 1)
        ).drop(1).sumOf { riskLevelMap[it.y][it.x] }

    fun dijkstra(graph: ValueGraph<Coord, Int>, start: Coord): Map<Coord, Coord?> {
        val visited: MutableSet<Coord> = mutableSetOf()

        val delta = graph.nodes().associateWith { Int.MAX_VALUE }.toMutableMap()
        delta[start] = 0

        val previous: MutableMap<Coord, Coord?> = graph.nodes().associateWith { null }.toMutableMap()

        while (visited != graph.nodes()) {
            val node = delta
                .filter { !visited.contains(it.key) }
                .minByOrNull { it.value }!!
                .key

            graph.successors(node).minus(visited).forEach { neighbor ->
                val newPath = delta.getValue(node) + graph.edgeValue(node, neighbor).get()

                if (newPath < delta.getValue(neighbor)) {
                    delta[neighbor] = newPath
                    previous[neighbor] = node
                }
            }

            visited.add(node)
        }

        return previous.toMap()
    }

    fun shortestPath(shortestPathTree: Map<Coord, Coord?>, start: Coord, end: Coord): List<Coord> {

        fun pathTo(start: Coord, end: Coord): List<Coord> {
            if (shortestPathTree[end] == null) return listOf(end)
            return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
        }

        return pathTo(start, end)
    }

    companion object {
        fun fromInputLines(inputLines: List<String>) =
            Chiton(
                Matrix.fromRows(
                    rows = inputLines.map { inputLine -> inputLine.map { it.digitToInt() } },
                    defaultValue = 0
                )
            )
    }
}
