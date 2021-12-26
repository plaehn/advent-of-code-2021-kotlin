package org.plaehn.adventofcode.day23

data class Hallway(val spots: List<Char>) {

    fun findEmptySpotsReachableFrom(startIndex: Int) =
        sequence {
            var index = startIndex
            do {
                --index
                if (index >= 0 && spots[index].isOpenSpace()) yield(index)
            } while (index >= 0 && spots[index].isOpenSpace())
            index = startIndex
            do {
                ++index
                if (index < spots.size && spots[index].isOpenSpace()) yield(index)
            } while (index < spots.size && spots[index].isOpenSpace())
        }
            .filter { !it.isHallwayIndexAboveRoom() }
            .toList()

    fun replace(indexToReplace: Int, newChar: Char) =
        Hallway(spots.mapIndexed { index, char ->
            if (index == indexToReplace) newChar else char
        })

    override fun toString() = spots.joinToString("")
}