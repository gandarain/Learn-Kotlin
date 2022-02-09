package com.example.oop_2

// need min 1 parameter
// can't use open
data class User(val id: Long, var name: String)

fun main() {
    val user1 = User(1, "Ganda")
    user1.name = "Michael"
    println(user1.name)

    val user2 = User(1, "Michael")
    println(user1.equals(user2))

    println("User Detail $user1")

    val updatedUser: User = user1.copy(name = "Messi")
    println("Updated User $updatedUser")

    println(updatedUser.component1())
    println(updatedUser.component2())

    val (id, name) = updatedUser
    println("Id= $id, Name= $name")
}