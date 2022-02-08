package com.example.oop_2

fun main() {
    var myCar = Car()
    println("Owner is ${myCar.owner}")
    println("Brand is ${myCar.brand}")
    myCar.maxSpeed = 240
    println("Max Speed is ${myCar.maxSpeed}")
    println("Model is ${myCar.myModel}")

}

class Car() {
    lateinit var owner: String

    val brand: String = "BMW"
        // Custom Getter
        get() {
            return field.toLowerCase()
        }

    var maxSpeed: Int = 250
        get() = field
        set(value) {
            field = if (value > 0) value else throw IllegalArgumentException("Max speed is not valid")
        }

    var myModel: String = "M5"
        private set

    init {
        this.myModel = "M3"
        this.owner = "Frank"
    }
}
