package com.example.cab.activities.resultingInformation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cab.activities.resultingInformation.vm.ResultingInformationViewModel
import com.example.cab.databinding.ActivityResultingInformationBinding
import com.google.android.gms.maps.model.LatLng

class ResultingInformationActivity: AppCompatActivity() {
    private lateinit var resultingInformationViewModel: ResultingInformationViewModel
    private lateinit var resultingInformationBinding: ActivityResultingInformationBinding

    private lateinit var marker1: LatLng
    private lateinit var marker2: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val marker1Lat = intent.getDoubleExtra("marker1Lat", 0.0)
        val marker1Lng = intent.getDoubleExtra("marker1Lng", 0.0)
        val marker2Lat = intent.getDoubleExtra("marker2Lat", 0.0)
        val marker2Lng = intent.getDoubleExtra("marker2Lng", 0.0)

        marker1 = LatLng(marker1Lat, marker1Lng)
        marker2 = LatLng(marker2Lat, marker2Lng)

        resultingInformationViewModel = ResultingInformationViewModel(marker1, marker2)
        setUpBinding()
        setContentView(resultingInformationBinding.root)
    }



    private fun setUpBinding(){
        resultingInformationBinding = ActivityResultingInformationBinding.inflate(layoutInflater)
        resultingInformationBinding.viewModel = resultingInformationViewModel
        resultingInformationBinding.lifecycleOwner = this
    }
}