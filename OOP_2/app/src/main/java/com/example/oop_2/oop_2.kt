package com.example.oop_2

fun main () {
    var ganda = Person("Ganda Rain", "Panjaitan", 25)
    ganda.hobby = "Futsal"
    ganda.age = 26
    println("Ganda is ${ganda.age} years old")
    ganda.stateHobby()

    var john = Person()
    john.hobby = "Play Video Games"
    john.stateHobby()

    var johnPetterson = Person(lastName = "Petterson")
    johnPetterson.sayHello("Adele")
}

class Person(var firstName: String = "John", var lastName: String = "Doe"){
    // Member Variables - Properties
    var age: Int? = null
    var hobby: String = "Making Love"

    // Initializer Block
    init {
        println("Initialized a new person object with " +
            "first name is ${this.firstName} and last name is ${this.lastName}"
        )
    }

    // Member Secondary Constructor
    constructor(firstName: String, lastName: String, age: Int): this(firstName, lastName){
        this.age = age
        println("Initialized a new person object with " +
                "first name is ${this.firstName} and last name is ${this.lastName}" +
                "and age is ${this.age}"
        )
    }

    // Member Functions - Methods
    fun sayHello(name: String): Unit {
        println("Hello $name, my name is ${this.firstName}")
    }

    fun stateHobby() {
        println("${this.firstName}\'s hobby is ${this.hobby}")
    }
}