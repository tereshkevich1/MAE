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
        editText.text.replace(getSelectionStart(editText), getSelectionEnd(editText), digit)
    }

    fun insertOperation(editText: EditText, operation: String){
        val position = getSelectionStart(editText)

    }

    fun evaluate(){
        _currentOperationString.value = _currentResult.value
        _currentResult.value = ""
    }

    fun clear(){
        _currentResult.value = ""
        _currentOperationString.value = ""
    }
    private fun getSelectionEnd(editText: EditText): Int{
        return editText.selectionEnd
    }
    private fun getSelectionStart(editText: EditText): Int {
        return editText.selectionStart
    }

}