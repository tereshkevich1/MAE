package com.example.cab.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cab.R
import com.example.cab.util.BaseValidator
import com.example.cab.util.ValidatorResult
import com.example.cab.validators.*

class RegistrationActivityViewModel: ViewModel() {
    val _name = MutableLiveData("")
    val _surname = MutableLiveData("")
    val _phone = MutableLiveData("")

    var nameValidator = ValidatorResult(isValid = false, message = R.string.empty_warning)
    var surnameValidator = ValidatorResult(isValid = false, message = R.string.empty_warning)
    var phoneValidator = ValidatorResult(isValid = false, message = R.string.empty_warning)

    fun validationCheck(){
        nameValidator = BaseValidator.validate(
            EmptyValidator(_name.value!!),
            NameLengthValidator(_name.value!!),
            StringCharactersValidator(_name.value!!)
        )
        surnameValidator = BaseValidator.validate(
            EmptyValidator(_surname.value!!),
            SurnameLengthValidator(_surname.value!!),
            StringCharactersValidator(_surname.value!!)
        )
        phoneValidator = BaseValidator.validate(
            EmptyValidator(_phone.value!!),
            PhoneNumberValidator(_phone.value!!)
        )
    }
}