package com.example.oop_2

var c = 10

fun main() {
    myFunction(5)

    c = 12
}

// this a is parameter
fun myFunction(a: Int): Unit {
    // this a is variable
    var a = 4
    c = 15
    println("A is $a")
}