package com.example.oop_2

// cant create object from abstract class
// but we can create sub class from abstract class
abstract class Mammal(private val name: String, private val origin: String, private val weight: Double){

    // abstract properties must be overridden by subclass
    abstract val maxSpeed: Double

    // abstract method must be overridden by subclass
    abstract fun run()
    abstract fun breath()

    // non abstract method
    fun displayDetails() {
        println("Name: $name, Origin: $origin, Weight: $weight, Max Speed: $maxSpeed")
    }
}

class Human(val name: String, val origin: String, val weight: Double,
            override val maxSpeed: Double): Mammal(name, origin, weight){

    override fun run(){
        // Code to run
        println("Runs on two legs")
    }

    override fun breath() {
        // Code to breath
        println("Breath through mouth or nose")
    }
}

class Elephant(val name: String, val origin: String, val weight: Double, override val maxSpeed: Double): Mammal(name, origin, weight){

    override fun run() {
        println("Run on four legs")
    }

    override fun breath() {
        println("Breath through the trunk")
    }
}


fun main() {
    var human = Human("Ganda", "Indonesia", 70.0, 20.0)
    human.breath()
    human.run()
    human.displayDetails()

    var elephant = Elephant("Rosy", "India", 5400.0, 25.0)
    elephant.run()
    elephant.breath()
    elephant.displayDetails()

    // cant create object from abstract class
    // val mammal = Mammal("Mammal", "India", 100.0, 27.0)
}