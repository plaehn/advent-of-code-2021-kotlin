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

    fun computeLowestTotalRisk(): Int {
        val graph = buildGraph(riskLevelMap)
        val shortestPathTree = computeShortestPathTree(graph, Coord(0, 0))
        val shortestPath = shortestPath(
            shortestPathTree = shortestPathTree,
            start = Coord(0, 0),
            end = Coord(riskLevelMap.width() - 1, riskLevelMap.height() - 1)
        )
        return shortestPath.drop(1).sumOf { riskLevelMap[it.y][it.x] }
    }

    private fun buildGraph(riskLevelMap: Matrix<Int>): ValueGraph<Coord, Int> {
        val mutableGraph: MutableValueGraph<Coord, Int> = ValueGraphBuilder
            .directed()
            .expectedNodeCount(riskLevelMap.height() * this.riskLevelMap.width())
            .build()
        riskLevelMap.toMap().forEach { (coord, risk) ->
            mutableGraph.addNode(coord)
            riskLevelMap.neighbors(coord).forEach { neighbor ->
                mutableGraph.addNode(neighbor)
                mutableGraph.putEdgeValue(coord, neighbor, risk)
            }
        }
        return ImmutableValueGraph.copyOf(mutableGraph)
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
