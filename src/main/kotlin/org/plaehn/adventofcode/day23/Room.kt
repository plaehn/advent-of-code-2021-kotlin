package org.plaehn.adventofcode.day23

data class Room(val spots: List<Char>) {

    fun topmostNonEmptyWithIndex() =
        spots.mapIndexed { index, ch -> ch to index }.first { !it.first.isOpenSpace() }

    fun isNotEmpty() = spots.any { !it.isOpenSpace() }

    fun hasRoomForAmphipodAtIndex(amphipod: Char, roomIndex: Int): Int? {
        val isCorrectPosition = amphipodLivingInRoom(roomIndex) == amphipod
        if (!isCorrectPosition) return null
        val emptySpots = spots.takeWhile { it.isOpenSpace() }
        if (spots.drop(emptySpots.size).all { it == amphipod }) {
            return emptySpots.size - 1
        }
        return null
    }

    fun canRemoveTopmostAmphipod(roomIndex: Int): Boolean {
        val amphipodLivingInThisRoom = amphipodLivingInRoom(roomIndex)
        return spots.filter { it != amphipodLivingInThisRoom }.any { it.isAmphipod() }
    }

    fun containsOnlyAmphipodsLivingHere(roomIndex: Int) =
        spots.all { it == amphipodLivingInRoom(roomIndex) }

    private fun amphipodLivingInRoom(roomIndex: Int) = 'A' + roomIndex
}