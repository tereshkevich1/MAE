package com.example.cab.activities.resultingInformation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cab.R
import com.example.cab.activities.registration.RegistrationActivity
import com.example.cab.activities.resultingInformation.vm.ResultingInformationViewModel
import com.example.cab.databinding.ActivityResultingInformationBinding
import com.example.cab.model.StoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
class ResultingInformationActivity: AppCompatActivity() {
    private lateinit var resultingInformationViewModel: ResultingInformationViewModel
    private lateinit var resultingInformationBinding: ActivityResultingInformationBinding

    private lateinit var againButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val distance = intent.getFloatExtra("distance", 0.0f)
        val userData = StoreManager.readUserData(this.baseContext)
        lifecycleScope.launch {
            resultingInformationViewModel = ResultingInformationViewModel(distance, userData.first())
            setUpBinding()
        }

        setContentView(resultingInformationBinding.root)
        againButton = findViewById(R.id.call_again)
        setButtonOnClickListener()
    }

    private fun setButtonOnClickListener(){
        againButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpBinding(){
        resultingInformationBinding = ActivityResultingInformationBinding.inflate(layoutInflater)
        resultingInformationBinding.viewModel = resultingInformationViewModel
        resultingInformationBinding.lifecycleOwner = this
    }
}