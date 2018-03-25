package com.ags.annada.weather

import android.app.Application
import android.content.SharedPreferences
import com.ags.annada.weather.Constants.FIRST_LAUNCH
import com.ags.annada.weather.dagger2.ApplicationComponent
//import com.ags.annada.weather.dagger2.DaggerApplicationComponent
import com.ags.annada.weather.dagger2.ApplicationModule
import javax.inject.Inject

/**
 * Created by : annada
 * Date : 25/02/2018.
 */

class WeatherApp : Application() {
    private var first_lunch_flag = true
    private var mApplicationComponent: ApplicationComponent? = null

    @Inject
    lateinit var mSharedPrefs: SharedPreferences

    val applicationComponent: ApplicationComponent
        get() {
            if (mApplicationComponent == null) {
//                mApplicationComponent = DaggerApplicationComponent.builder()
//                        .applicationModule(ApplicationModule(this))
//                        .build()
            }
            return mApplicationComponent as ApplicationComponent
        }

    val isThisFirstTimeLaunch: Boolean
        get() = first_lunch_flag && mSharedPrefs.getBoolean(FIRST_LAUNCH, true)

    override fun onCreate() {
        super.onCreate()

        applicationComponent.inject(this) // injection using Dagger 2

        first_lunch_flag = true
        mSharedPrefs = PreferenceUtils.getPreferences(applicationContext)
    }

    fun setFirstTimeLaunch() {
        first_lunch_flag = false
        PreferenceUtils.save(mSharedPrefs, FIRST_LAUNCH, false)
    }
}