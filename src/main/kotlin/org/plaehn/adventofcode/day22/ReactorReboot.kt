package org.plaehn.adventofcode.day22

class ReactorReboot(private val cuboids: List<Cuboid>) {

    fun computeNumberOfCubesThatAreOnAfterReboot(): Long {
        val volumes = mutableListOf<Cuboid>()
        cuboids.forEach { cuboid ->
            volumes.addAll(volumes.mapNotNull { it intersect cuboid })
            if (cuboid.on) volumes.add(cuboid)
        }
        return volumes.sumOf { it.volume() }
    }

    companion object {
        fun fromInputLines(inputList: List<String>, limit: IntRange? = null) =
            ReactorReboot(inputList
                              .map { Cuboid.fromInputString(it) }
                              .filter { it.isWithin(limit) }
            )
    }
}

data class Cuboid(val on: Boolean, val x: IntRange, val y: IntRange, val z: IntRange) {

    infix fun intersect(other: Cuboid) =
        if (!intersects(other)) {
            null
        } else {
            Cuboid(!on, x intersect other.x, y intersect other.y, z intersect other.z)
        }

    private fun intersects(other: Cuboid) =
        x intersects other.x && y intersects other.y && z intersects other.z

    fun isWithin(limit: IntRange?) = limit == null || (limit intersects x && limit intersects y && limit intersects z)

    fun volume() = x.size() * y.size() * z.size() * if (on) 1 else -1

    companion object {
        fun fromInputString(input: String): Cuboid {
            input.split(" ").let { (onOrOffStr, rangesStr) ->
                val ranges = rangesStr
                    .split(",")
                    .map {
                        it.split("=")[1].split("..").let { (start, end) -> start.toInt()..end.toInt() }
                    }
                return Cuboid(onOrOffStr == "on", ranges[0], ranges[1], ranges[2])
            }
        }
    }
}

private infix fun IntRange.intersects(other: IntRange) = first <= other.last && last >= other.first

private infix fun IntRange.intersect(other: IntRange) = maxOf(first, other.first)..minOf(last, other.last)

private fun IntRange.size() = (last - first + 1).toLong()

