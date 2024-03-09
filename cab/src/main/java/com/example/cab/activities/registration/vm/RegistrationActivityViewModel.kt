package com.example.cab.activities.registration.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cab.R
import com.example.cab.activities.registration.validators.*
import com.example.cab.activities.registration.validators.components.BaseValidator
import com.example.cab.activities.registration.validators.components.ValidatorResult

class RegistrationActivityViewModel: ViewModel() {
    val name = MutableLiveData("")
    val surname = MutableLiveData("")
    val phone = MutableLiveData("")

    var nameValidator = ValidatorResult(isValid = false, message = R.string.empty_warning)
    var surnameValidator = ValidatorResult(isValid = false, message = R.string.empty_warning)
    var phoneValidator = ValidatorResult(isValid = false, message = R.string.empty_warning)

    fun validationCheck(){
        nameValidator = BaseValidator.validate(
            EmptyValidator(name.value!!),
            NameLengthValidator(name.value!!),
            StringCharactersValidator(name.value!!)
        )
        surnameValidator = BaseValidator.validate(
            EmptyValidator(surname.value!!),
            SurnameLengthValidator(surname.value!!),
            StringCharactersValidator(surname.value!!)
        )
        phoneValidator = BaseValidator.validate(
            EmptyValidator(phone.value!!),
            PhoneNumberValidator(phone.value!!)
        )
    }
}