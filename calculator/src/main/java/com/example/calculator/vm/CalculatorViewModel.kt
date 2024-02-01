package com.example.calculator.vm

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    private val _currentOperationString = MutableLiveData("")
    val currentOperationString: LiveData<String> get() = _currentOperationString

    private val _currentResult = MutableLiveData("")
    val currentResult: LiveData<String> get() = _currentResult

    fun insertDigit(editText: EditText, digit: String) {
        val position = getCursorPosition(editText)
        editText.text.insert(position, digit)
    }
    fun getCursorPosition(editText: EditText): Int {
        return editText.selectionStart
    }

}