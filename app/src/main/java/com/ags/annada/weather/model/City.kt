package com.ags.annada.weather.model

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.ags.annada.weather.readBoolean
import com.ags.annada.weather.writeBoolean

/**
 * Created by : annada
 * Date : 22/02/2018.
 */

class City(val name: String, private val imageName: String, val isFav: Boolean = false,
           var color: Int = 0, var latitude: Double = 0.0, var longitude: Double = 0.0, var viewData: ViewData? = null) : Parcelable {
    fun getImageResourceId(context: Context): Int {
        return context.resources.getIdentifier(this.imageName, "drawable", context.packageName)
    }

    /**
     * Companion object is used to defined static members to the class..
     * in our case the creator and the iconsize
     */
    companion object {
        /**
         * Parcelable creator.
         *
         * @JvmField used make the creator implementation visible as a field to Java.
         *
         */
        @JvmField
        val CREATOR = object : Parcelable.Creator<City> {
            override fun createFromParcel(source: Parcel): City? = City(source)
            override fun newArray(size: Int): Array<out City?> = arrayOfNulls(size)
        }
    }

    /**
     * Secondary constructor for the parcelable
     */
    protected constructor(parcelIn: Parcel) : this(
            parcelIn.readString(), //name
            parcelIn.readString(), //imageName
            parcelIn.readBoolean(), //isFav
            parcelIn.readInt(), //color
            parcelIn.readDouble(),//latitude
            parcelIn.readDouble(), //longitude
            parcelIn.readParcelable(ViewData::class.java.classLoader))//viewData


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(imageName)
        dest?.writeBoolean(isFav)
        dest?.writeInt(color)
        dest?.writeDouble(latitude)
        dest?.writeDouble(longitude)
        dest?.writeParcelable(viewData, flags)
    }

    override fun describeContents() = 0
}