package app

import data.Handphone
import data.Laptop

fun printObject(any: Any){
    if (any is Laptop){
        println("Laptop ${any.name}")
    } else if (any is Handphone){
        println("Handphone ${any.name}")
    } else {
        println(any)
    }
}

fun printObjectWithWhen(any: Any){
    when(any){
        is Laptop -> println("Laptop ${any.name}")
        is Handphone -> println("Handphone ${any.name}")
        else -> println(any)
    }
}

fun printString(any: Any) {
    // val value = any as String
    val value = any as? String
    println(value)
}

fun main() {
    println("printObject")
    printObject("Hello")
    printObject(1)
    printObject(Laptop("Ace"))
    printObject(Handphone("Samsung"))
    println()

    println("printObjectWithWhen")
    printObjectWithWhen("Hello")
    printObjectWithWhen(1)
    printObjectWithWhen(Laptop("Ace"))
    printObjectWithWhen(Handphone("Samsung"))
    println()

    printString("Eko")
    printString(1)
}