package com.example.cab.activities.registration.validators

import com.example.cab.R
import com.example.cab.activities.registration.validators.components.BaseValidator
import com.example.cab.activities.registration.validators.components.ValidatorResult

class NameLengthValidator(private val input: String): BaseValidator() {
    private val maximumSize = 30
    override fun validate(): ValidatorResult {
        val isValid = input.length <= maximumSize
        return ValidatorResult(
            isValid,
            if (isValid) R.string.success_validation else R.string.name_length_warning
        )
    }
}