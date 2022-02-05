package app

import data.Person

fun main() {
    var eko = Person()
    eko.firstName = "Eko"

    eko.sayHello("Budi")
    eko.sayHello("Joko", "Nugroho")
}