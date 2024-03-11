package com.example.cab.activities.map

import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cab.R
import com.example.cab.activities.map.constants.IntentKeys
import com.example.cab.activities.map.vm.UserDataViewModel
import com.example.cab.databinding.UserDataLayoutBinding
import com.google.android.material.snackbar.Snackbar


class UserDataActivity : AppCompatActivity() {

    private lateinit var binding: UserDataLayoutBinding
    private lateinit var viewModel: UserDataViewModel
    private lateinit var mapHandler: MapHandler

    private var gpsCheck: GPSCheck? = null

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

        mapHandler = MapHandler(this)
        if (savedInstanceState != null) {
            mapHandler.onRestoreInstanceState(savedInstanceState)
        }

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
            if (mapHandler.checkLastLocationAndMarker(object :
                    MapHandler.CheckLastLocationCallBack {
                    override fun enableGpsMessage() = showSnackbar(R.string.enable_gps)
                    override fun setMarkerMessage() = showSnackbar(R.string.select_arrival_point)
                })) {
                val intent = Intent(this@UserDataActivity, MainActivity::class.java)
                intent.putExtra("user", mapHandler.getUserCoordinates())
                intent.putExtra("marker", mapHandler.getMarkerCoordinates())
                startActivity(intent)

            }
        }

    }

    override fun onStart() {
        super.onStart()
        gpsCheck = GPSCheck(object : GPSCheck.LocationCallBack {
            override fun turnedOn() {
                mapHandler.onMyLocationButtonClick()
            }

            override fun turnedOff() {
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapHandler.onSaveInstanceState(outState)

    }

    private fun showSnackbar(messageInt: Int) {
        val message = getString(messageInt)
        Snackbar.make(binding.callTaxiButton, message, Snackbar.LENGTH_SHORT).show()
    }

}