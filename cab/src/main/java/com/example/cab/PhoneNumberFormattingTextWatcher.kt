package com.example.cab

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneNumberFormattingTextWatcher(private val editText: EditText) : TextWatcher {

    private var isDeleting = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // No operation
    }

    override fun afterTextChanged(s: Editable) {
        editText.removeTextChangedListener(this)

        val text = s.toString()
        val resources = editText.context.resources

        // Remember the original cursor position
        var originalPosition = editText.selectionStart

        // Remove non-numerical characters
        val cleaned = StringBuilder()
        for (char in text) {
            if (char.isDigit() || char == '+' || char == ' ') {
                cleaned.append(char)
            }
        }

        val cleanedText = cleaned.toString()

        if (isDeleting) {
            editText.setText(cleanedText)
        } else {
            if (cleanedText.length == 1 && !cleanedText.startsWith("+")) {
                editText.setText(resources.getString(R.string.phone_number_format_plus, cleanedText))
                originalPosition++
            } else if (cleanedText.length > 4 && cleanedText[4] != ' ') {
                editText.setText(resources.getString(R.string.phone_number_format_space, cleanedText.substring(0, 4), cleanedText.substring(4)))
                if (originalPosition > 4) originalPosition++
            } else if (cleanedText.length > 7 && cleanedText[7] != ' ') {
                editText.setText(resources.getString(R.string.phone_number_format_space, cleanedText.substring(0, 7), cleanedText.substring(7)))
                if (originalPosition > 7) originalPosition++
            } else if (cleanedText.length > 11 && cleanedText[11] != ' ') {
                editText.setText(resources.getString(R.string.phone_number_format_space, cleanedText.substring(0, 11), cleanedText.substring(11)))
                if (originalPosition > 11) originalPosition++
            } else if (cleanedText.length > 14 && cleanedText[14] != ' ') {
                editText.setText(resources.getString(R.string.phone_number_format_space, cleanedText.substring(0, 14), cleanedText.substring(14)))
                if (originalPosition > 14) originalPosition++
            }
        }

        // Restore the cursor position
        if (originalPosition <= editText.text.length) {
            editText.setSelection(originalPosition)
        } else {
            editText.setSelection(editText.text.length)
        }

        editText.addTextChangedListener(this)
    }
}











