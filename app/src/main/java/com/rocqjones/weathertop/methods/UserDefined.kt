package com.rocqjones.weathertop.methods

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class UserDefined {

    var c : Calendar = Calendar.getInstance()
    private var df : SimpleDateFormat? = null
    private var formattedDate = ""

    fun convertKelvinToCelsius(k: Double): Double {
        val c = 273.15
        val res = k - c
        // ans to 1 decimal place
        return String.format("%.1f", res).toDouble()
    }

    @SuppressLint("SimpleDateFormat")
    fun currentDateTime() : String {
        df = SimpleDateFormat("dd-MM-yyyy HH:mm a")
        formattedDate = df!!.format(c.time)
        return formattedDate
    }
}