package app

import data.Address

fun main () {
    val address = Address("Jalan A", "Jakarta")
    println("${address.street}")
    println("${address.city}")
    println("${address.country}")

    val address2 = Address("Jalan A", "Jakarta", "Indonesia")
    println("${address2.street}")
    println("${address2.city}")
    println("${address2.country}")
}