package com.example.calculator.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mariuszgromada.math.mxparser.Expression

class CalculatorViewModel : ViewModel() {
    private val expression = Expression()

    private val _currentOperationString = MutableLiveData("")
    val currentOperationString: LiveData<String> get() = _currentOperationString

    private val _selectionStart = MutableLiveData<Int>(0)
    val selectionStart: LiveData<Int> get() = _selectionStart

    private val _selectionEnd = MutableLiveData<Int>(0)
    val selectionEnd: LiveData<Int> get() = _selectionEnd

    private val _currentResult = MutableLiveData("")
    val currentResult: LiveData<String> get() = _currentResult

    val snackbarMessage = MutableLiveData<String>()

    private val pattern = Regex("[+×/^\\-]")
    private val minusPattern = Regex("[/×^]")

    fun insert(digit: String, selectionStart: Int, selectionEnd: Int) {
        if (_currentOperationString.value!!.length + digit.length > 50) {
            snackbarMessage.value = "Expression cannot be longer than 50 characters!"
            return
        }
        if (digit == "0" && (selectionStart == 0 || _currentOperationString.value!![selectionStart - 1] == '0')) {
            snackbarMessage.value = "Cannot enter multiple zeros at the beginning of a number!"
            return
        }
        _currentOperationString.value = StringBuilder(_currentOperationString.value ?: "")
            .replace(selectionStart, selectionEnd, digit)
            .toString()
        setNewSelection(selectionStart, digit)
        expression.setExpressionString(_currentOperationString.value)
        if (hasLargePower()) {
            snackbarMessage.value = "Calculation error: power too large!"
            _currentResult.value = ""
        } else {
            val result = expression.calculate()
            if(result.isNaN()){
                _currentResult.value = ""
            }
            if (result.isInfinite()) {
                snackbarMessage.value = "Calculation error: power too large!"
                _currentResult.value = ""
            } else {
                _currentResult.value = result.toString()
            }
        }
    }

    fun insertComma(selectionStart: Int, selectionEnd: Int) {
        if (_currentOperationString.value!!.length + 1 > 50) {
            snackbarMessage.value = "Expression cannot be longer than 50 characters!"
            return
        }
        _currentOperationString.value = StringBuilder(_currentOperationString.value ?: "")
            .replace(selectionStart, selectionEnd, ",")
            .toString()
        setNewSelection(selectionStart, ",")
    }

    fun insertOperation(operation: String, selectionStart: Int, selectionEnd: Int) {
        when {
            selectionStart == 0 -> {
                snackbarMessage.value = "Invalid operation placement!"
            }
            isSelectionNearOperation(selectionStart, selectionEnd) -> {
                if(_currentOperationString.value!!.length == selectionEnd){
                    replaceNearOperation(operation, selectionStart, selectionEnd)
                } else if (
                    pattern.containsMatchIn(_currentOperationString.value!![selectionStart - 1].toString())
                    && pattern.containsMatchIn(_currentOperationString.value!![selectionEnd].toString())
                ) {
                    snackbarMessage.value = "Invalid operation placement!"
                } else {
                    replaceNearOperation(operation, selectionStart, selectionEnd)
                }
            }

            else -> {
                insert(operation, selectionStart, selectionEnd)
            }
        }
    }

    fun insertMinus(selectionStart: Int, selectionEnd: Int) {
        if (selectionStart == 0 || !minusPattern.containsMatchIn(_currentOperationString.value!![selectionStart - 1].toString())) {
            snackbarMessage.value = "Minus can only be placed after /, *, ^"
            return
        }
        if (_currentOperationString.value!![selectionStart - 1] == '-') {
            _currentOperationString.value = _currentOperationString.value!!.replaceRange(selectionStart - 1, selectionEnd, "")
        } else {
            insert("-", selectionStart, selectionEnd)
        }
    }

    fun evaluate() {
        if(_currentResult.value == ""){
            snackbarMessage.value = "Invalid expression!"
        } else{
            _currentOperationString.value = _currentResult.value
            _currentResult.value = ""
        }
    }

    fun clear() {
        _currentResult.value = ""
        _currentOperationString.value = ""
    }

    private fun replaceNearOperation(digit: String, selectionStart: Int, selectionEnd: Int) {
        when{
            pattern.containsMatchIn(_currentOperationString.value!![selectionStart-1].toString()) ->{
                insert(digit, selectionStart-1, selectionStart)
            }
            pattern.containsMatchIn(_currentOperationString.value!![selectionEnd].toString()) ->{
                insert(digit, selectionEnd, selectionEnd+1)
            }
        }
    }

    private fun hasLargePower(): Boolean {
        val parts = _currentOperationString.value!!.split("^")
        for (i in 1 until parts.size) {
            if (parts[i].toDoubleOrNull() ?: 0.0 > 1000) { // replace 1000 with your threshold
                return true
            }
        }
        return false
    }
    private fun isSelectionNearOperation(startPosition: Int, endPosition: Int): Boolean {
        val leftChar = if (startPosition > 0) _currentOperationString.value!![startPosition - 1] else null
        val rightChar = if (endPosition < _currentOperationString.value!!.length) _currentOperationString.value!![endPosition] else null
        return (leftChar != null && pattern.containsMatchIn(leftChar.toString())) || (rightChar != null && pattern.containsMatchIn(rightChar.toString()))
    }

    private fun setNewSelection(selectionStart: Int, digit: String) {
        _selectionStart.value = selectionStart + digit.length
        _selectionEnd.value = selectionStart + digit.length
    }
}