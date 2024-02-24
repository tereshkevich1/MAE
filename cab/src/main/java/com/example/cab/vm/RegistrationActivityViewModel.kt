package com.example.cab.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cab.R
import com.example.cab.util.BaseValidator
import com.example.cab.util.ValidatorResult
import com.example.cab.validators.EmptyValidator

class RegistrationActivityViewModel: ViewModel() {
    val _name = MutableLiveData("")
    val _surname = MutableLiveData("")
    val _phone = MutableLiveData("")

    val nameValidator = MutableLiveData(ValidatorResult(isValid = false, message = R.string.empty_warning))

    fun check(){
        nameValidator.value = BaseValidator.validate(EmptyValidator(_name.value!!))
    }
}