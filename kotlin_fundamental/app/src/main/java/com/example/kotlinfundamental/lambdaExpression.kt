package com.example.kotlinfundamental

fun main() {
    var num1 = 5
    val num2 = 10
    var res = addNumber(num1, num2)
    println(res)

    // Lambda
    var sum: (Int, Int) -> Int = { a: Int, b: Int -> a + b }
    println(sum(num1, num2))
}

fun addNumber(a: Int, b: Int): Int {
    return a + b
}