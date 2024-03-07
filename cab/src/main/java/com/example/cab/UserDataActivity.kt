package com.example.cab

import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cab.constants.IntentKeys
import com.example.cab.databinding.UserDataLayoutBinding
import com.example.cab.vm.UserDataViewModel
import com.google.android.material.snackbar.Snackbar


class UserDataActivity : AppCompatActivity(){

    private lateinit var binding: UserDataLayoutBinding
    private lateinit var viewModel: UserDataViewModel

    private var gpsCheck: GPSCheck? = null
   // private lateinit var mapHandler: MapHandler

    /*private val activityLaunch = registerForActivityResult(RouteSettingActivityContract()) {
        when (it) {
            ActionConstants.KEY_SUCCESSFUL -> {
                enableCallTaxiButton()
                Log.d("departure_", intent.getStringExtra(IntentKeys.DEPARTURE).toString())
                getRoute()
            }
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.user_data_layout)

        val mapHandler = MapHandler(this)

        viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.phone.observe(this) {
            binding.phoneTextView.text = it
        }

        viewModel.username.observe(this) {
            binding.usernameTextView.text = it
        }

        viewModel.changePhone(intent.getStringExtra(IntentKeys.PHONE))
        viewModel.changeUsername(intent.getStringExtra(IntentKeys.USERNAME))

        binding.callTaxiButton.setOnClickListener {
            mapHandler.checkLastLocationAndMarker(object: MapHandler.CheckLastLocationCallBack {
                override fun enableGpsMessage() = showSnackbar(R.string.enable_gps)
                override fun setMarkerMessage() = showSnackbar(R.string.select_arrival_point)
            })
        }

    }

    override fun onStart() {
        super.onStart()
        gpsCheck = GPSCheck(object : GPSCheck.LocationCallBack {
            override fun turnedOn() {
               // EnableLocationDialog().show(supportFragmentManager,"ENABLE_GPS")
            }
        })
        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        registerReceiver(gpsCheck, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(gpsCheck)
    }

    private fun showSnackbar(messageInt: Int) {
        val message = getString(messageInt)
        Snackbar.make(binding.callTaxiButton, message, Snackbar.LENGTH_SHORT).show()
    }

}