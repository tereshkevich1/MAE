package com.example.cab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
        val nameEditText: EditText = findViewById(R.id.nameField)
        val surnameEditText: EditText = findViewById(R.id.surnameField)
        val phoneEditText: EditText = findViewById(R.id.phoneField)

        val nextButton: Button = findViewById(R.id.registration)


        nextButton.setOnClickListener {
            registrationActivityViewModel.check()
            if(!registrationActivityViewModel.nameValidator.value!!.isValid){
                nameEditText.error = getString(registrationActivityViewModel.nameValidator.value!!.message)
            }
            Log.d(
                "f", registrationActivityViewModel._name.value ?: "null"
            )
        }
    }
}