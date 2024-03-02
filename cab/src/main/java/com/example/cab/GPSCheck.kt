package com.example.cab

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager

class GPSCheck(private val locationCallBack: LocationCallBack): BroadcastReceiver() {

    interface LocationCallBack {
        fun turnedOn()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationCallBack.turnedOn()
        }
    }
}