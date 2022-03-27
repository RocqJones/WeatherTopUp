package com.rocqjones.weathertop.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.rocqjones.weathertop.R
import com.rocqjones.weathertop.adapters.AdapterMultipleCities
import com.rocqjones.weathertop.databinding.ActivityMainBinding
import com.rocqjones.weathertop.methods.UserDefined
import com.rocqjones.weathertop.model.CitiesModel
import com.rocqjones.weathertop.patterns.MySingleton
import com.rocqjones.weathertop.utils.Constants
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    var userDefined = UserDefined()

    // adapter
    var citiesModelList: MutableList<CitiesModel?>? = null
    private var adapterMultipleCities: AdapterMultipleCities? = null

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

        citiesModelList = ArrayList()
        val linearLayoutManager = LinearLayoutManager(this)
        binding!!.dashRv.layoutManager = linearLayoutManager

        // Update for multiple cities with ids
        multipleCityDetails()
    }

    @SuppressLint("SetTextI18n")
    private fun currentWeatherDetails() {
        binding!!.progressBar.visibility = View.VISIBLE
        val country = "Nairobi"
        val url = Constants.BASE_URL + "weather?q=" + country + "&appid=" + Constants.API_KEY

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
                        val jsonObjectC = JSONObject(response.getString("sys"))
                        val countryID = jsonObjectC.getString("country")
                        binding!!.defaultCity.locationName.text = response.getString("name") +
                                ", " + countryID

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

    private fun multipleCityDetails() {
        binding!!.progressBar.visibility = View.VISIBLE
        val countryIds = "524901,703448,2643743,5128581,4350049,4896861,4736286,5551752,6254927," +
                "5165418,184745,186301,2017370,298795,745042,2988507,3117735,2267057,1796236,1816670"
        val url = Constants.BASE_URL + "group?id=" + countryIds + "&appid=" + Constants.API_KEY

        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response: JSONObject ->
                Log.d("responseMultiple", response.toString())
                binding!!.progressBar.visibility = View.GONE
                try {
                    /**
                     * iterate through json array 'list'
                     * set data to model within the loop
                     */
                    Log.d("responseArrData", response.getString("list"))
                    val jsonArrayList = JSONArray(response.getString("list"))
                    for (i in 0 until jsonArrayList.length()) {
                        val jsonObjectArr = jsonArrayList.getJSONObject(i)
                        val citiesModel = CitiesModel()

                        // wind speed
                        Log.d("responseWind", jsonObjectArr.getString("wind"))
                        val jsonObjectW = JSONObject(jsonObjectArr.getString("wind"))
                        citiesModel.speed = jsonObjectW.getString("speed")

                        // country name
                        citiesModel.name = jsonObjectArr.getString("name")
                        val jsonObjectC = JSONObject(jsonObjectArr.getString("sys"))
                        citiesModel.country = jsonObjectC.getString("country")

                        // temp
                        Log.d("responseTemp", jsonObjectArr.getString("main"))
                        val jsonObjectT = JSONObject(jsonObjectArr.getString("main"))
                        citiesModel.temp = jsonObjectT.getString("temp")

                        // weather updates
                        Log.d("responseDesc", jsonObjectArr.getString("weather"))
                        val jsonArrayD = JSONArray(jsonObjectArr.getString("weather"))
                        for (j in 0 until jsonArrayD.length()) {
                            val jsonObjectDesc = jsonArrayD.getJSONObject(j)
                            citiesModel.description = jsonObjectDesc.getString("description")
                        }
                        citiesModelList!!.add(citiesModel)
                    }
                    /**
                     * Set data to recyclerView using Adapter
                     */
                    adapterMultipleCities = AdapterMultipleCities(citiesModelList, this)
                    binding!!.dashRv.adapter = adapterMultipleCities

                    /**
                     * If the blocks fails check errors and throw warning dialog
                     * On bad URL error 404, in case of others are caught in 'else'
                     */
                    if (response.getString("cod").equals("404")) {
                        val msg = response.getString("message")
                        userDefined.warnDialog(msg, this)
                    } else {
                        val msg = response.getString("message")
                        userDefined.warnDialog(msg, this)
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