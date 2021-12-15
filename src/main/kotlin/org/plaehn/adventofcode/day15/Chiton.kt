package org.plaehn.adventofcode.day15

import com.google.common.graph.ImmutableValueGraph
import com.google.common.graph.MutableValueGraph
import com.google.common.graph.ValueGraph
import com.google.common.graph.ValueGraphBuilder
import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Dijkstra.computeShortestPathTree
import org.plaehn.adventofcode.common.Dijkstra.shortestPath
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
            shortestPathTree = computeShortestPathTree(graph, Coord(0, 0)),
            start = Coord(0, 0),
            end = Coord(riskLevelMap.width() - 1, riskLevelMap.height() - 1)
        ).drop(1).sumOf { riskLevelMap[it.y][it.x] }

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
