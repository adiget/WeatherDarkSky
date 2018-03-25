package com.ags.annada.weather.views

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.ags.annada.weather.*
import com.ags.annada.weather.adapters.CityListAdapter
import com.ags.annada.weather.dagger2.DaggerDependencies
import com.ags.annada.weather.dagger2.Dependencies
import com.ags.annada.weather.model.*
import com.ags.annada.weather.presenter.MainPresenter
import com.ags.annada.weather.retrofit.ApiUnits
import com.ags.annada.weather.retrofit.NetworkModule
import com.ags.annada.weather.retrofit.Service
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MVP_Main.RequiredViewOps {
    companion object {
        val EXTRA_PARAM_ID = "city_id"
    }

    lateinit private var staggeredLayoutManager: StaggeredGridLayoutManager
    lateinit private var mAdapter: CityListAdapter

    lateinit private var menu: Menu
    private var isListView: Boolean = false
    lateinit private var mData: ArrayList<City>
    private var mPresenter: MVP_Main.ProvidedPresenterOps? = null
    lateinit var deps: Dependencies

    @Inject
    lateinit var service: Service

    // Responsible to maintain the object's integrity
    // during configurations change
    public val mStateMaintainer = StateMaintainer(fragmentManager, MainActivity::class.java.name)

//    fun getDeps(): Dependencies {
//        return deps
//    }

    fun getPresenter(): MVP_Main.ProvidedPresenterOps {
        return this!!.mPresenter!!
    }


    private val onItemClickListener = object : CityListAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            //Toast.makeText(this@MainActivity, "Clicked " + position, Toast.LENGTH_SHORT).show()
//            val cityDetailsIntent = Intent(this@MainActivity, DetailActivity::class.java)
//            cityDetailsIntent.putExtra(EXTRA_PARAM_ID, position)
//            startActivity(cityDetailsIntent)




            val cityDetailsIntent = Intent(this@MainActivity, DetailActivity::class.java)

            val citySelected: City = mData[position]
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_PARAM_ID, citySelected)
            cityDetailsIntent.putExtras(bundle)
            startActivity(cityDetailsIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)


        val cacheFile = File(cacheDir, "responses")
        deps = DaggerDependencies.builder().networkModule(NetworkModule(cacheFile)).build()

        deps.inject(this)

        setupViews()

//        isListView = true
//
//        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
//        list.layoutManager = staggeredLayoutManager
//
//
//        mData = CityData.cityList()
//
//        adapter = CityListAdapter(this, mData)
//        list.adapter = adapter
//
//        adapter.setOnItemClickListener(onItemClickListener)

        setupMVP()

        getWeatherForAllCities()
    }

    /**
     * Setup the Views
     */
    private fun setupViews() {
        isListView = true

        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        list.layoutManager = staggeredLayoutManager


        mData = CityData.cityList()

        mAdapter = CityListAdapter(this, mData)
        list.adapter = mAdapter

        mAdapter.setOnItemClickListener(onItemClickListener)
    }

    /**
     * Setup Model View Presenter pattern.
     * Use a [StateMaintainer] to maintain the
     * Presenter and Model instances between configuration changes.
     * Could be done differently,
     * using a dependency injection for example.
     */
    private fun setupMVP() {
        // Check if StateMaintainer has been created
        if (mStateMaintainer.firstTimeIn()) {
            // Create the Presenter
            val presenter = MainPresenter(this)

            // Create the Model
            val model = MainModel(presenter, service)

            // Set Presenter model
            presenter.setModel(model)

            // Add Presenter and Model to StateMaintainer
            mStateMaintainer.put(presenter)
            mStateMaintainer.put(model)

            // Set the Presenter as a interface
            // To limit the communication with it
            mPresenter = presenter
        } else {
            // Get the Presenter
            mPresenter = mStateMaintainer[MainPresenter::class.java!!.getName()]

            // Updated the View in Presenter
            mPresenter?.setView(this)
        }// get the Presenter from StateMaintainer
    }

    fun getWeatherForAllCities(){
        findCoordOfAllCities()

        for (item in mData) {
            mPresenter?.getWeatherByLocation(item.latitude, item.longitude, ApiUnits.SI.value)
        }
    }

    private fun findCoordOfAllCities() {
        for (item in mData) {
            when (item.name) {
                "New York" -> {
                    item.latitude= 42.3482
                    item.longitude = -75.1890
                }
                "London" -> {
                    item.latitude= 51.5072
                    item.longitude = -0.1275
                }
                "Los Angeles" -> {
                    item.latitude= 34.0500
                    item.longitude = -118.2500
                }
                "Paris" -> {
                    item.latitude= 48.8567
                    item.longitude = 2.3508
                }
                "Tokyo" -> {
                    item.latitude= 35.6833
                    item.longitude = 139.6833
                }
                else -> {
                    item.latitude = 42.3482
                    item.longitude = -75.1890
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_toggle) {
            toggle()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggle() {
        if (isListView) {
            showGridView()
        } else {
            showListView()
        }
    }

    private fun showListView() {
        staggeredLayoutManager.spanCount = 1
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_grid)
        item.title = getString(R.string.show_as_grid)
        isListView = true
    }

    private fun showGridView() {
        staggeredLayoutManager.spanCount = 2
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_list)
        item.title = getString(R.string.show_as_list)
        isListView = false
    }

    override fun getAppContext(): Context {
        return applicationContext
    }

    override fun getActivityContext(): Context {
        return this
    }

    override fun showToast(toast: Toast) {
        toast.show()
    }

    override fun showProgress() {
        //mProgress.setVisibility(View.VISIBLE)
    }

    override fun hideProgress() {
        //mProgress.setVisibility(View.GONE)
    }

    override fun showAlert(dialog: AlertDialog) {
        dialog.show()
    }

    // Notify the RecyclerAdapter that a new item was inserted
    override fun notifyItemInserted(adapterPos: Int) {
        //mAdapter.notifyItemInserted(adapterPos)
    }

    // notify the RecyclerAdapter that items has changed
    override fun notifyItemRangeChanged(positionStart: Int, itemCount: Int) {
        //mAdapter.notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun notifyWeatherDataSuccess(weatherDataResponse: WeatherData) {
        makeViewData(weatherDataResponse)
        mAdapter.notifyDataSetChanged()

        //updateWeatherView()
    }

//    fun notifyWeatherForecastSuccess(weatherForecastResponse: WeatherForecast) {
//        //makeForecastRequiredModel(weatherForecastResponse);
//        mAdapter.notifyDataSetChanged()
//    }

    // notify the RecyclerAdapter that data set has changed
    override fun notifyDataSetChanged() {
        //mAdapter.notifyDataSetChanged()
    }

    fun makeViewData(weatherDataResponse: WeatherData){
        val temperture: Double = weatherDataResponse.currently.temperature
        val humidity: Double = weatherDataResponse.currently.humidity
        val summary: String = weatherDataResponse.currently.summary

        val city: String = weatherDataResponse.timezone
        var cityName = city.split("/")[1]
        val re = Regex("[^A-Za-z0-9]")
        cityName = re.replace(cityName, "")

        for (item in mData) {

            //cityName.replace("[^A-Za-z ]", "")


            val itemCityName = re.replace(item.name, "")

            if(itemCityName.equals(cityName,true)) {

            //if((item.name).equals(cityName)) {
                item.viewData = ViewData(temperture, humidity, summary)
                break
            }
        }
    }
}
