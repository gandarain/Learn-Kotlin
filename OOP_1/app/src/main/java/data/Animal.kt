package data

abstract class Animal {
    abstract val name: String
    abstract fun run(): Unit
}

class Cat(): Animal() {
    override val name: String = "Cat"
    override fun run(): Unit {
        println("Cat Run!")
    }
}

class Dog(): Animal() {
    override val name: String = "Dog"
    override fun run(): Unit {
        println("Dog Run!")
    }
}