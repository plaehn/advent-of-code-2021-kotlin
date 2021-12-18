package org.plaehn.adventofcode.common

import com.google.common.collect.Sets

fun <E> Set<E>.combinations(ofSize: Int): Set<Set<E>> = Sets.combinations(this, ofSize)
