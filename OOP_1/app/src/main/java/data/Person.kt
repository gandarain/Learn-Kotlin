package data

class Person() {
    var firstName: String = ""
    var middleName: String? = null
    var lastName: String = ""

    fun sayHello(name: String): Unit {
        println("Hello $name, my name is ${this.firstName}")
    }

    fun run(): Unit {
        println("I am run")
    }

    fun getFullName(): String {
        return "${this.firstName} ${this.middleName} ${this.lastName}"
    }

    fun sayHello(firstName: String, lastName: String): Unit {
        println("Hello $firstName $lastName, my name is ${this.firstName} ${this.lastName}")
    }
}