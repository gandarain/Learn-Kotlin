package app

import data.Person

fun main() {
    val eko = Person()
    eko.firstName = "Eko"
    eko.middleName = "Kurniawan"
    eko.lastName = "Khannedy"
    println("${eko.firstName}")

    val joko = Person()
    joko.firstName = "Joko"
    joko.middleName = "Susilo"
    joko.lastName = "Bambang"
    println("${joko.firstName}")

    val budi = Person()
    budi.firstName = "Budi"
    println("${budi.firstName}")
}