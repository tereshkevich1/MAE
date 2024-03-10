package com.example.cab.activities.registration.validators

import com.example.cab.R
import com.example.cab.activities.registration.validators.components.BaseValidator
import com.example.cab.activities.registration.validators.components.ValidatorResult

class SurnameLengthValidator(private val input: String): BaseValidator() {
    private val maximumSize = 50
    override fun validate(): ValidatorResult {
        val isValid = input.length <= maximumSize
        return ValidatorResult(
            isValid,
            if (isValid) R.string.success_validation else R.string.surname_length_warning
        )
    }
}