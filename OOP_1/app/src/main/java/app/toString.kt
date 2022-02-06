package app

import data.User

fun main() {
    val user = User("gandarainpanjaitan", "rahasia")
    println(user.toString())
    println(user)

    val user2 = User("eko", "123456")
    println(user2.toString())
    println(user2)
}