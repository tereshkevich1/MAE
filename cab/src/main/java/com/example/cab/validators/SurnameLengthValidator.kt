package com.example.cab.validators

import com.example.cab.R
import com.example.cab.util.BaseValidator
import com.example.cab.util.ValidatorResult

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