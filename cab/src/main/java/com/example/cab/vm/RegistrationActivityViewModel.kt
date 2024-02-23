package com.example.cab.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationActivityViewModel: ViewModel() {
    private val _name = MutableLiveData("")
    private val _surname = MutableLiveData("")
    private val _phone = MutableLiveData<String>()

    val name: LiveData<String> = _name
    val surname: LiveData<String> = _surname
    val phone: LiveData<String> = _phone



}