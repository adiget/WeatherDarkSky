package com.ags.annada.weather.presenter

import android.content.Context
import android.widget.Toast
import com.ags.annada.weather.MVP_Main
import com.ags.annada.weather.model.WeatherData
import java.lang.ref.WeakReference

/**
 * Created by : annada
 * Date : 23/02/2018.
 */

class MainPresenter
/**
 * Presenter Constructor
 *
 * @param view MainActivity
 */
(view: MVP_Main.RequiredViewOps) : MVP_Main.ProvidedPresenterOps, MVP_Main.RequiredPresenterOps {
    // View reference. We use as a WeakReference
    // because the Activity could be destroyed at any time
    // and we don't want to create a memory leak
    private var mView: WeakReference<MVP_Main.RequiredViewOps>? = null

    // Model reference
    private var mModel: MVP_Main.ProvidedModelOps? = null

    /**
     * Return the View reference.
     * Throw an exception if the View is unavailable.
     */
    /**
     * Called by View during the reconstruction events
     * @param view  Activity instance
     */
//    private var view: MVP_Main.RequiredViewOps
//        @Throws(NullPointerException::class)
//        get() = if (mView != null)
//            mView!!.get()!!
//        else
//            throw NullPointerException("View is unavailable")
//        set(view) {
//            mView = WeakReference<MVP_Main.RequiredViewOps>(view)
//        }


    /**
     * Return the View reference.
     * Throw an exception if the View is unavailable.
     */
    @Throws(NullPointerException::class)
    private fun getView(): MVP_Main.RequiredViewOps {
        return if (mView != null)
            mView!!.get()!!
        else
            throw NullPointerException("View is unavailable")
    }

    /**
     * Called by View during the reconstruction events
     * @param view  Activity instance
     */
    override fun setView(view: MVP_Main.RequiredViewOps) {
        mView = WeakReference(view)
    }


//    val forecastCount: Int
//        get() = mModel!!.getForecastCount()

    override val appContext: Context
        get() {
            try {
                return getView().getAppContext()
            } catch (e: NullPointerException) {
                return null as Context
            }

        }

    override val activityContext: Context
        get() {
            try {
                return getView().getActivityContext()
            } catch (e: NullPointerException) {
                return null as Context
            }

        }

    init {
        mView = WeakReference<MVP_Main.RequiredViewOps>(view)
    }

//    fun getWeatherByCity(city: String, lang: String) {
//        view.showProgress()
//
//        mModel!!.loadWeatherByCityData(city, lang)
//    }

    override fun getWeatherByLocation(latitude: Double, longitude: Double, units: String) {
        getView().showProgress()

        mModel!!.loadWeatherByLocationData(latitude, longitude, units)
    }

//    fun getWeatherForecast(city: String, lang: String) {
//        view.showProgress()
//
//        mModel!!.loadWeatherForecastData(city, lang)
//    }

//    fun getWeatherForecast(city: String) {
//        view.showProgress()
//
//        mModel!!.loadWeatherForecastData(city)
//    }

    /**
     * Called by View every time it is destroyed.
     * @param isChangingConfiguration   true: is changing configuration
     * and will be recreated
     */
    override fun onDestroy(isChangingConfiguration: Boolean) {
        // View show be null every time onDestroy is called
        mView = null

        // Inform Model about the event
        mModel!!.onDestroy(isChangingConfiguration)

        // Activity destroyed
        if (!isChangingConfiguration) {
            // Nulls Model when the Activity destruction is permanent
            mModel = null
        }
    }

    /**
     * Called by Activity during MVP setup. Only called once.
     * @param model Model instance
     */
    fun setModel(model: MVP_Main.ProvidedModelOps) {
        mModel = model

    }

    /**
     * Creat a Toast object with given message
     * @param msg   Toast message
     * @return      A Toast object
     */
    private fun makeToast(msg: String): Toast {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT)
    }


//    fun createViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
//        val viewHolder: ForecastViewHolder
//
//        val inflater = LayoutInflater.from(parent.context)
//
//        val viewTaskRow = inflater.inflate(R.layout.weather_daily_list, parent, false)
//        viewHolder = ForecastViewHolder(viewTaskRow)
//
//        return viewHolder
//    }

//    fun bindViewHolder(holder: ForecastViewHolder, position: Int) {
//        val forecast = mModel!!.getForecast(position)
//
//        holder.dayOfWeek.setText(forecast.getDayOfWeek())
//
//        Picasso.with(activityContext)
//                .load(Constants.ICON_URL + forecast.getWeatherIcon() + ".png")
//                .into(holder.weatherIcon)
//
//        val mTemp = java.lang.Double.parseDouble(forecast.getWeatherResult())
//        holder.weatherResult.setText(Math.round(mTemp).toString() + "°")
//        holder.weatherResultSmall.setText(forecast.getWeatherResultSmall())
//        holder.weatherResultSmall.setVisibility(View.GONE)
//
//
//    }

    override fun notifyWeatherDataSuccess(weatherDataResponse: WeatherData) {
        getView().hideProgress()

        getView().notifyWeatherDataSuccess(weatherDataResponse)

    }

//    fun notifyWeatherForecastSuccess(weatherForecastResponse: WeatherForecast) {
//        view.hideProgress()
//
//        view.notifyWeatherForecastSuccess(weatherForecastResponse)
//    }

    override fun notifyLoadDataError(error: String) {
        getView().hideProgress()

        getView().showToast(makeToast("Error loading data." + error))
    }
}