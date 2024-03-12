package com.example.cab.activities.registration.validators.components

import com.example.cab.R


abstract class BaseValidator: IValidator {

    companion object{
        fun validate(vararg validators: IValidator): ValidatorResult {
            validators.forEach {
                val result = it.validate()
                if(!result.isValid){
                    return result
                }
            }
            return ValidatorResult(true, R.string.success_validation)
        }
    }
}