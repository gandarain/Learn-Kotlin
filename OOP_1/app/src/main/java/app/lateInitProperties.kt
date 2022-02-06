package app

import data.Television

fun main() {
    var tv = Television()
    tv.initTelevision("Samsung")
    println(tv.brand)
}