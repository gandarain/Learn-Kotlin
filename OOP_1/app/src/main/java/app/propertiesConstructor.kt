package app

import data.User

fun main() {
    val user1 = User("Eko", "Rahasia")
    user1.username = "Budi"
    user1.password = "Sangat Rahasia"
    println(user1.username)
    println(user1.password)

    val user2 = User("Joko", "Rahasia123")
    println(user2.username)
    println(user2.password)
}