package org.plaehn.adventofcode.common

import com.google.common.graph.ValueGraph

object Dijkstra {

    fun <N : Any> computeShortestPathTree(graph: ValueGraph<N, Int>, start: N): Map<N, N?> {
        val visited: MutableSet<N> = mutableSetOf()

        val delta = graph.nodes().associateWith { Int.MAX_VALUE }.toMutableMap()
        delta[start] = 0

        val previous: MutableMap<N, N?> = graph.nodes().associateWith { null }.toMutableMap()

        while (visited != graph.nodes()) {
            val node = delta
                .filter { !visited.contains(it.key) }
                .minByOrNull { it.value }!!
                .key

            graph.successors(node).minus(visited).forEach { neighbor ->
                val newPath = delta.getValue(node) + graph.edgeValue(node, neighbor!!).get()

                if (newPath < delta.getValue(neighbor)) {
                    delta[neighbor] = newPath
                    previous[neighbor] = node
                }
            }

            visited.add(node)
        }

        return previous.toMap()
    }

    fun <N : Any> shortestPath(shortestPathTree: Map<N, N?>, start: N, end: N): List<N> {

        fun pathTo(start: N, end: N): List<N> {
            if (shortestPathTree[end] == null) return listOf(end)
            return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
        }

        return pathTo(start, end)
    }
}