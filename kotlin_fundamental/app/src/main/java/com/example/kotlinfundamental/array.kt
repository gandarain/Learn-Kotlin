package com.example.kotlinfundamental

fun main() {
    // val numbers: IntArray = intArrayOf(1, 2, 3, 4, 5, 6)
    // val numbers = intArrayOf(1, 2, 3, 4, 5, 6)
    val numbers = arrayOf(1, 2, 3, 4, 5, 6)
    println(numbers.contentToString())

    for(element in numbers){
        print("$element+2 = ${element+2} ")
    }
    println()

    println(numbers[3])

    println("Initial values: ${numbers.contentToString()}")
    numbers[0] = 6
    numbers[1] = 5
    numbers[4] = 2
    numbers[5] = 1
    println("Changed values: ${numbers.contentToString()}")

    val days = arrayOf("Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat")
    println()
    println(days.contentToString())

    val fruitList = arrayOf(Fruit("Apple", 2.5), Fruit("Grape", 3.5))
    println(fruitList.contentToString())

    println()
    for (fruit in fruitList){
        println("${fruit.name}")
    }
    println()
    for (index in fruitList.indices){
        println("${fruitList[index].name} is in index $index")
    }

    println()
    val mixed = arrayOf("Sun", "Mon", "Tues", "Wed", 1, 2, 3, Fruit("Apple", 2.5))
    println(mixed.contentToString())
}

data class Fruit(val name: String, val price: Double)