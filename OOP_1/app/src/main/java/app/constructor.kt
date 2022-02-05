package app

import data.Car

fun main() {
    val avanza = Car("Toyota")
    avanza.year = 2015
    println("${avanza.brand}")
    println("${avanza.year}")

    val almaz = Car("Wulling", "Almaz")
    println("${almaz.brand}")
    println("${almaz.year}")
}