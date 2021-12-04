package org.plaehn.adventofcode.day4

import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.groupByBlankLines

class Bingo(
    private val numbers: List<Int>,
    private val boards: List<Board>
) {

    fun computeScoreOfFirstWinningBoard(): Int {
        val (winningBoard, lastNumber) = findWinningBoardsAndLastNumber().first()
        return lastNumber * winningBoard.findAllUnmarkedNumbers().sum()
    }

    fun computeScoreOfLastWinningBoard(): Int {
        val (winningBoard, lastNumber) = findWinningBoardsAndLastNumber().last()
        return lastNumber * winningBoard.findAllUnmarkedNumbers().sum()
    }

    private fun findWinningBoardsAndLastNumber(): Sequence<Pair<Board, Int>> =
        sequence {
            numbers.forEach { number ->
                boards
                    .filter { !it.isWinning() }
                    .forEach { board ->
                        board.mark(number)
                        if (board.isWinning()) {
                            yield(Pair(board, number))
                        }
                    }
            }
        }

    companion object {
        fun fromLines(input: String): Bingo {
            val groups = input.groupByBlankLines()
            val numbers = groups.first().split(",").map { it.toInt() }
            val boards = groups.drop(1).map { Board.fromInput(it) }
            return Bingo(numbers, boards)
        }
    }
}

class Board(rows: List<List<Int>>) {

    private val matrix = Matrix.fromRows(rows)

    fun mark(number: Int) {
        matrix.replaceAll { value ->
            if (value == number && !value.isMarked()) {
                value.mark()
            } else {
                value
            }
        }
    }

    fun isWinning() = matrix.rows().any(::allMarked) || matrix.columns().any(::allMarked)

    private fun allMarked(values: Array<Int>) = values.all { it.isMarked() }

    fun findAllUnmarkedNumbers() = matrix.values().filter { !it.isMarked() }

    private fun Int.isMarked() = this < 0

    private fun Int.mark() = -1 * this

    companion object {
        fun fromInput(input: String) =
            Board(input.lines().map { line -> toRow(line) })

        private fun toRow(line: String) =
            line.split(' ')
                .filter { it.isNotBlank() }
                .map {
                    it.trim().toInt()
                }
    }
}
