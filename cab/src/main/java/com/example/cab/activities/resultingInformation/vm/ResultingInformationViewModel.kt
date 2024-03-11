package com.example.cab.activities.resultingInformation.vm

import android.location.Location
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class ResultingInformationViewModel(
    private val marker1: LatLng,
    private val marker2: LatLng,
) : ViewModel() {
    val distance: String = "${calculateDistance()} km"

    private fun calculateDistance(): Float {
        val location1 = Location("locationA")
        location1.latitude = marker1.latitude
        location1.longitude = marker1.longitude

        val location2 = Location("locationB")
        location2.latitude = marker2.latitude
        location2.longitude = marker2.longitude

        return location1.distanceTo(location2)/1000
    }

}