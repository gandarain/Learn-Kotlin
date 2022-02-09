package com.example.oop_2

interface Drivable {
    val maxSpeed: Double

    fun drive(): String

    fun brake(): Unit {
        println("The drivable is braking")
    }
}

open class Mobile(override val maxSpeed: Double, val name: String, val brand: String): Drivable{
    open var range: Double = 0.0

    fun extendRange(amount: Double) {
        if (amount > 0) {
            this.range += amount
        }
    }

    open fun drive(distance: Double) {
        println("Drove for $distance KM")
    }

    override fun drive() = "driving the interface drive"
}

class ElectricMobile(maxSpeed: Double, name: String, brand: String, batteryLife: Double): Mobile(maxSpeed, name, brand){
    override var range = batteryLife * 6
    var chargerType: String = "Type 1"

    override fun drive(distance: Double){
        println("Drove for $distance KM on electricity")
    }

    override fun drive(): String {
        return "Drove for $range KM on electricity"
    }

    override fun brake() {
        super.brake()
        println("Brake inside of electric car")
    }
}

fun main() {
    var audi3 = Mobile(200.0, "A3", "Audi")
    var teslaS = ElectricMobile(250.0,"S-Model", "Tesla", 85.0)
    teslaS.chargerType = "Type 2"
    teslaS.extendRange(200.0)

    teslaS.drive()
    teslaS.brake()
    audi3.brake()
}