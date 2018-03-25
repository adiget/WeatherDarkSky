package com.ags.annada.weather.retrofit

import com.ags.annada.weather.BuildConfig
import com.ags.annada.weather.Constants
import com.ags.annada.weather.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * Created by : annada
 * Date : 23/02/2018.
 */

interface NetworkService {
//    @GET(Constants.WEATHER_ENDPOINT + "&APPID=" + Constants.API_KEY + "&units=metric")
//    fun getWeatherByCity(@Query("q") city: String, @Query("lang") lang: String): Observable<WeatherData>

    @GET(Constants.WEATHER_FORECAST_ENDPOINT + BuildConfig.DARKSKY_API_KEY + "/{lat},{long}")
    fun getWeatherByLocation(@Path("lat") latitude: Double,
                             @Path("long") longitude: Double,
                             @Query("units") units: String = ApiUnits.US.value) : Observable<WeatherData>

//    @GET(Constants.WEATHER_FORECAST_ENDPOINT + "&APPID=" + Constants.API_KEY + "&units=metric")
//    fun getWeatherForecast(@Query("q") city: String, @Query("lang") lang: String): Observable<WeatherForecast>
//
//    @GET(Constants.WEATHER_FORECAST_ENDPOINT + "&APPID=" + Constants.API_KEY + "&units=metric")
//    fun getWeatherForecast(@Query("q") city: String): Observable<WeatherForecast>
}
