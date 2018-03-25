package com.ags.annada.weather.model

import java.util.ArrayList

/**
 * Created by : annada
 * Date : 22/02/2018.
 */
object CityData {

    var cityNameArray = arrayOf("London", "Paris", "NewYork", "Los Angeles", "Tokyo")

    fun cityList(): ArrayList<City> {
        val list = ArrayList<City>()

        for (i in cityNameArray.indices) {
            val city = City(cityNameArray[i], cityNameArray[i].replace("\\s+".toRegex(), "").toLowerCase())
            list.add(city)
        }
        return list
    }
}