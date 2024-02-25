package com.example.cab.validators

import com.example.cab.R
import com.example.cab.util.BaseValidator
import com.example.cab.util.ValidatorResult

class PhoneNumberValidator(private val input: String): BaseValidator() {
    private val regex = Regex("^\\+[0-9]{10,13}$")
    override fun validate(): ValidatorResult {
        val isValid = input.matches(regex)
        return ValidatorResult(
            isValid,
            if(isValid) R.string.success_validation else R.string.invalid_phone_number_warning
        )
    }
}