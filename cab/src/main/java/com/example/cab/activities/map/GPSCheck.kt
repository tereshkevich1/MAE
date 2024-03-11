package com.example.cab.activities.map

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.util.Log

class GPSCheck(private val locationCallBack: LocationCallBack) : BroadcastReceiver() {

    interface LocationCallBack {
        fun turnedOff()
        fun turnedOn()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationCallBack.turnedOff()
        } else {
            Log.d("On780", "gps on")
            locationCallBack.turnedOn()
        }
    }
}