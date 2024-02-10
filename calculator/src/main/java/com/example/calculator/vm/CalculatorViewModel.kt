package com.example.calculator.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.Constants
import com.example.calculator.Constants.INVALID_EXPRESSION_WARNING
import com.example.calculator.Constants.LARGE_POWER_THRESHOLD
import com.example.calculator.Constants.LARGE_POWER_WARNING
import com.example.calculator.Constants.LONG_EXPRESSION_WARNING
import com.example.calculator.Constants.MAX_EXPRESSION_LENGTH
import com.example.calculator.Constants.MULTIPLE_ZEROS_WARNING
import com.example.calculator.Constants.OPERATION_PLACEMENT_WARNING
import com.example.calculator.Constants.UNABLE_TO_PERFORM_WARNING
import com.example.calculator.Constants.minusPattern
import com.example.calculator.Constants.pattern
import org.mariuszgromada.math.mxparser.Expression


class CalculatorViewModel : ViewModel() {
    private val expression = Expression()

    private val _currentOperationString = MutableLiveData("")
    val currentOperationString: LiveData<String> get() = _currentOperationString

    private val _selectionStart = MutableLiveData(0)
    val selectionStart: LiveData<Int> get() = _selectionStart

    private val _selectionEnd = MutableLiveData(0)
    val selectionEnd: LiveData<Int> get() = _selectionEnd

    private val _currentResult = MutableLiveData("")
    val currentResult: LiveData<String> get() = _currentResult

    val snackbarMessage = MutableLiveData<String>()

    private val isMaxSize: Boolean get() = _currentOperationString.value!!.length >= MAX_EXPRESSION_LENGTH

    private val isResultEmpty: Boolean get() = _currentResult.value!! == ""

    private val isSelectionOnStringEnd: Boolean get() = _selectionEnd.value != _currentOperationString.value!!.length

    fun reverseFraction() {
        if (isResultEmpty) {
            snackbarMessage.value = UNABLE_TO_PERFORM_WARNING
        } else {
            expression.expressionString = "1/(${_currentResult.value})"
            _currentOperationString.value = expression.calculate().toString()
            _currentResult.value = ""
        }
    }

    fun insert(digit: String, selectionStart: Int, selectionEnd: Int) {
        when {
            isMaxSize -> {
                snackbarMessage.value = LONG_EXPRESSION_WARNING
            }

            _currentOperationString.value!!.isNotEmpty() && selectionStart != 0 -> {
                if (digit == "0" && pattern.containsMatchIn(_currentOperationString.value!![selectionStart - 1].toString())) {
                    snackbarMessage.value = MULTIPLE_ZEROS_WARNING
                    return
                }
                if (_currentOperationString.value!![selectionStart - 1] == '0') {
                    _currentOperationString.value = StringBuilder(_currentOperationString.value ?: "")
                        .replace(selectionStart - 1, selectionEnd, digit)
                        .toString()
                    return
                }
            }

        }
        _currentOperationString.value = StringBuilder(_currentOperationString.value ?: "")
            .replace(selectionStart, selectionEnd, digit)
            .toString()

        setNewSelection(selectionStart, digit)
        tryEvaluateFromString()
    }

    fun removeSymbol(selectionStart: Int, selectionEnd: Int) {
        if (selectionStart == 0) {
            return
        }
        insert("", selectionStart - 1, selectionEnd)
    }


    fun insertComma(selectionStart: Int, selectionEnd: Int) {
        when {
            isMaxSize -> {
                snackbarMessage.value = LONG_EXPRESSION_WARNING
            }

            _currentOperationString.value!!.isNotEmpty() -> {
                tryPutComma(selectionStart, selectionEnd)
            }

            else -> {
                insert("0.", selectionStart, selectionEnd)
            }
        }
    }

    fun insertOperation(operation: String, selectionStart: Int, selectionEnd: Int) {
        when {
            selectionStart == 0 -> {
                snackbarMessage.value = OPERATION_PLACEMENT_WARNING
            }

            isSelectionNearOperation(selectionStart, selectionEnd) -> {
                tryReplaceNearOperation(operation, selectionStart, selectionEnd)
            }

            else -> {
                insert(operation, selectionStart, selectionEnd)
            }
        }
    }

