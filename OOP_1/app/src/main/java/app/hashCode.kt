package app

import data.Company

fun main() {
    val company1 = Company("Tokopedia")
    val company2 = Company("Gojek")

    println(company1.hashCode())
    println(company2.hashCode())
    println(company1.hashCode() == company2.hashCode())
}