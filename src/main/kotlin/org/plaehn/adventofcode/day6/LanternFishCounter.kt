package org.plaehn.adventofcode.day6

class LanternFishCounter(
    initialLanternFishes: List<Int>
) {

    private val age2Count = Array(9) { 0L }

    init {
        initialLanternFishes.forEach {
            age2Count[it]++
        }
    }

    fun computeNumberOfLanternFishesAfter(days: Int): Long {
        repeat(days) {
            computeNext()
        }
        return age2Count.sum()
    }

    private fun computeNext() {
        val spawnCount = age2Count[0]
        for (age in 0..7) {
            age2Count[age] = age2Count[age + 1]
        }
        age2Count[6] = spawnCount + age2Count[6]
        age2Count[8] = spawnCount
    }

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
