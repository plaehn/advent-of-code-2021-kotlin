package org.plaehn.adventofcode.day21

class DiracDice(private val start1: Int, private val start2: Int) {

    fun solvePart1(): Int {
        val dice = Dice()
        var player1 = Player(position = start1)
        var player2 = Player(position = start2)
        do {
            player1 = player1.advance(dice.take(3).sum())
            if (player1.wins()) return player2.score * dice.numberOfRolls

            player2 = player2.advance(dice.take(3).sum())
            if (player2.wins()) return player1.score * dice.numberOfRolls
        } while (true)
    }

    companion object {
        fun fromInputLines(inputLines: List<String>) =
            inputLines.map { it.split(": ")[1].toInt() }.let { (start1, start2) -> DiracDice(start1, start2) }
    }
}

data class Player(val position: Int = 0, val score: Int = 0) {

    fun advance(sum: Int): Player {
        val newPosition = (position + sum - 1) % 10 + 1
        val newScore = score + newPosition
        return Player(newPosition, newScore)
    }

    fun wins() = score >= 1000
}

data class Dice(private var next: Int = 1, var numberOfRolls: Int = 0) {

    fun take(number: Int): List<Int> {
        numberOfRolls += number
        return (1..number).map { next() }.toList()
    }

    fun next(): Int {
        val ret = next
        next = next % 100 + 1
        return ret
    }
}