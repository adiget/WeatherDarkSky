package com.ags.annada.weather

import android.os.Parcel

/**
 * Created by : annada
 * Date : 22/02/2018.
 */

// Parcel extensions

inline fun Parcel.readBoolean() = readInt() != 0

inline fun Parcel.writeBoolean(value: Boolean) = writeInt(if (value) 1 else 0)