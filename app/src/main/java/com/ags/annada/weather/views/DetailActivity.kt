package com.ags.annada.weather.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.ags.annada.weather.model.City
import com.ags.annada.weather.R
import com.ags.annada.weather.views.MainActivity.Companion.EXTRA_PARAM_ID
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Created by : annada
 * Date : 22/02/2018.
 */

class DetailActivity : AppCompatActivity() {

    private val API = "https://api.forecast.io/forecast/<YOUR_API_KEY>/%s"

    lateinit private var inputManager: InputMethodManager
    lateinit private var city: City

    private var defaultColor: Int = 0

    lateinit var coord: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)

        val ab : ActionBar? = getSupportActionBar()

        if (ab != null) ab.setDisplayHomeAsUpEnabled(true)

        val intent = this.intent
        val bundle = intent.extras

        city = bundle.getParcelable(EXTRA_PARAM_ID)

        setupValues()
        loadCity()
        windowTransition()
        getPhoto()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        chooseCity(city.name)

        val json = getJSON(this, coord)
        //Log.v("JSON:", json.toString());
        renderWeather(json!!)
    }

    private fun chooseCity(cityName: String) {
        when (cityName) {
            "New York" -> {
                coord = "42.3482,-75.1890"
            }
            "London" -> {
                coord = "51.5072,-0.1275"
            }
            "Los Angeles" -> {
                coord = "34.0500,-118.2500"
            }
            "Paris" -> {
                coord = "48.8567,2.3508"
            }
            "Tokyo" -> {
                coord = "35.6833,139.6833"
            }
            else -> coord = "42.3482,-75.1890"
        }
    }

    private fun renderWeather(json: JSONObject) {
        try {
            //detailsField.setText("");
            //cityField.setText("");
            val calendar = Calendar.getInstance()
            val today = calendar.get(Calendar.DAY_OF_WEEK)

            val days = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
            val data_array = json.getJSONObject("daily").getJSONArray("data")
//            for (i in 0..6) {
//                val item = data_array.getJSONObject(i)
//
//                var temperatureMax = item.getString("temperatureMax")
//                var temperatureMin = item.getString("temperatureMin")
//                val w_summary = item.getString("summary")
//                temperatureMax = temperatureMax.substring(0, 2)
//                temperatureMin = temperatureMin.substring(0, 2)
//
//                detailsField.setText(detailsField.getText() + days[(today + i) % 7] + ": " + temperatureMin + " - " + temperatureMax + " " + w_summary + "\n")
//            }


            //cityField.setText("New York");
//            if (json.getString("timezone").contains("York"))
//                cityField.setText("New York")
//            if (json.getString("timezone").contains("London"))
//                cityField.setText("London")
//            if (json.getString("timezone").contains("Los"))
//                cityField.setText("Los Angeles")
//            if (json.getString("timezone").contains("Paris"))
//                cityField.setText("Paris")
//            if (json.getString("timezone").contains("Tokyo"))
//                cityField.setText("Tokyo")


            temperature.setText("Temperature: " + json.getJSONObject("currently").getString("temperature") + " \u00b0 F")
            humidity.setText("Humidity: " + json.getJSONObject("currently").getString("humidity") + "%")
            summary.setText(json.getJSONObject("currently").getString("summary"))


//            updatedField.setText(
//
//                    // "SUMMARY OF WEEK  : " +
//                    json.getJSONObject("daily").getString("summary")
//                    // +      "\nTIME ZONE  : " + json.getString("timezone")
//            )


        } catch (e: Exception) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data")
        }

    }

    fun getJSON(context: Context, coord: String): JSONObject? {
        try {
            //coord = "40.7127,-74.0059";//debug
            val url = URL(String.format(API, coord))

            val connection = url.openConnection() as HttpURLConnection
            connection.inputStream

            print("CONNECTION:::" + connection.inputStream)

            val reader = BufferedReader(
                    InputStreamReader(connection.inputStream))

            print("url:::")
            val json = StringBuffer(1024)
            var tmp = reader.readLine()

            while (tmp != null) {
                json.append(tmp).append("\n")
                tmp = reader.readLine()
            }

//            var tmp = ""
//            while ((tmp = reader.readLine()) != null)
//                json.append(tmp).append("\n")

            reader.close()

            return JSONObject(json.toString())
        } catch (e: Exception) {
            e.printStackTrace()

            return null
        }

    }

    private fun setupValues() {
        //city = CityData.cityList()[intent.getIntExtra(EXTRA_PARAM_ID, 0)]
        defaultColor = ContextCompat.getColor(this, R.color.primary_dark)
        inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun loadCity() {
        //cityTitle.text = city.name
        cityImage.setImageResource(city.getImageResourceId(this))

        // Toolbar setup
        collapsing_toolbar.setTitle(city.name)
        collapsing_toolbar.setExpandedTitleColor(Color.WHITE)
        collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE)
        collapsing_toolbar.setContentScrimColor(city.color)
        collapsing_toolbar.setStatusBarScrimColor(city.color)
        collapsing_toolbar.setBackgroundColor(city.color)
    }

    private fun windowTransition() {

    }

    private fun getPhoto() {
        val photo = BitmapFactory.decodeResource(resources, city.getImageResourceId(this))
    }

    private fun colorize(photo: Bitmap) {}

    private fun applyPalette() {

    }

    private fun revealEditText(view: LinearLayout) {

    }

    private fun hideEditText(view: LinearLayout) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}