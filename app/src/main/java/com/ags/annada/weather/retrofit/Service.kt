package com.ags.annada.weather.retrofit

import com.ags.annada.weather.model.WeatherData
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers

/**
 * Created by : annada
 * Date : 23/02/2018.
 */

class Service(private val networkService: NetworkService) {

//    fun getWeatherByCity(city: String, lang: String, callback: GetWeatherByCityCallback): Subscription {
//
//        return networkService.getWeatherByCity(city, lang)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .onErrorResumeNext(object : Func1<Throwable, Observable<out WeatherData>>() {
//                    fun call(throwable: Throwable): Observable<out WeatherData> {
//                        return Observable.error(throwable)
//                    }
//                })
//                .subscribe(object : Subscriber<WeatherData>() {
//                    fun onCompleted() {
//
//                    }
//
//                    fun onError(e: Throwable) {
//                        callback.onError(NetworkError(e))
//                    }
//
//                    fun onNext(weatherDataResponse: WeatherData) {
//                        callback.onSuccess(weatherDataResponse)
//                    }
//                })
//    }
//
//    interface GetWeatherByCityCallback {
//        fun onSuccess(weatherDataResponse: WeatherData)
//
//        fun onError(networkError: NetworkError)
//    }

    fun getWeatherByLocation(latitude: Double, longitude: Double, units: String = ApiUnits.US.value, callback: GetWeatherByLocationCallback): Subscription {

        return networkService.getWeatherByLocation(latitude, longitude, units)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(object : Func1<Throwable, Observable<out WeatherData>> {
                    override fun call(throwable: Throwable): Observable<out WeatherData> {
                        return Observable.error(throwable)
                    }
                })
                .subscribe(object : Subscriber<WeatherData>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        callback.onError(NetworkError(e))
                    }

                    override fun onNext(weatherDataResponse: WeatherData) {
                        callback.onSuccess(weatherDataResponse)
                    }
                })
    }

    interface GetWeatherByLocationCallback {
        fun onSuccess(weatherDataResponse: WeatherData)

        fun onError(networkError: NetworkError)
    }

//    fun getWeatherForecast(city: String, lang: String, callback: GetWeatherForecastCallback): Subscription {
//
//        return networkService.getWeatherForecast(city, lang)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .onErrorResumeNext(object : Func1<Throwable, Observable<out WeatherForecast>>() {
//                    fun call(throwable: Throwable): Observable<out WeatherForecast> {
//                        return Observable.error(throwable)
//                    }
//                })
//                .subscribe(object : Subscriber<WeatherForecast>() {
//                    fun onCompleted() {
//
//                    }
//
//                    fun onError(e: Throwable) {
//                        callback.onError(NetworkError(e))
//                    }
//
//                    fun onNext(weatherForecastResponse: WeatherForecast) {
//                        callback.onSuccess(weatherForecastResponse)
//
//                    }
//                })
//    }
//
//    interface GetWeatherForecastCallback {
//        fun onSuccess(weatherForecastResponse: WeatherForecast)
//
//        fun onError(networkError: NetworkError)
//    }
//
//    fun getWeatherForecast(city: String, callback: GetWeatherForecastCallback): Subscription {
//
//        return networkService.getWeatherForecast(city)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .onErrorResumeNext(object : Func1<Throwable, Observable<out WeatherForecast>>() {
//                    fun call(throwable: Throwable): Observable<out WeatherForecast> {
//                        return Observable.error(throwable)
//                    }
//                })
//                .subscribe(object : Subscriber<WeatherForecast>() {
//                    fun onCompleted() {
//
//                    }
//
//                    fun onError(e: Throwable) {
//                        callback.onError(NetworkError(e))
//                    }
//
//                    fun onNext(weatherForecastResponse: WeatherForecast) {
//                        callback.onSuccess(weatherForecastResponse)
//
//                    }
//                })
//    }
}