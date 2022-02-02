package com.example.kotlinbasics

fun main(){
    val red = "\u001b[31m"
    val reset = "\u001b[0m"
    // Var & Val
    // var can be overwritten
    println(red + "Var & val" + reset)
    var myName = "Ganda Rain Panjaitan"
    myName = "Lionel Messi"
    println("Var " + myName)

    // val can't be overwritten
    val name = "Cristiano Ronaldo"
    println("Val " + name)



    // Data Type
    // int, string, char, float, double
    println("\n"+ red + "Data Type" + reset)
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


    // Arithmetic Operators
    // (+, -, /, %)
    println("\n" + red + "Arithmetic Operators" + reset)

    var result = 5 + 3
    result /= 2

    val a = 5
    val b = 3
    // % ==> a - a / b
    result = a % b
    println("Result $result")

    val intA = 5
    val intB = 3
    println("Int ${intA / intB}")

    var resultDouble: Double
    val valueA = 5.3
    val valueB = 3
    resultDouble = valueA / valueB
    println("Result Double $resultDouble")


    // Comparison operators
    // (==, !=, <, >, <=, >=)
    println("\n" + red + "Comparison Operators" + reset)
    val isEqual = 5 == 3
    println("Is equal is $isEqual")

    val isNotEqual = 5 != 5
    println("Is not equal is $isNotEqual")

    println("Is 5 greater 3 ${5 > 3}")
    println("Is 5 lower equal 3 ${5 <= 3}")
    println("Is 5 greater equal 3 ${5 >= 3}")

    // Assignment operators
    // (+=, -=, *=, /=, %=)
    println("\n" + red + "Assignment Operators" + reset)
    var myNum = 5
    myNum += 3
    println("My num is $myNum")
    myNum *= 4
    println("My num is $myNum")

    // Increment & decrement operators
    // (++, --)
    println("\n" + red + "Increment & Decrement Operators" + reset)
    myNum ++
    // println("My Num is $myNum")
    // myNum --
    // println("My num is $myNum")

    println("My num is ${myNum++}")
    println("My num is ${++myNum}")
    println("My num is ${--myNum}")

    // If statement
    println("\n" + red + "If Statement" + reset)

    var heightPerson1 = 170
    var heightPerson2 = 189
    if(heightPerson1 > heightPerson2){
        println("Person one is higher than person two")
    } else if (heightPerson1 == heightPerson2) {
        println("Person two and one is same high")
    } else {
        println("Person two is higher than person one")
    }

    val age = 31
    if (age >= 21){
        println("Now you may drink in the US")
    } else if (age >= 18){
        println("You may vote now")
    } else if (age >= 16) {
        println("You may drive now")
    } else {
        println("You are too young")
    }

    var personName: String = "Denis"
    if (personName == "Denis"){
        println("Welcome home Denis")
    } else {
        println("Who are you?")
    }

    var isRainny = true
    if (isRainny)
        println("It's rainny")

    // When expression
    println("\n" + red + "When Expression" + reset)

    var season = 3
    when(season){
        1 -> println("Spring")
        2 -> println("Summer")
        3 -> {
            println("Fall")
            println("Autumn")
        }
        4 -> println("Winter")
        else -> println("Invalid season")
    }

    var month = 3
    when(month){
        in 3..5 -> println("Spring")
        in 6..8 -> println("Summer")
        in 9..11 -> println("Fall")
        // 12, 1, 2 -> println("Winter")
        in 12 downTo 2 -> println("Winter")
        else -> println("Invalid month")
    }

    when(age){
        16, 17 -> println("You may drive now")
        in 18..20 -> println("You may vote now")
        in 21..150 -> println("Now you may drink in the US")
        else -> println("You are too young")
    }

    var x: Any = 13.37
    when(x){
        is Int -> println("$x is an Int")
        is Double -> println("$x is a Double")
        is String -> println("$x is a String")
        else -> println("$x is none the above")
    }

    // While Loop
    println("\n" + red + "While Loop" + reset)

    var value: Int = 1
    while (value <= 10){
        print("$value, ")
        value++
    }
    println("\nWhile loop is done")

    var descending: Int = 100
    while (descending >= 0){
        print("$descending, ")
        descending -= 2
    }
    println("\nWhile loop is done")

    var feltTemp = "cold"
    var roomTemp = 10
    while (feltTemp == "cold"){
        roomTemp++
        if (roomTemp >= 20){
            feltTemp = "comfy"
            println("It's comfy now")
        }
    }

    // Do While Loop
    // Do while is better than while
    println("\n" + red + "Do While Loop" + reset)
    println(red + "Do while is better than while" + reset)

    value = 1
    do {
        print("$value, ")
        value++
    } while (value <= 10)
    println("\nDo while loop is done")



    // For loops
    println("\n" + red + "For loops" + reset)

    for (num in 1..10){
        print("$num, ")
    }
    println()

    for (i in 1 until 10){
        print("$i, ")
    }
    println()

    // for (i in 10.downTo(1).step(2))
    for (i in 10 downTo 1 step 2){
        print("$i, ")
    }
    println()

    // Write a for loop that runs from 0 to 10000
    // Once it's at 9001 it should write "IT'S OVER 9000!!!"
    for (num in 1..10000){
        if (num == 9001) print("IT'S OVER 9000!!! $num")
    }

    // Write a while loop that checks the humidity (not the humidityLevel).
    // The variable humidityLevel starts at 80. The variable humidity is initialized with "humid".
    // If it is "humid" then it should reduce the "humidityLevel" by 5 and print "humidity decreased"
    // Once the humidityLevel is below 60 it should print "it's comfy now" and set the humidity to "comfy"
    var humidity = "humid"
    var humidityLevel = 80

    do {
        humidityLevel -= 5
        println("humidity decreased")

        if (humidityLevel < 60) {
            println("it's comfy now")
            humidity = "comfy"
        }
    } while (humidity == "humid")



    // Break & Continue
    println("\n" + red + "Break & Continue" + reset)

    for(i in 1..20){
        print("$i ,")
        if (i/2 == 5){
            break
        }
    }
    println("Done with the loop")

    for(i in 1..20){
        // 11/2 ==> 5.5 but because its int so the result is 5
        if (i/2 == 5){
            continue
        }
        print("$i ,")
    }
    println("Done with the loop")



    // Function
    println("\n" + red + "Function" + reset)
    myFunction()
    var res = addUp(1, 2)
    println("Res $res")

    var resAvg = average(5.0, 4.1)
    println("Avg $resAvg")



    // Nullable
    println("\n" + red + "Nullable" + reset)

    var basicName = "John"
    var nullAbleName: String? = "Adam"
    nullAbleName = null

    var len1 = basicName.length
    var len2 = nullAbleName?.length
    println("len1 $len1")
    // println("len2 $len2")
    nullAbleName?.let {println("len2 $len2")}


    // Elvis operator & Not null assertion
    // ?: => Elvis operator
    // !! => null assertion
    println("\n" + red + "Elvis operator & Not null assertion" + reset)
    println(red + "?: => Elvis operator" + reset)
    println(red + "!! => null assertion" + reset)
    val naming = nullAbleName ?: "Guest"
    println("Naming $naming")

    // var nullValue: String? = null
    var nullValue: String = "Value"
    println("${nullValue!!.toLowerCase()}")
}

fun myFunction(){
    println("Called from my function")
}

// Function with parameter
fun addUp(a: Int, b: Int): Int{
    return a + b
}

fun average(a: Double, b: Double): Double {
    return (a+b) / 2
}