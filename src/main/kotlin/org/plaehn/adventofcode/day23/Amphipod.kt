package org.plaehn.adventofcode.day23

class Amphipod(private val initialBurrow: Burrow) {

    fun computeLeastEnergyToOrganizeAmphipods(): Int {
        println(initialBurrow)
        TODO()
    }

    companion object {
        fun fromInputLines(inputList: List<String>): Amphipod {
            val hallwayLength = inputList.drop(1).first().length - 2
            val roomsTopDown = inputList.drop(2).take(2).map { roomLine ->
                roomLine.filter { it != '#' && it != ' ' }
            }
            val rooms = roomsTopDown.first()
                .zip(roomsTopDown.last())
                .map { Room(it.first, it.second) }
            val hallway = Hallway(List(hallwayLength) { '.' })
            return Amphipod(Burrow(hallway, rooms))
        }
    }
}

data class Burrow(val hallway: Hallway, val rooms: List<Room>) {

    override fun toString(): String {
        var str = '#'.repeat(hallway.size + 2) + '\n'
        str += "#$hallway#\n"
        str += '#'.repeat(3)
        str += rooms.joinToString("") { it.upper + "#" } + "##\n"
        str += "  #" + rooms.joinToString("") { it.lower + "#" } + "##\n"
        str += "  " + '#'.repeat(hallway.size - 2) + "  \n"
        return str
    }

}

private fun Char.repeat(number: Int) = (1..number).map { this }.joinToString("")

data class Hallway(val spots: List<Char>) {
    val size = spots.size

    override fun toString() = spots.joinToString("")
}

data class Room(val upper: Char, val lower: Char)
