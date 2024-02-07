package com.example.calculator.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.Constants.INVALID_EXPRESSION_WARNING
import com.example.calculator.Constants.LARGE_POWER_THRESHOLD
import com.example.calculator.Constants.LARGE_POWER_WARNING
import com.example.calculator.Constants.LONG_EXPRESSION_WARNING
import com.example.calculator.Constants.MAX_EXPRESSION_LENGTH
import com.example.calculator.Constants.MULTIPLE_POINTS_WARNING
import com.example.calculator.Constants.MULTIPLE_ZEROS_WARNING
import com.example.calculator.Constants.OPERATION_PLACEMENT_WARNING
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

    fun insert(digit: String, selectionStart: Int, selectionEnd: Int) {
        if (_currentOperationString.value!!.length + digit.length > MAX_EXPRESSION_LENGTH) {
            snackbarMessage.value = LONG_EXPRESSION_WARNING
            return
        }
        if (digit == "0") {
            if (_currentOperationString.value!! != "" && selectionStart != 0) {
                if (_currentOperationString.value!![selectionStart - 1] == '0') {
                    snackbarMessage.value = MULTIPLE_ZEROS_WARNING
                    return
                }
            }
        } else {
            if (_currentOperationString.value!! != "" && selectionStart != 0) {
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
        expression.expressionString = _currentOperationString.value
        if (hasLargePower()) {
            snackbarMessage.value = LARGE_POWER_WARNING
            _currentResult.value = ""
        } else {
            val result = expression.calculate()
            if (result.isNaN() || result.isInfinite()) {
                snackbarMessage.value = INVALID_EXPRESSION_WARNING
                _currentResult.value = ""
            } else {
                _currentResult.value = result.toString()
            }
        }
    }


    fun insertComma(selectionStart: Int, selectionEnd: Int) {
        if (_currentOperationString.value!!.length + 1 > MAX_EXPRESSION_LENGTH) {
            snackbarMessage.value = LONG_EXPRESSION_WARNING
            return
        }
        if (_currentOperationString.value!! != "") {
            if (_currentOperationString.value!!.contains('.')) {
                if (_currentOperationString.value!!.substring(_currentOperationString.value!!.indexOf('.') + 1)
                        .contains('.')
                ) {
                    snackbarMessage.value = MULTIPLE_POINTS_WARNING
                    return
                }
            }
            _currentOperationString.value = StringBuilder(_currentOperationString.value ?: "")
                .replace(selectionStart, selectionEnd, ".")
                .toString()
            setNewSelection(selectionStart, ".")
        } else {
            _currentOperationString.value = "0."
            setNewSelection(selectionStart, _currentOperationString.value!!)
        }
    }

    fun insertOperation(operation: String, selectionStart: Int, selectionEnd: Int) {
        when {
            selectionStart == 0 -> {
                snackbarMessage.value = OPERATION_PLACEMENT_WARNING
            }

            isSelectionNearOperation(selectionStart, selectionEnd) -> {
                if (_currentOperationString.value!!.length == selectionEnd) {
                    replaceNearOperation(operation, selectionStart, selectionEnd)
                } else if (
                    pattern.containsMatchIn(_currentOperationString.value!![selectionStart - 1].toString())
                    && pattern.containsMatchIn(_currentOperationString.value!![selectionEnd].toString())
                ) {
                    snackbarMessage.value = OPERATION_PLACEMENT_WARNING
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
        when {
            selectionStart == 0 -> {
                snackbarMessage.value = OPERATION_PLACEMENT_WARNING
            }

            _currentOperationString.value!![selectionStart - 1] == '+' -> {
                insert("-", selectionStart - 1, selectionStart)
            }

            _currentOperationString.value!![selectionStart - 1] == '-' -> {
                if (minusPattern.containsMatchIn(_currentOperationString.value!![selectionStart - 2].toString())) {
                    insert("-", selectionStart - 2, selectionEnd)
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

    private fun replaceNearOperation(digit: String, selectionStart: Int, selectionEnd: Int) {
        when {
            pattern.containsMatchIn(_currentOperationString.value!![selectionStart - 1].toString()) -> {
                insert(digit, selectionStart - 1, selectionStart)
            }

            pattern.containsMatchIn(_currentOperationString.value!![selectionEnd].toString()) -> {
                insert(digit, selectionEnd, selectionEnd + 1)
            }
        }
    }

    private fun hasLargePower(): Boolean {
        val parts = _currentOperationString.value!!.split("^")
        for (i in 1 until parts.size) {
            if (parts[i].toDoubleOrNull() ?: 0.0 > LARGE_POWER_THRESHOLD) {
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

    private fun setNewSelection(selectionStart: Int, digit: String) {
        _selectionStart.value = selectionStart + digit.length
        _selectionEnd.value = selectionStart + digit.length
    }
}