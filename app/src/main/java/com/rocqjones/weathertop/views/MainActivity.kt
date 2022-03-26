package com.rocqjones.weathertop.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.rocqjones.weathertop.databinding.ActivityMainBinding
import com.rocqjones.weathertop.methods.UserDefined
import com.rocqjones.weathertop.patterns.MySingleton
import com.rocqjones.weathertop.utils.Constants
import org.json.JSONArray
import org.json.JSONObject
import com.rocqjones.weathertop.R


class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    var userDefined = UserDefined()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.searchIC.setOnClickListener {
            binding!!.defaultLinearLayout.visibility = View.GONE
            binding!!.searchLinearLayout.visibility = View.VISIBLE
        }

        binding!!.cancelBtn.setOnClickListener {
            binding!!.defaultLinearLayout.visibility = View.VISIBLE
            binding!!.searchLinearLayout.visibility = View.GONE
            binding!!.searchText.text.clear()
        }

        Log.d("dateTime:", userDefined.currentDateTime()
                + userDefined.convertKelvinToCelsius(300.94).toString())

        binding!!.defaultCity.dateTimeTv.text = userDefined.currentDateTime()

        // By default we want to display current country data
        currentWeatherDetails()
    }

    @SuppressLint("SetTextI18n")
    private fun currentWeatherDetails() {
        binding!!.progressBar.visibility = View.VISIBLE
        val country = "Kenya"
        val url = Constants.BASE_URL + country + "&appid=" + Constants.API_KEY

        /**
         * Append country key work as param and api KEY (above)
         * Send request as JsonObjectRequest because we expect a JSONObject response
         */
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response: JSONObject ->
                /**
                 * API response logging
                 * wrap it in try and catch for exception handling
                 */
                Log.d("response", response.toString())
                binding!!.progressBar.visibility = View.GONE
                try {
                    if (response.getString("cod").equals("200")) {
                        // set date to UI
                        binding!!.defaultCity.locationName.text = response.getString("name")

                        // temp
                        Log.d("responseTemp", response.getString("main"))
                        val jsonObjectT = JSONObject(response.getString("main"))
                        val temperature = jsonObjectT.getString("temp")
                        binding!!.defaultCity.degreeTv.text =
                            userDefined.convertKelvinToCelsius(temperature.toDouble()).toString() + "\u2103"

                        // wind speed
                        Log.d("responseWind", response.getString("wind"))
                        val jsonObjectW = JSONObject(response.getString("wind"))
                        binding!!.defaultCity.windSpeed.text =
                            "Wind Speed: " + jsonObjectW.getString("speed") + " FPM"

                        // weather updates
                        Log.d("responseDesc1", response.getString("weather"))
                        val jsonArrayD = JSONArray(response.getString("weather"))
                        Log.d("responseDesc2", jsonArrayD.toString())
                        for (i in 0 until jsonArrayD.length()) {
                            val jsonObjectU = jsonArrayD.getJSONObject(i)
                            binding!!.defaultCity.description.text =
                                jsonObjectU.getString("description")
                        }
                    } else {
                        Toast.makeText(this, "Error occurred!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                error.printStackTrace()
                Log.e("error", error.toString())
                binding!!.progressBar.visibility = View.GONE
                /**
                 * Error handling
                 */
                if (error is TimeoutError || error is NoConnectionError) {
                    userDefined.warnDialog(getString(R.string.network_offline), this)
                } else if (error is AuthFailureError) {
                    userDefined.warnDialog(getString(R.string.auth_error), this)
                } else if (error is ServerError) {
                    userDefined.warnDialog(getString(R.string.server_error), this)
                } else if (error is NetworkError) {
                    userDefined.warnDialog(getString(R.string.network_error), this)
                } else if (error is ParseError) {
                    userDefined.warnDialog(getString(R.string.parse_error), this)
                }
            }) {
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                headers["Content-Type"] = "application/json"
                return headers
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }

        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(60000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        MySingleton.getInstance(this@MainActivity)!!.addToRequestQueue(jsonObjectRequest)
    }
}