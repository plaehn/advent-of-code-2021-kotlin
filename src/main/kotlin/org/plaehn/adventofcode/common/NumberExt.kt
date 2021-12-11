package org.plaehn.adventofcode.common

fun Iterable<Int>.product(): Int = this.reduce(Int::times)
