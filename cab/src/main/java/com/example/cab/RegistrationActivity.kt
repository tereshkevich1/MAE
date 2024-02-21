package com.example.cab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.cab.databinding.ActivityRegistrationBinding
import com.example.cab.vm.RegistrationActivityViewModel

class RegistrationActivity : AppCompatActivity() {
    private lateinit var registrationActivityViewModel: RegistrationActivityViewModel
    private lateinit var registrationBinding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registrationBinding = ActivityRegistrationBinding.inflate(layoutInflater)
        registrationActivityViewModel = ViewModelProvider(this)[RegistrationActivityViewModel::class.java]

        val registrationView = registrationBinding.root
        registrationBinding.viewModel = registrationActivityViewModel
        registrationBinding.lifecycleOwner = this

        setContentView(registrationView)
    }
}