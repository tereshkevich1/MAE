package com.example.calculator.vm

import android.widget.EditText
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

    private val pattern = Regex("[+\\-×/^]")
    fun insertDigit(digit: String, selectionStart: Int, selectionEnd: Int) {
        val newValue = StringBuilder(_currentOperationString.value ?: "")
            .replace(selectionStart, selectionEnd, digit)
            .toString()
        _currentOperationString.value = newValue
        setNewSelection(selectionStart, digit)
        expression.expressionString = _currentOperationString.value
        _currentResult.value = expression.calculate().toString()
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
                insertDigit(operation, selectionStart, selectionEnd)
            }
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

    private fun replaceNearOperation(digit: String, selectionStart: Int, selectionEnd: Int) {
        when{
            pattern.containsMatchIn(_currentOperationString.value!![selectionStart-1].toString()) ->{
                insertDigit(digit, selectionStart-1, selectionStart)

            }
            pattern.containsMatchIn(_currentOperationString.value!![selectionEnd].toString()) ->{
                insertDigit(digit, selectionEnd, selectionEnd+1)

            }
        }
    }

    private fun isSelectionNearOperation(startPosition: Int, endPosition: Int): Boolean {
        val leftChar = _currentOperationString.value!![startPosition - 1]
        if (_currentOperationString.value!!.length == endPosition) {
            return pattern.containsMatchIn(leftChar.toString())
        }
        val rightChar = _currentOperationString.value!![endPosition]
        return pattern.containsMatchIn(leftChar.toString()) || pattern.containsMatchIn(rightChar.toString())
    }

    private fun setNewSelection(selectionStart: Int, digit: String) {
        _selectionStart.value = selectionStart + digit.length
        _selectionEnd.value = selectionStart + digit.length
    }
}