package com.rocqjones.weathertop.dashboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rocqjones.weathertop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

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
        }
    }
}