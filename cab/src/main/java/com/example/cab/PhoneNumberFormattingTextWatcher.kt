package com.example.cab

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneNumberFormattingTextWatcher(private val editText: EditText) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // No action needed here
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        // No action needed here
    }

    override fun afterTextChanged(s: Editable) {
        val text = s.toString()
        val resources = editText.context.resources

        if (text.length == 1 && !text.startsWith("+")) {
            editText.setText(resources.getString(R.string.phone_number_format_plus, text))
            editText.setSelection(editText.text.length)
        } else if (text.length > 4 && text[4] != ' ') {
            editText.setText(resources.getString(R.string.phone_number_format_space, text.substring(0, 4), text.substring(4)))
            editText.setSelection(editText.text.length)
        } else if (text.length > 7 && text[7] != ' ') {
            editText.setText(resources.getString(R.string.phone_number_format_space, text.substring(0, 7), text.substring(7)))
            editText.setSelection(editText.text.length)
        } else if (text.length > 11 && text[11] != ' ') {
            editText.setText(resources.getString(R.string.phone_number_format_space, text.substring(0, 11), text.substring(11)))
            editText.setSelection(editText.text.length)
        } else if (text.length > 14 && text[14] != ' ') {
            editText.setText(resources.getString(R.string.phone_number_format_space, text.substring(0, 14), text.substring(14)))
            editText.setSelection(editText.text.length)
        }
    }
}



