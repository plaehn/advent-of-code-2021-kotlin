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

class Board(rows: List<List<BingoNumber>>) {

    private val matrix = Matrix.fromRows(rows, BingoNumber(-1))

    fun mark(number: Int) {
        matrix.replaceAll { value ->
            if (value.isMatch(number)) {
                value.mark()
            } else {
                value
            }
        }
    }

    fun isWinning() = matrix.rows().any(::allMarked) || matrix.columns().any(::allMarked)

    private fun allMarked(values: List<BingoNumber>) = values.all { it.isMarked }

    fun findAllUnmarkedNumbers() = matrix.values().filter { !it.isMarked }.map { it.number }

    companion object {
        fun fromInput(input: String) =
            Board(input.lines().map { line -> toRow(line) })

        private fun toRow(line: String) =
            line.split(' ')
                .filter { it.isNotBlank() }
                .map {
                    BingoNumber(it.trim().toInt())
                }
    }
}

data class BingoNumber(val number: Int, val isMarked: Boolean = false) {

    fun mark(): BingoNumber = BingoNumber(number, true)

    fun isMatch(number: Int) = this.number == number && !isMarked
}