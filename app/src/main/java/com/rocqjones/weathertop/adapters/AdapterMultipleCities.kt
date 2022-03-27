package com.rocqjones.weathertop.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rocqjones.weathertop.databinding.ItemDashCardBinding
import com.rocqjones.weathertop.methods.UserDefined
import com.rocqjones.weathertop.model.CitiesModel

class AdapterMultipleCities(var citiesModelList: MutableList<CitiesModel?>?, var mContext: Context) :
    RecyclerView.Adapter<AdapterMultipleCities.RVHolder>() {

    var binding: ItemDashCardBinding? = null
    var userDefined = UserDefined()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        binding = ItemDashCardBinding.inflate(LayoutInflater.from(parent.context))
        val view: View = binding!!.root
        return RVHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        holder.locationName.text =
            citiesModelList!![position]?.name + ", " + citiesModelList!![position]?.country
        holder.degreeTv.text =
            userDefined.convertKelvinToCelsius(citiesModelList!![position]?.temp!!.toDouble()).toString() + "\u2103"
        holder.dateTimeTv.text = userDefined.currentDateTime()
        holder.windSpeed.text = "Wind Speed: " + citiesModelList!![position]?.speed + " FPM"
        holder.description.text = citiesModelList!![position]?.description
    }

    override fun getItemCount(): Int {
        return citiesModelList!!.size
    }

    class RVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var locationName: TextView
        var dateTimeTv: TextView
        var degreeTv: TextView
        var windSpeed: TextView
        var description: TextView

        init {
            val itemDashCardBinding: ItemDashCardBinding = ItemDashCardBinding.bind(itemView)
            locationName = itemDashCardBinding.locationName
            dateTimeTv = itemDashCardBinding.dateTimeTv
            degreeTv = itemDashCardBinding.degreeTv
            windSpeed = itemDashCardBinding.windSpeed
            description = itemDashCardBinding.description
        }
    }
}