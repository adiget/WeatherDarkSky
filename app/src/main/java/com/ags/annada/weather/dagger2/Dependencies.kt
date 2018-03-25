package com.ags.annada.weather.dagger2

import com.ags.annada.weather.retrofit.NetworkModule
import com.ags.annada.weather.views.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by : annada
 * Date : 24/02/2018.
 */
@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface Dependencies {
    fun inject(mainActivity: MainActivity)
}

