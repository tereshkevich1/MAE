package com.example.cab.activities.map

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cab.R
import com.example.cab.activities.map.MapHandler.Companion.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.example.cab.activities.map.constants.IntentKeys
import com.example.cab.activities.map.vm.UserDataViewModel
import com.example.cab.databinding.UserDataLayoutBinding
import com.google.android.material.snackbar.Snackbar

class UserDataActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: UserDataLayoutBinding
    private lateinit var viewModel: UserDataViewModel
    private lateinit var mapHandler: MapHandler

    private var gpsCheck: GPSCheck? = null

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
                    override fun enableGpsMessage() = mapHandler.showEnableLocationDialog()
                    override fun setMarkerMessage() = showSnackbar(R.string.select_arrival_point)
                })) {
                val intent = Intent(this@UserDataActivity, MainActivity::class.java)
                intent.putExtra(IntentKeys.DISTANCE, mapHandler.getDistance())
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        gpsCheck = GPSCheck(object : GPSCheck.LocationCallBack {
            override fun turnedOff() {
                   Log.d("GPS_LOG","GPS turned off")
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    mapHandler.onLocationPermissionGranted()
                }
            }
            /*
            else -> {
                this.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }*/
        }
    }

    private fun showSnackbar(messageInt: Int) {
        val message = getString(messageInt)
        Snackbar.make(binding.callTaxiButton, message, Snackbar.LENGTH_SHORT).show()
    }

    fun startGpsSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

}