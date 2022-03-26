package com.rocqjones.weathertop.methods

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.rocqjones.weathertop.R
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

    fun warnDialog(message: String, context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_warning)
        dialog.setCancelable(false)
        val warnMessage = dialog.findViewById<TextView>(R.id.warnMessage)
        val okBtn = dialog.findViewById<Button>(R.id.okBtn)
        warnMessage.text = message
        okBtn.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation_1
        dialog.show()
        dialog.setCanceledOnTouchOutside(true)
    }
}