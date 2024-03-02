package com.example.cab

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cab.constants.IntentKeys
import com.example.cab.databinding.UserDataLayoutBinding
import com.example.cab.vm.UserDataViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.google.android.material.snackbar.Snackbar


class UserDataActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMapClickListener {

    private lateinit var binding: UserDataLayoutBinding
    private lateinit var viewModel: UserDataViewModel

    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false
    private var marker: Marker? = null
    private val defaultLocation = LatLng(53.918599, 27.593955)
    private var lastKnownLocation: Location? = null

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

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

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
            checkLastLocationAndMarker()
        }

    }

    override fun onStart() {
        super.onStart()
        gpsCheck = GPSCheck(object : GPSCheck.LocationCallBack {
            override fun turnedOn() {
                EnableLocationDialog().show(supportFragmentManager,"ENABLE_GPS")
            }
        })
        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        registerReceiver(gpsCheck, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(gpsCheck)
    }

    private fun checkLastLocationAndMarker() {
        if (lastKnownLocation == null) {
            showSnackbar(R.string.enable_gps)
            getDeviceLocation()
        } else if (marker == null) {
            showSnackbar(R.string.select_arrival_point)
        }
    }

    private fun showSnackbar(messageInt: Int) {
        val message = getString(messageInt)
        Snackbar.make(binding.callTaxiButton, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        googleMap.setOnMapClickListener(this)

        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()

    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        getDeviceLocation()
        return true
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    override fun onMapClick(latLng: LatLng) {
        marker?.remove()
        marker = map?.addMarker(MarkerOptions().position(latLng).title("Выбросить"))
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), 16f
                                )
                            )
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, 16f)
                        )
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


    // [START maps_check_location_permission_result]
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }

            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        updateLocationUI()
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    companion object {
        //Request code for location permission request.
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }
}