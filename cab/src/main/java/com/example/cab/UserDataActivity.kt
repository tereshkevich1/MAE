package com.example.cab

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cab.constants.IntentKeys
import com.example.cab.databinding.UserDataLayoutBinding
import com.example.cab.vm.UserDataViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class UserDataActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMapClickListener {

    private lateinit var binding: UserDataLayoutBinding
    private lateinit var viewModel: UserDataViewModel
    private var permissionDenied = false
    private lateinit var map: GoogleMap
    private var marker: Marker? = null

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

        /*
        binding.setPathButton.setOnClickListener {
            activityLaunch.launch("")
        }*/

        binding.callTaxiButton.setOnClickListener {
        }

        

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        googleMap.setOnMapClickListener(this)
        enableMyLocation()
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {

        // [START maps_check_location_permission]
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            return
        }

        // 2. If if a permission rationale dialog should be shown
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            // PermissionUtils.RationaleDialog.newInstance(
            //   LOCATION_PERMISSION_REQUEST_CODE, true
            //).show(supportFragmentManager, "dialog")
            return
        }

        // 3. Otherwise, request permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
        // [END maps_check_location_permission]
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    // [START maps_check_location_permission_result]
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
            return
        }

        /*  if (isPermissionGranted(
            permissions,
            grantResults,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) || isPermissionGranted(
            permissions,
            grantResults,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    ) {*/
        // Enable the my location layer if the permission has been granted.
        enableMyLocation()
        /*} else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
            // [END_EXCLUDE]
        }*/
    }

    // [END maps_check_location_permission_result]
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            // showMissingPermissionError()
            permissionDenied = false
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     *//*
    private fun showMissingPermissionError() {
        newInstance(true).show(supportFragmentManager, "dialog")
    }*/

    companion object {
        /**
         * Request code for location permission request.
         *
         * @see .onRequestPermissionsResult
         */
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMapClick(latLng: LatLng) {
        marker?.remove()
        marker = map.addMarker(MarkerOptions().position(latLng).title("Выбросить"))
        enableCallTaxiButton()
    }


    private fun enableCallTaxiButton() {
        binding.callTaxiButton.isEnabled = true
    }
    /*
    private fun getRoute() {
        viewModel.setRoute(
            Pair(
                intent.getStringExtra(IntentKeys.DEPARTURE),
                intent.getStringExtra(IntentKeys.ARRIVAL)
            )
        )
        Log.d("departure", intent.getStringExtra(IntentKeys.DEPARTURE).toString())
    }
    */
}