    fun insertMinus(selectionStart: Int, selectionEnd: Int) {
        when {
            selectionStart == 0 -> {
                snackbarMessage.value = OPERATION_PLACEMENT_WARNING
            }

            !isSelectionOnStringEnd
                    && minusPattern.containsMatchIn(_currentOperationString.value!![selectionEnd].toString()) -> {
                insert("-", selectionStart, selectionEnd + 1)
            }

            _currentOperationString.value!![selectionStart - 1] == '+' -> {
                insert("-", selectionStart - 1, selectionStart)
            }

            _currentOperationString.value!![selectionStart - 1] == '-' -> {
                if (minusPattern.containsMatchIn(_currentOperationString.value!![selectionStart - 2].toString())) {
                    insert("-", selectionStart - 2, selectionEnd)
                }
            }

            minusPattern.containsMatchIn(_currentOperationString.value!![selectionStart - 1].toString()) -> {
                if (isSelectionOnStringEnd) {
                    insert("-", selectionStart, selectionEnd)
                } else if (_currentOperationString.value!![selectionEnd] == '-') {
                    insert("-", selectionStart - 1, selectionEnd + 1)
                }
            }

            else -> {
                insert("-", selectionStart, selectionEnd)
            }
        }
    }

    fun evaluate() {
        if (_currentResult.value.isNullOrEmpty()) {
            snackbarMessage.value = INVALID_EXPRESSION_WARNING
        } else {
            _currentOperationString.value = _currentResult.value
            _currentResult.value = ""
        }
    }

    fun clear() {
        _currentResult.value = ""
        _currentOperationString.value = ""
    }

    private fun canInsertComma(selectionStart: Int, selectionEnd: Int): Boolean {
        if (selectionStart > 0 && _currentOperationString.value!![selectionStart - 1].isDigit()) {
            if (isSelectionOnStringEnd) {
                return !_currentOperationString.value!!.substring(0, selectionEnd)
                    .split(pattern).last().contains('.')
            }

            return !_currentOperationString.value!!.substring(selectionEnd - 1)
                .split(pattern)[0].contains('.')
                    && !_currentOperationString.value!!.substring(0, selectionStart + 1)
                .split(pattern).last().contains('.')
        }
        if (selectionStart == 0) return !_currentOperationString.value!!
            .split(pattern)[0].contains('.')
        return _currentOperationString.value!![selectionStart - 1] != '.'
    }

    private fun replaceNearOperation(digit: String, selectionStart: Int, selectionEnd: Int) {
        when {
            pattern.containsMatchIn(_currentOperationString.value!![selectionStart - 1].toString()) -> {
                insert(digit, selectionStart - 1, selectionEnd)
            }

            pattern.containsMatchIn(_currentOperationString.value!![selectionEnd].toString()) -> {
                insert(digit, selectionStart, selectionEnd + 1)
            }
        }
    }

    private fun hasLargePower(): Boolean {
        val parts = _currentOperationString.value!!.split("^")
        for (i in 1 until parts.size) {
            if ((parts[i].toDoubleOrNull() ?: 0.0) > LARGE_POWER_THRESHOLD) {
                return true
            }
        }
        return false
    }

    private fun isSelectionNearOperation(startPosition: Int, endPosition: Int): Boolean {
        val leftChar = if (startPosition > 0) _currentOperationString.value!![startPosition - 1] else null
        val rightChar =
            if (endPosition < _currentOperationString.value!!.length) _currentOperationString.value!![endPosition] else null
        return (leftChar != null && pattern.containsMatchIn(leftChar.toString())) || (rightChar != null && pattern.containsMatchIn(
            rightChar.toString()
        ))
    }

    private fun tryEvaluateFromString() {
        expression.expressionString = _currentOperationString.value
        if (hasLargePower()) {
            snackbarMessage.value = LARGE_POWER_WARNING
            _currentResult.value = ""
        } else {
            val result = expression.calculate()
            if (result.isNaN() || result.isInfinite()) {
                if (snackbarMessage.value != INVALID_EXPRESSION_WARNING) {
                    snackbarMessage.value = INVALID_EXPRESSION_WARNING
                }
                _currentResult.value = ""
            } else {
                _currentResult.value = result.toString()
            }
        }
    }

    private fun setNewSelection(selectionStart: Int, digit: String) {
        _selectionStart.value = selectionStart + digit.length
        _selectionEnd.value = selectionStart + digit.length
    }

    private fun tryReplaceNearOperation(operation: String, selectionStart: Int, selectionEnd: Int) {
        if (isSelectionOnStringEnd) {
            replaceNearOperation(operation, selectionStart, selectionEnd)
        } else if (
            Constants.isContainInPattern(
                _currentOperationString.value!!,
                selectionStart - 1,
                selectionEnd
            )
        ) {
            snackbarMessage.value = OPERATION_PLACEMENT_WARNING
        } else {
            replaceNearOperation(operation, selectionStart, selectionEnd)
        }
    }

    private fun tryPutComma(selectionStart: Int, selectionEnd: Int){
        if (canInsertComma(selectionStart, selectionEnd)) {
            if (selectionStart == 0
                || pattern.containsMatchIn(_currentOperationString.value!![selectionStart - 1].toString())
            ) {
                insert("0.", selectionStart, selectionEnd)
            } else {
                insert(".", selectionStart, selectionEnd)
            }
        }
    }
}