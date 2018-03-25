package com.ags.annada.weather.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ags.annada.weather.model.City
import com.ags.annada.weather.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_city.view.*

/**
 * Created by : annada
 * Date : 22/02/2018.
 */

class CityListAdapter(private var context: Context, private val mData: List<City>) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    lateinit var itemClickListener: OnItemClickListener

    override fun getItemCount() = mData.size //CityData.cityList().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_city, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = mData[position] //CityData.cityList()[position]
        holder.itemView.cityName.text = city.name
        holder.itemView.temperature.text = city.viewData?.temperature.toString() + " °C"
//        holder.itemView.humidity.text = "Humidity: " + city.viewData?.humidity.toString() + "%"
//        holder.itemView.summary.text = city.viewData?.summary

        Picasso.with(context).load(city.getImageResourceId(context)).into(holder.itemView.cityImage)

        val photo = BitmapFactory.decodeResource(context.resources, city.getImageResourceId(context))
        Palette.from(photo).generate { palette ->
            val bgColor = palette.getMutedColor(ContextCompat.getColor(context, android.R.color.black))
            holder.itemView.cityNameHolder.setBackgroundColor(bgColor)
            city.color = bgColor
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.cityHolder.setOnClickListener(this)
        }

        override fun onClick(view: View) = itemClickListener.onItemClick(itemView, adapterPosition)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}