package com.example.kotlinfundamental

fun main() {
    val arrayList = ArrayList<String>()
    arrayList.add("One")
    arrayList.add("Two")
    println(arrayList)
    for (item in arrayList){
        println(item)
    }

    println()
    val arrayList2: ArrayList<String> = ArrayList<String>(5)
    var list: MutableList<String> = mutableListOf()
    list.add("One")
    list.add("Two")
    arrayList2.addAll(list)
    println(arrayList2)

    val itr = arrayList2.iterator()
    while (itr.hasNext()){
        println(itr.next())
    }
    println("Array list 2 size is ${arrayList2.size}")


    println()
    val arrayList3: ArrayList<String> = ArrayList<String>()
    arrayList3.add("One")
    arrayList3.add("Two")

    for (item in arrayList3){
        println(item)
    }
    println(arrayList3.get(1))
}