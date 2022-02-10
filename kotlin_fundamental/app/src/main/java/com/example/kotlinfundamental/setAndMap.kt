package com.example.kotlinfundamental

fun main() {
    // set of ignore the duplicate
    val fruits = setOf<String>("Orange", "Apple", "Mango", "Grape", "Apple", "Orange")
    println(fruits.size)
    println(fruits)
    println(fruits.toSortedSet())

    println()
    val newFruits = fruits.toMutableList()
    newFruits.add("Water Melon")
    newFruits.add("Pear")
    println(newFruits)
    println(newFruits.elementAt(4))

    println()
    println("Map")
    val daysOfTheWeek = mapOf(1 to "Monday", 2 to "Tuesday", 3 to "Wednesday")
    println(daysOfTheWeek[2])

    for (key in daysOfTheWeek.keys){
        println("$key is to ${daysOfTheWeek[key]}")
    }

    println()
    val fruitAge = mapOf("Favorite" to Fruits("Grape", 2.5), "OK" to Fruits("Apple", 1.0))
    println(fruitAge)

    println()
    val newDaysOfWeek = daysOfTheWeek.toMutableMap()
    newDaysOfWeek[4] = "Thursday"
    newDaysOfWeek[5] = "Friday"
    println(newDaysOfWeek.toSortedMap())
}

data class Fruits(val name: String, val price: Double)