package com.example.cab.activities.registration.validators

import com.example.cab.R
import com.example.cab.activities.registration.validators.components.BaseValidator
import com.example.cab.activities.registration.validators.components.ValidatorResult

class PhoneNumberValidator(private val input: String): BaseValidator() {
    private val regex = Regex("^\\+\\d{3} \\d{2} \\d{3} \\d{2} \\d{2}$")
    override fun validate(): ValidatorResult {
        val isValid = input.matches(regex)
        return ValidatorResult(
            isValid,
            if(isValid) R.string.success_validation else R.string.invalid_phone_number_warning
        )
    }
}