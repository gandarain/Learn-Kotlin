package com.example.kotlinfundamental

fun main() {
    val months = listOf<String>("January", "February", "March")
    val anyTypes = listOf<Any>(1, 2, 3, true, false, "String")
    println(anyTypes.size)
    println(months[1])

    println()
    for (month in months){
        println(month)
    }
    println()

    val additionalMonths = months.toMutableList()
    val newMonths = arrayOf("April", "May", "June")
    val subNewMonths = arrayOf("July", "August", "September", "October", "November", "December")
    additionalMonths.addAll(newMonths)
    additionalMonths.addAll(subNewMonths)
    println(additionalMonths)

    println()
    val days = mutableListOf<String>("Mon", "Tue", "Wed")
    days.add("Thu")
    days[2] = "Sunday"
    days.removeAt(1)
    println(days)

    val removedList = mutableListOf<String>("Mon", "Wed")
    days.removeAll(removedList)
    println(days)
    days.removeAll(days)
    println(days)
}