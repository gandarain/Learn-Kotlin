package data

// interface is abstract
interface Interaction {
    val name: String
    fun sayHello(name: String): Unit {
        println("Hello $name, mya name is ${this.name}")
    }
}

interface Go: Interaction {
    fun go() {
        println("Go ${this.name}!")
    }
}

interface MoveA {
    fun move() {
        println("Move A")
    }
}

interface MoveB {
    fun move() {
        println("Move B")
    }
}

class Human(override val name: String): Interaction, Go, MoveA, MoveB {
    override fun move() {
        super<MoveA>.move()
        super<MoveB>.move()
        println("Move Human")
    }
}