package com.example.oop_2

fun main() {
    val iphone = MobilePhone("iOS", "Apple", "iPhone 12")
    val galaxyS20 = MobilePhone("Android","Samsung", "Galaxy S20")
    val mateXS = MobilePhone("Android", "Huawei", "Mate X S")

    iphone.chargeBattery(30)
    galaxyS20.chargeBattery(20)
    mateXS.chargeBattery(10)
}

class MobilePhone(var osName: String, var brand: String, var model: String, var battery: Int = 40) {
    init {
        println("Here the osName is ${this.osName}, brand is ${this.brand}, model is ${this.model}")
    }

    fun chargeBattery(charged: Int): Unit {
        println("Battery before: ${this.battery}, Charged: $charged, Battery now: ${this.battery + charged}")
        this.battery += charged
    }
}