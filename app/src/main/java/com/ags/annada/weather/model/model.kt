package com.ags.annada.weather.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by : annada
 * Date : 23/02/2018.
 */

data class Currently(val time: Int, val summary: String, val icon: String, val nearestStormDistance: Int,
                     val precipIntensity: Double, val precipIntensityError: Double, val precipProbability: Double,
                     val precipType: String, val temperature: Double, val apparentTemperature: Double,
                     val dewPoint: Double, val humidity: Double, val pressure: Double, val windSpeed: Double,
                     val windGust: Double, val windBearing: Int, val cloudCover: Double, val uvIndex: Int,
                     val visibility: Double, val ozone: Double)

data class Daily(val summary: String, val icon: String, val data: List<Datum__>)

data class Datum(val time: Int, val precipIntensity: Double, val precipIntensityError: Double, val precipProbability: Double,
                 val precipType: String)

data class Datum_(val time: Int, val summary: String, val icon: String, val precipIntensity: Double,
                  val precipProbability: Double, val precipType: String, val temperature: Double,
                  val apparentTemperature: Double, val dewPoint: Double, val humidity: Double,
                  val pressure: Double, val windSpeed: Double, val windGust: Double, val windBearing: Int,
                  val cloudCover: Double,  val uvIndex: Int, val visibility: Double, val ozone: Double)

data class Datum__(val time: Int, val summary: String, val icon: String, val sunriseTime: Int, val sunsetTime: Int,
                   val moonPhase: Double, val precipIntensity: Double, val precipIntensityMax: Double,
                   val precipIntensityMaxTime: Int, val precipProbability: Double, val precipType: String,
                   val temperatureHigh: Double, val temperatureHighTime: Int, val temperatureLow: Double,
                   val temperatureLowTime: Int, val apparentTemperatureHigh: Double, val apparentTemperatureHighTime: Int,
                   val apparentTemperatureLow: Double, val apparentTemperatureLowTime: Int, val dewPoint: Double,
                   val humidity: Double, val pressure: Double, val windSpeed: Double, val windGust: Double,
                   val windGustTime: Int, val windBearing: Int, val cloudCover: Double, val uvIndex: Int,
                   val uvIndexTime: Int, val visibility: Double, val ozone: Double, val temperatureMin: Double,
                   val temperatureMinTime: Int, val temperatureMax: Double, val temperatureMaxTime: Int,
                   val apparentTemperatureMin: Double, val apparentTemperatureMinTime: Int, val apparentTemperatureMax: Double,
                   val apparentTemperatureMaxTime: Int)

data class WeatherData(val latitude: Double, val longitude: Double, val timezone: String, val currently: Currently,
                       val minutely: Minutely, val hourly: Hourly, val daily: Daily, val flags: Flags, val offset: Int)

data class Flags(val sources: List<String>, val isdStations: List<String>, val units: String)

data class Hourly(val summary: String, val icon: String, val data: List<Datum_>)

data class Minutely(val summary: String, val icon: String, val data: List<Datum>)

data class ViewData(var temperature: Double = 0.0, var humidity: Double, var summary: String): Parcelable {
    /**
     * Companion object is used to defined static members to the class..
     * in our case the creator and the iconsize
     */
    companion object {
        /**
         * Parcelable creator.
         *
         * @JvmField used make the creator implementation visible as a field to Java.
         *
         */
        @JvmField
        val CREATOR = object : Parcelable.Creator<ViewData> {
            override fun createFromParcel(source: Parcel): ViewData? = ViewData(source)
            override fun newArray(size: Int): Array<out ViewData?> = arrayOfNulls(size)
        }
    }

    /**
     * Secondary constructor for the parcelable
     */
    protected constructor(parcelIn: Parcel) : this(
            parcelIn.readDouble(), //temperature
            parcelIn.readDouble(), //humidity
            parcelIn.readString()) //summary

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeDouble(temperature)
        dest?.writeDouble(humidity)
        dest?.writeString(summary)
    }

    override fun describeContents() = 0
}