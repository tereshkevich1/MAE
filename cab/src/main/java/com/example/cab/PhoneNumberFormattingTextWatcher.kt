package com.example.cab

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneNumberFormattingTextWatcher(private val editText: EditText) : TextWatcher {

    private var isDeleting = false
    private var clearedText = editText.text.toString()
    private var position = editText.selectionStart
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        clearedText = clearInput(s.toString())
        position = editText.selectionStart
    }

    override fun afterTextChanged(s: Editable) {
        editText.removeTextChangedListener(this)
        editText.setText(formatPhone(clearedText))
        editText.changeSelection()
        editText.addTextChangedListener(this)
    }

    private fun clearInput(input: String): String {
        val cleaned = StringBuilder()
        for (char in input) {
            if (char.isDigit()) {
                cleaned.append(char)
            }
        }
        return cleaned.toString()
    }

    private fun formatPhone(input: String): String {
        val formattedNumber = StringBuilder("+")
        for ((index, digit) in input.withIndex()) {
            if (index == 3 || index == 5 || index == 8 || index == 10 || index == 12) {
                formattedNumber.append(" ")
            }
            formattedNumber.append(digit)
        }
        return formattedNumber.toString()
    }

    private fun Int.setAsSelection() {
        if (this < editText.text.length) {
            editText.setSelection(this)
        } else {
            editText.setSelection(editText.text.length)
        }
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
            }
        }
    }
}











