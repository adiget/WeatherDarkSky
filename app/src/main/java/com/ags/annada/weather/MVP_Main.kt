package com.ags.annada.weather

import android.content.Context
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.ags.annada.weather.model.WeatherData
import com.ags.annada.weather.retrofit.ApiUnits

/**
 * Created by : annada
 * Date : 23/02/2018.
 */

interface MVP_Main {
    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     */
    interface RequiredViewOps {
        // View operations permitted to Presenter
        //val appContext: Context

        //val activityContext: Context

        abstract fun getAppContext(): Context

        abstract fun getActivityContext(): Context


        fun showToast(toast: Toast)

        fun showProgress()

        fun hideProgress()

        fun showAlert(dialog: AlertDialog)

        fun notifyDataSetChanged()

        fun notifyItemInserted(layoutPosition: Int)

        fun notifyItemRangeChanged(positionStart: Int, itemCount: Int)

        fun notifyWeatherDataSuccess(weatherDataResponse: WeatherData)

        //fun notifyWeatherForecastSuccess(weatherForecastResponse: WeatherForecast)
    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Processes user interactions, sends data requests to Model, etc.
     */
    interface ProvidedPresenterOps {

        //val forecastCount: Int
        // Presenter operations permitted to View
        fun onDestroy(isChangingConfiguration: Boolean)

        fun setView(view: RequiredViewOps)

        //fun createViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder

        //fun bindViewHolder(holder: ForecastViewHolder, position: Int)

        //fun getWeatherByCity(city: String, lang: String)

        fun getWeatherByLocation(latitude: Double, longitude: Double, units: String = ApiUnits.US.value)

        //fun getWeatherForecast(city: String, lang: String)

        //fun getWeatherForecast(city: String)
    }

    /**
     * Required Presenter methods available to Model.
     */
    interface RequiredPresenterOps {
        // Presenter operations permitted to Model
        val appContext: Context

        val activityContext: Context

        fun notifyWeatherDataSuccess(weatherDataResponse: WeatherData)

        //fun notifyWeatherForecastSuccess(weatherForecastResponse: WeatherForecast)

        fun notifyLoadDataError(error: String)
    }

    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     */
    interface ProvidedModelOps {

        //val forecastCount: Int
        // Model operations permitted to Presenter
        fun onDestroy(isChangingConfiguration: Boolean)

        //fun loadWeatherByCityData(city: String, lang: String): Boolean

        fun loadWeatherByLocationData(latitude: Double, longitude: Double, units: String = ApiUnits.US.value): Boolean

        //fun loadWeatherForecastData(city: String, lang: String): Boolean

        //fun loadWeatherForecastData(city: String): Boolean

        //fun getForecast(position: Int): ForecastRequiredData
    }
}