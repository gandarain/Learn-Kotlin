package app

import data.City
import data.Country
import data.Location

fun main(){
    // var location = Location("Palmerah")
    var city = City("Jakarta")
    var country = Country("Indonesia")

    println(city.name)
    println(country.name)
}