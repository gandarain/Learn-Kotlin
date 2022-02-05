package app

import data.Employee
import data.Manager
import data.SuperManager
import data.VicePresident

fun main() {
    var employee = Employee("Ganda")
    employee.sayHello("Rain")

    var manager = Manager("Eko")
    manager.sayHello("Budi")

    var vicePresident = VicePresident("Budi")
    vicePresident.sayHello("Joko")

    var superManager = SuperManager("Didit")
    superManager.sayHello("Adi")
}