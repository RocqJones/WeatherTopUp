package com.rocqjones.weathertop.patterns

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * Makes RequestQueue last the lifetime of this app
 * Created by - Jones Mbindyo
 * Date - 27-03-2022
 */
class MySingleton private constructor(private val c: Context) {

    private var requestQueue: RequestQueue?

    private fun getRequestQueue(): RequestQueue {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(c.applicationContext)
        }
        return requestQueue as RequestQueue
    }

    // method that accepts request
    fun <T> addToRequestQueue(request: Request<T>?) {
        requestQueue!!.add(request)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mySingleton: MySingleton? = null

        // ref method that return instance of this class
        @Synchronized
        fun getInstance(context: Context): MySingleton? {
            if (mySingleton == null) {
                mySingleton = MySingleton(context)
            }
            return mySingleton
        }
    }

    // object of class
    init {
        requestQueue = getRequestQueue()
    }
}