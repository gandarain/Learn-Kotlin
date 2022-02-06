package app

import data.SuperTeacher
import data.Teacher

fun main() {
    val teacher = Teacher("Eko")
    println(teacher.name)
    // teacher.teach() error
    teacher.write()

    val superTeacher = SuperTeacher("Eko")
    superTeacher.test()
}