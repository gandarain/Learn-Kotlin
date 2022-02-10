package com.example.kotlinfundamental

fun main() {
    val arrayList: ArrayList<Double> = ArrayList<Double>(5)
    arrayList.add(13.212312)
    arrayList.add(23.151232)
    arrayList.add(32.651553)
    arrayList.add(16.223817)
    arrayList.add(18.523999)

    var total = 0.0
    for (item in arrayList){
        total += item
    }
    var average = total / arrayList.size
    println("Average is $average")
}