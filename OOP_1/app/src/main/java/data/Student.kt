package data

class Student(val name: String, private val age: Int)

// extension function
fun Student?.sayHello(name: String){
    if (this != null) {
        println("Hello $name, my name is ${this.name}")
    }
}


// extension properties
val Student.upperName: String
    get() = this.name.toUpperCase()