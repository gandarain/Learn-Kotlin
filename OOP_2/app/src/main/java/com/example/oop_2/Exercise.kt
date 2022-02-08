package com.example.oop_2

fun main() {
    val iphone = MobilePhone("iOS", "Apple", "iPhone 12")
    val galaxyS20 = MobilePhone("Android","Samsung", "Galaxy S20")
    val mateXS = MobilePhone("Android", "Huawei", "Mate X S")
}

class MobilePhone(var osName: String, var brand: String, var model: String) {
    init {
        println("Here the osName is ${this.osName}, brand is ${this.brand}, model is ${this.model}")
    }
}