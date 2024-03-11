package com.example.cab.activities.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.cab.R
import com.example.cab.activities.registration.model.StoreManager
import com.example.cab.activities.registration.model.UserData
import com.example.cab.activities.registration.vm.RegistrationActivityViewModel
import com.example.cab.activities.registration.watchers.PhoneFormattingTextWatcher
import com.example.cab.activities.registration.watchers.StringFormattingTextWatcher
import com.example.cab.activities.resultingInformation.ResultingInformationActivity
import com.example.cab.databinding.ActivityRegistrationBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data")

class RegistrationActivity : AppCompatActivity() {
    private lateinit var registrationActivityViewModel: RegistrationActivityViewModel
    private lateinit var registrationBinding: ActivityRegistrationBinding
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userData = StoreManager.readUserData(this.baseContext)
        registrationActivityViewModel = ViewModelProvider(this)[RegistrationActivityViewModel::class.java]
        registrationActivityViewModel.viewModelScope.launch {
            registrationActivityViewModel.setUserData(userData.first())
        }
        setUpBinding()
        setContentView(registrationBinding.root)
        findViewsForFields()
        addListeners()
        setButtonOnClickListener()
    }

    private fun findViewsForFields(){
        nameEditText = findViewById(R.id.nameField)
        surnameEditText = findViewById(R.id.surnameField)
        phoneEditText = findViewById(R.id.phoneField)
        nextButton = findViewById(R.id.registration)
    }

    private fun addListeners(){
        phoneEditText.addTextChangedListener(PhoneFormattingTextWatcher(phoneEditText))
        surnameEditText.addTextChangedListener(StringFormattingTextWatcher(surnameEditText))
        nameEditText.addTextChangedListener(StringFormattingTextWatcher(nameEditText))
    }
    private fun setButtonOnClickListener(){
        nextButton.setOnClickListener {
            if (registrationActivityViewModel.validationCheck()) {
                lifecycleScope.launch {
                    StoreManager.saveUserData(
                        this@RegistrationActivity.baseContext,
                        UserData(
                            registrationActivityViewModel.name.value!!,
                            registrationActivityViewModel.surname.value!!,
                            registrationActivityViewModel.phone.value!!
                        )
                    )
                }
            }
            val intent = Intent(this, ResultingInformationActivity::class.java).apply {
                putExtra("marker1Lat", 40.7128)
                putExtra("marker1Lng", -74.0060)
                putExtra("marker2Lat", 51.5074)
                putExtra("marker2Lng", -0.1278)
            }
            startActivity(intent)
            setErrorsOnValidationFailure()
        }
    }

    private fun setErrorsOnValidationFailure(){
        if (!registrationActivityViewModel.nameValidator.isValid) {
            nameEditText.error = getString(registrationActivityViewModel.nameValidator.message)
        }
        if (!registrationActivityViewModel.surnameValidator.isValid) {
            surnameEditText.error = getString(registrationActivityViewModel.surnameValidator.message)
        }
        if (!registrationActivityViewModel.phoneValidator.isValid) {
            phoneEditText.error = getString(registrationActivityViewModel.phoneValidator.message)
        }
    }

    private fun setUpBinding(){
        registrationBinding = ActivityRegistrationBinding.inflate(layoutInflater)
        registrationBinding.viewModel = registrationActivityViewModel
        registrationBinding.lifecycleOwner = this
    }
}