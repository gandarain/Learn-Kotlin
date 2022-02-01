package com.example.kotlinbasics

fun main(){
    // Var & Val
    // var can be overwritten
    println("Var & val")
    var myName = "Ganda Rain Panjaitan"
    myName = "Ruth"
    println("Var " + myName)

    // val can't be overwritten
    val name = "Cristiano Ronaldo"
    println("Val " + name)



    // Data Type
    // type int
    println()
    println("Data Type")
    var myAge: Int = 31

    // type float
    var myFloat: Float = 13.37F

    // type double
    var myDouble: Double = 13.37

    // type boolean
    var isSunny: Boolean = true
    isSunny = false

    // type char
    var letterChar: Char = 'A'
    val digitChar: Char = '1'

    // type string
    val myStr: String = "Frank"
    val firstChar: Char = myStr[0]
    val lastChar: Char = myStr[myStr.length - 1]
    println("String Interpolation")
    println("First character of myStr is $firstChar, last char is $lastChar, and length is ${myStr.length}")

}