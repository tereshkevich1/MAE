package com.example.cab

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneFormattingTextWatcher(private val editText: EditText) : TextWatcher {

    private var isDeleting = false
    private var clearedText = editText.text.toString()
    private var position = editText.selectionStart
    private val positionsForSpaces = listOf(3,5,8,10,12)
    private val spacePositions = listOf(5,8,12,15)
    private val stringBuilder = StringBuilder()

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s != null) {
            clearedText = clearInput(s)
        }
        position = editText.selectionStart
    }

    override fun afterTextChanged(s: Editable) {
        editText.removeTextChangedListener(this)
        editText.setText(createFormattedNumber(clearedText))
        editText.changeSelection()
        editText.addTextChangedListener(this)
    }

    private fun clearInput(input: CharSequence): String {
        stringBuilder.clear()
        for (char in input) {
            if (char.isDigit()) {
                stringBuilder.append(char)
            }
        }
        return stringBuilder.toString()
    }

    private fun createFormattedNumber(input: String): String {
        stringBuilder.clear().append("+")
        for ((index, digit) in input.withIndex()) {
            if (index in positionsForSpaces) {
                stringBuilder.append(" ")
            }
            stringBuilder.append(digit)
        }
        return stringBuilder.toString()
    }

    private fun Int.setAsSelection() {
        editText.setSelection(this.coerceIn(0, editText.text.length))
    }

    private fun EditText.changeSelection(){
        this.adjustSelection()
        position.setAsSelection()
    }

    private fun EditText.adjustSelection(){
        if (!isDeleting) {
            position++
            if (position < this.text.length) {
                position--
                if (position in spacePositions){
                    position++
                }
            }
        }
    }
}

