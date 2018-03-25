package com.ags.annada.weather.dagger2

import com.ags.annada.weather.WeatherApp
import dagger.Component

/**
 * Created by : annada
 * Date : 25/02/2018.
 */
@ApplicationScope
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(app: WeatherApp)
}
