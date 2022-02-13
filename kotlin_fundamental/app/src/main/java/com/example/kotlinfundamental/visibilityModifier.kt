package com.example.kotlinfundamental

// Public modifier is default modifier, its accessible from everywhere in the project
// Private modifier, accessible only within the block
// Internal modifier

fun main() {

}

class Demo {
    fun hello(){
        println("Hello")
    }
}

// this class only accessible on same file
private class PrivateDemo() {
    fun hello() {}

    // this property only accessible on this block class
    private val name: String = ""
}