package com.example.cab.activities.registration.validators

import com.example.cab.R
import com.example.cab.activities.registration.validators.components.BaseValidator
import com.example.cab.activities.registration.validators.components.ValidatorResult

class StringCharactersValidator(private val input: String): BaseValidator() {
    private val regex = Regex("[A-Za-z ]+")
    override fun validate(): ValidatorResult {
        val isValid = input.matches(regex) && !input.startsWith(" ")
        return ValidatorResult(
            isValid,
            if (isValid) R.string.success_validation else R.string.invalid_characters_warning
        )
    }
}