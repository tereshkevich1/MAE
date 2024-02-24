package com.example.cab.validators

import com.example.cab.R
import com.example.cab.util.BaseValidator
import com.example.cab.util.ValidatorResult

class EmptyValidator(val input: String): BaseValidator() {
    override fun validate(): ValidatorResult {
        val isValid = input.isNotEmpty()
        return ValidatorResult(
            isValid,
            if(isValid) R.string.success_validation else R.string.empty_warning
        )
    }
}