package org.plaehn.adventofcode.common

fun String.tokenize(): List<String> = this.split("\\s+".toRegex()).filter { it.isNotBlank() }

fun String.countTokens(): Int = this.tokenize().count()
    
fun String.groupByBlankLines(): List<String> = this.split("\r?\n\\s*\r?\n".toRegex())

fun String.countUniqueChars(): Int = this.groupBy { it }.entries.count()

fun String.countCharsAppearing(ntimes: Int): Int = this.groupBy { it }.entries.count { ntimes == it.value.count() }
