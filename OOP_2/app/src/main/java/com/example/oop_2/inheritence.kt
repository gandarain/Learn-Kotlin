package com.example.oop_2

// open -> open parent class
// shield -> lock parent class

open class Cars(val name: String, val brand: String){
    open var range: Double = 0.0

    fun extendRange(amount: Double) {
        if (amount > 0) {
            this.range += amount
        }
    }

    open fun drive(distance: Double) {
        println("Drove for $distance KM")
    }
}

class ElectricCar(name: String, brand: String, batteryLife: Double): Cars(name, brand){
    override var range = batteryLife * 6
    var chargerType: String = "Type 1"

    override fun drive(distance: Double){
        println("Drove for $distance KM on electricity")
    }

    fun drive() {
        println("Drove for $range KM on electricity")
    }
}

// Any class inherit from Any class
fun main() {
    var audi3 = Cars("A3", "Audi")
    var teslaS = ElectricCar("S-Model", "Tesla", 85.0)
    teslaS.chargerType = "Type 2"
    teslaS.extendRange(200.0)

    teslaS.drive()

    // Polymorphism
    audi3.drive(200.0)
    teslaS.drive(200.0)
}