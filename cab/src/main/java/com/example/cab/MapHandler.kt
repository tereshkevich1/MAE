package com.example.cab

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

class MapHandler(private val activity: AppCompatActivity) :
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    interface CheckLastLocationCallBack {
        fun enableGpsMessage()
        fun setMarkerMessage()
    }

    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false
    private var marker: Marker? = null
    private val defaultLocation = LatLng(53.918599, 27.593955)
    private var lastKnownLocation: Location? = null

    init {
        val mapFragment =
            activity.supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
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

    fun checkLastLocationAndMarker(messageCallBack: CheckLastLocationCallBack) {
        if (lastKnownLocation == null) {
            messageCallBack.enableGpsMessage()
            getDeviceLocation()
        } else if (marker == null) {
            messageCallBack.setMarkerMessage()
        }
    }


    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(activity, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        getDeviceLocation()
        return true
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(activity, "Current location:\n$location", Toast.LENGTH_LONG)
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
                locationResult.addOnCompleteListener(activity) { task ->
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
                        Log.d(ContentValues.TAG, "Current location is null. Using defaults.")
                        Log.e(ContentValues.TAG, "Exception: %s", task.exception)
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
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    companion object {
        //Request code for location permission request.
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }
}
