package com.rocqjones.weathertop.methods

class UserDefined {

    fun convertKelvinToCelsius(k: Double): Double {
        val c = 273.15
        val res = k - c
        // ans to 1 decimal place
        return String.format("%.1f", res).toDouble()
    }
}