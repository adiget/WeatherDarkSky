package com.ags.annada.weather.model

import com.ags.annada.weather.MVP_Main
import com.ags.annada.weather.retrofit.NetworkError
import com.ags.annada.weather.retrofit.Service
import rx.subscriptions.CompositeSubscription
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by : annada
 * Date : 23/02/2018.
 */

class MainModel : MVP_Main.ProvidedModelOps {
    // Presenter reference
    private var mPresenter: MVP_Main.RequiredPresenterOps? = null
    // Recycler data
    //var mForecastData: ArrayList<ForecastRequiredData>? = null
    var mWeatherData: WeatherData? = null
    private var subscriptions: CompositeSubscription? = null
    private var service: Service? = null

//    /**
//     * Get ArrayList size
//     * @return  ArrayList size
//     */
//    val forecastCount: Int
//        get() = if (mForecastData != null) mForecastData!!.size else 0

    /**
     * Main constructor, called by Activity during MVP setup
     * @param presenter Presenter instance
     */
    constructor(presenter: MVP_Main.RequiredPresenterOps) {
        this.mPresenter = presenter
        this.subscriptions = CompositeSubscription()
        //mForecastData = ArrayList<ForecastRequiredData>()
    }

    /**
     * Main constructor, called by Activity during MVP setup
     * @param presenter Presenter instance
     */
    constructor(presenter: MVP_Main.RequiredPresenterOps, service: Service) {
        this.mPresenter = presenter
        this.service = service
        this.subscriptions = CompositeSubscription()
        //mForecastData = ArrayList<ForecastRequiredData>()
    }

    /**
     * Called by Presenter when View is destroyed
     * @param isChangingConfiguration   true configuration is changing
     */
    override fun onDestroy(isChangingConfiguration: Boolean) {
        if (!isChangingConfiguration) {
            mPresenter = null
            //mForecastData = null

            //Un-subscribe subscription
            rxUnSubscribe()
        }
    }

    fun rxUnSubscribe() {
        if (subscriptions != null && !subscriptions!!.isUnsubscribed)
            subscriptions!!.unsubscribe()
    }

//    fun loadWeatherByCityData(city: String, lang: String): Boolean {
//        val subscription = service.getWeatherByCity(city, lang, object : Service.GetWeatherByCityCallback() {
//            fun onSuccess(weatherDataResponse: WeatherData) {
//                mWeatherData = weatherDataResponse
//                mPresenter!!.notifyWeatherDataSuccess(weatherDataResponse)
//            }
//
//            fun onError(networkError: NetworkError) {
//                mPresenter!!.notifyLoadDataError(networkError.getMessage())
//            }
//
//        })
//
//        subscriptions!!.add(subscription)
//
//        return mWeatherData != null
//    }

    override fun loadWeatherByLocationData(latitude: Double, longitude: Double, units: String): Boolean {

        val subscription = service?.getWeatherByLocation(latitude, longitude, units, object : Service.GetWeatherByLocationCallback {
            override fun onSuccess(weatherDataResponse: WeatherData) {
                mWeatherData = weatherDataResponse
                mPresenter!!.notifyWeatherDataSuccess(weatherDataResponse)
            }

            override fun onError(networkError: NetworkError) {
                mPresenter!!.notifyLoadDataError(networkError.message.toString())
            }

        })

        subscriptions!!.add(subscription)

        return mWeatherData != null
    }

//    fun loadWeatherForecastData(city: String, lang: String): Boolean {
//
//        val subscription = service.getWeatherForecast(city, lang, object : Service.GetWeatherForecastCallback() {
//            fun onSuccess(weatherForecastResponse: WeatherForecast) {
//                makeForecastRequiredModel(weatherForecastResponse)
//                mPresenter!!.notifyWeatherForecastSuccess(weatherForecastResponse)
//            }
//
//            fun onError(networkError: NetworkError) {
//                mPresenter!!.notifyLoadDataError(networkError.getMessage())
//            }
//
//        })
//
//        subscriptions!!.add(subscription)
//
//        return mForecastData != null
//    }

//    fun loadWeatherForecastData(city: String): Boolean {
//
//        val subscription = service.getWeatherForecast(city, object : Service.GetWeatherForecastCallback() {
//            fun onSuccess(weatherForecastResponse: WeatherForecast) {
//                makeForecastRequiredModel(weatherForecastResponse)
//                mPresenter!!.notifyWeatherForecastSuccess(weatherForecastResponse)
//            }
//
//            fun onError(networkError: NetworkError) {
//                mPresenter!!.notifyLoadDataError(networkError.getMessage())
//            }
//        })
//
//        subscriptions!!.add(subscription)
//
//        return mForecastData != null
//    }

//    private fun makeForecastRequiredModel(forecast: WeatherForecast) {
//        mForecastData!!.clear()
//
//        val everyday = intArrayOf(0, 0, 0, 0, 0, 0, 0)
//
//        val weatherInfo = forecast.getList()
//
//        for (i in weatherInfo.indices) {
//            val time = weatherInfo.get(i).getDtTxt()
//            val shortDay = convertTimeToDay(time)
//            val temp = weatherInfo.get(i).getMain().getTemp()
//            val tempMin = weatherInfo.get(i).getMain().getTempMin()
//            val icon = weatherInfo.get(i).getWeather().get(0).getIcon()
//
//            if (convertTimeToDay(time) == "Mon" && everyday[0] < 1) {
//                mForecastData!!.add(ForecastRequiredData(shortDay, icon, temp, tempMin))
//                everyday[0] = 1
//            }
//
//            if (convertTimeToDay(time) == "Tue" && everyday[1] < 1) {
//                mForecastData!!.add(ForecastRequiredData(shortDay, icon, temp, tempMin))
//                everyday[1] = 1
//            }
//
//            if (convertTimeToDay(time) == "Wed" && everyday[2] < 1) {
//                mForecastData!!.add(ForecastRequiredData(shortDay, icon, temp, tempMin))
//                everyday[2] = 1
//            }
//
//            if (convertTimeToDay(time) == "Thu" && everyday[3] < 1) {
//                mForecastData!!.add(ForecastRequiredData(shortDay, icon, temp, tempMin))
//                everyday[3] = 1
//            }
//
//            if (convertTimeToDay(time) == "Fri" && everyday[4] < 1) {
//                mForecastData!!.add(ForecastRequiredData(shortDay, icon, temp, tempMin))
//                everyday[4] = 1
//            }
//
//            if (convertTimeToDay(time) == "Sat" && everyday[5] < 1) {
//                mForecastData!!.add(ForecastRequiredData(shortDay, icon, temp, tempMin))
//                everyday[5] = 1
//            }
//
//            if (convertTimeToDay(time) == "Sun" && everyday[6] < 1) {
//                mForecastData!!.add(ForecastRequiredData(shortDay, icon, temp, tempMin))
//                everyday[6] = 1
//            }
//        }
//    }

    private fun convertTimeToDay(time: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:SSSS", Locale.getDefault())
        var days = ""
        try {
            val date = format.parse(time)
            println("Our time " + date)
            val calendar = Calendar.getInstance()
            calendar.time = date
            days = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
            println("Our time " + days)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return days
    }

    /**
     * Get a specific forecast from list using its array position
     * @param position    Array position
     * @return            forecast from list
     */
//    fun getForecast(position: Int): ForecastRequiredData {
//        return mForecastData!![position]
//    }
}