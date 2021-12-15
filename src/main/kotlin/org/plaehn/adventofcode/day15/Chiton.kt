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
        fun fromInputLines(
            inputLines: List<String>,
            tileCount: Int = 1
        ): Chiton {
            val rows: List<List<Int>> =
                inputLines
                    .map { inputLine -> computeTileRow(tileCount, inputLine) }

            val tileRows: List<List<Int>> = (0 until tileCount).fold(emptyList()) { tileRow, tileNumber ->
                tileRow + rows.map { row ->
                    row.map { (it + tileNumber - 1) % 9 + 1 }
                }

            }

            val matrix = Matrix.fromRows(rows = tileRows, defaultValue = 0)

            return Chiton(
                matrix
            )
        }

        private fun computeTileRow(tileCount: Int, inputLine: String): List<Int> =
            (0 until tileCount).fold(emptyList()) { row, tileNumber ->
                row + inputLine.map {
                    (it.digitToInt() + tileNumber - 1) % 9 + 1
                }
            }
    }
}
