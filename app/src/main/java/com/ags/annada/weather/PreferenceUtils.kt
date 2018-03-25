package com.ags.annada.weather

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

/**
 * Created by : annada
 * Date : 25/02/2018.
 */
object PreferenceUtils {
    fun save(preferences: SharedPreferences, key: String, newValue: Any) {
        val editor = preferences.edit()
        save(editor, key, newValue)
        editor.apply()
    }

    fun getPreferences(context: Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences(context.packageName, MODE_PRIVATE)
    }

    private fun save(editor: SharedPreferences.Editor, key: String, newValue: Any) {
        if (newValue is Boolean)
            editor.putBoolean(key, newValue)

        if (newValue is String)
            editor.putString(key, newValue)
    }
}
