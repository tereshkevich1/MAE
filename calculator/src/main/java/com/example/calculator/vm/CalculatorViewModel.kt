package com.example.calculator.vm

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mariuszgromada.math.mxparser.Expression

class CalculatorViewModel : ViewModel() {
    private val expression = Expression()

    private val _currentOperationString = MutableLiveData("")
    val currentOperationString: LiveData<String> get() {
        expression.expressionString = _currentOperationString.value
        return _currentOperationString
    }

    private val _currentResult = MutableLiveData("")
    val currentResult: LiveData<String> get() = _currentResult

    val snackbarMessage = MutableLiveData<String>()

    fun insertDigit(editText: EditText, digit: String) {
        editText.text.replace(getSelectionStart(editText), getSelectionEnd(editText), digit)
        _currentOperationString.value = editText.text.toString()
        _currentResult.value = expression.calculate().toString()
    }
    //TODO("Fix result output")
    fun insertOperation(editText: EditText, operation: String) {
        val startPosition = getSelectionStart(editText)
        val endPosition = getSelectionEnd(editText)
        if (startPosition == 0
            || _currentOperationString.value!!.isEmpty()
            || isPlacedNearOperation(startPosition, endPosition)
        ) {
            snackbarMessage.value = "Your message here"
        } else {
            insertDigit(editText, operation)
        }

    }

    fun insertMinus(editText: EditText) {

    }

    fun evaluate() {
        _currentOperationString.value = _currentResult.value
        _currentResult.value = ""
    }

    fun clear() {
        _currentResult.value = ""
        _currentOperationString.value = ""
    }

    private fun getSelectionEnd(editText: EditText): Int {
        return editText.selectionEnd
    }

    private fun getSelectionStart(editText: EditText): Int {
        return editText.selectionStart
    }

    private fun isPlacedNearOperation(startPosition: Int, endPosition: Int): Boolean {
        val pattern = Regex("[+\\-×/^]")
        val leftChar = _currentOperationString.value!![startPosition - 1]
        if (_currentOperationString.value!!.length == endPosition){
            return pattern.containsMatchIn(leftChar.toString())
        }
        val rightChar = _currentOperationString.value!![endPosition]
        return pattern.containsMatchIn(leftChar.toString()) || pattern.containsMatchIn(rightChar.toString())
    }
}