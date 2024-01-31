package com.example.calculator.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    private val _currentOperationString = MutableLiveData("")
    val currentOperationString: LiveData<String> get() = _currentOperationString

    private val _currentResult = MutableLiveData<String>()
    val currentResult: LiveData<String> get() = _currentResult

    fun doSmt(num: String){

    }
    fun appendDigit(
        digit: String
    ){
        _currentOperationString.value += digit
    }

}