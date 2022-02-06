package app

import data.Product

fun main() {
    val product = Product("Indomie", 1500, "Food")
    println(product)

    val product2 = product.copy()
    println(product2)

    val product3 = product.copy(name = "Supermie")
    println(product3)
}