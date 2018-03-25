package com.ags.annada.weather.dagger2

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ags.annada.weather.Constants.SHARED_PREF
import dagger.Module
import dagger.Provides

/**
 * Created by : annada
 * Date : 25/02/2018.
 */
@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @ApplicationScope
    internal fun provideApplicationContext(): Application {
        return mApplication
    }

    @Provides
    @ApplicationScope
    internal fun provideSharedPreferences(): SharedPreferences {
        return mApplication.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }
}
