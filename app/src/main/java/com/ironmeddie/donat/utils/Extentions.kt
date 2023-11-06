package com.ironmeddie.donat.utils

fun String.toListOfStrings(): List<String> {
    val cat = this.substringAfter("[").substringBeforeLast("]").replace(" ", "")
    if (cat.contains(",")) return cat.split(",")
    else if (cat.isBlank()) return emptyList()
    else return listOf(cat)

}