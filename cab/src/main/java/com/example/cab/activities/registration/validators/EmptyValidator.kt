package com.example.cab.activities.registration.validators

import com.example.cab.R
import com.example.cab.activities.registration.validators.components.BaseValidator
import com.example.cab.activities.registration.validators.components.ValidatorResult

class EmptyValidator(private val input: String): BaseValidator() {
    override fun validate(): ValidatorResult {
        val isValid = input.isNotEmpty()
        return ValidatorResult(
            isValid,
            if(isValid) R.string.success_validation else R.string.empty_warning
        )
    }
}