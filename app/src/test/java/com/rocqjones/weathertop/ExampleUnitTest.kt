@file:Suppress("DEPRECATION")

package com.rocqjones.weathertop

import android.content.Context
import com.rocqjones.weathertop.methods.UserDefined
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    /**
     * Here we're testing code which doesn't call the Android API
     */
    var userDefined = UserDefined()
    lateinit var context: Context
    var c : Calendar = Calendar.getInstance()
    private var df : SimpleDateFormat? = null
    private var formattedDate = ""

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    // conversion
    @Test
    fun convert_Kelvin_To_Celsius() {
        val res = 27.75
        assertEquals(String.format("%.1f", res), userDefined.convertKelvinToCelsius(300.9).toString())
    }

    // dateTime
    @Test
    fun confirm_Date_Time() {
        df = SimpleDateFormat("dd-MM-yyyy HH:mm a")
        formattedDate = df!!.format(c.time)
        assertEquals(formattedDate, userDefined.currentDateTime())
    }
}