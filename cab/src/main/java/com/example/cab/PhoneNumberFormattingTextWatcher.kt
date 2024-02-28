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
        val resources = editText.context.resources

        // Remember the original cursor position
        var originalPosition = editText.selectionStart

        // Remove non-numerical characters
        val cleaned = clearInput(s.toString())

        if (isDeleting) {
            editText.setText(cleaned)
        } else {
            if (cleaned.length == 1 && !cleaned.startsWith("+")) {
                editText.setText(resources.getString(R.string.phone_number_format_plus, cleaned))
                originalPosition++
            } else if (cleaned.length > 4 && cleaned[4] != ' ') {
                editText.setText(resources.getString(
                    R.string.phone_number_format_space,
                    cleaned.substring(0, 4),
                    cleaned.substring(4)
                ))
                if (originalPosition > 4) originalPosition++
            } else if (cleaned.length > 7 && cleaned[7] != ' ') {
                editText.setText(resources.getString(
                    R.string.phone_number_format_space,
                    cleaned.substring(0, 7),
                    cleaned.substring(7)
                ))
                if (originalPosition > 7) originalPosition++
            } else if (cleaned.length > 11 && cleaned[11] != ' ') {
                editText.setText(resources.getString(
                    R.string.phone_number_format_space,
                    cleaned.substring(0, 11),
                    cleaned.substring(11)
                ))
                if (originalPosition > 11) originalPosition++
            } else if (cleaned.length > 14 && cleaned[14] != ' ') {
                editText.setText(resources.getString(
                    R.string.phone_number_format_space,
                    cleaned.substring(0, 14),
                    cleaned.substring(14)
                ))
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

    private fun clearInput(input: String): String{
        val cleaned = StringBuilder()
        for (char in input) {
            if (char.isDigit() || char == '+' || char == ' ') {
                cleaned.append(char)
            }
        }
        return cleaned.toString()
    }
}











