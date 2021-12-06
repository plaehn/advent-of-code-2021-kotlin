package org.plaehn.adventofcode.day6

class LanternFishCounter(
    private val initialLanternFishes: List<Int>
) {

    fun computeNumberOfLanternFishesAfter(days: Int): Int {
        var fishes = initialLanternFishes
        repeat(days) {
            fishes = computeNext(fishes)
        }
        return fishes.size
    }

    private fun computeNext(fishes: List<Int>) =
        sequence {
            fishes.forEach { age ->
                if (age == 0) {
                    yield(6)
                    yield(8)
                } else {
                    yield(age - 1)
                }
            }
        }.toList()

    companion object {
        fun fromInputString(input: String) =
            LanternFishCounter(
                input
                    .split(",")
                    .map { it.trim() }
                    .map { it.toInt() }
            )
    }
}
