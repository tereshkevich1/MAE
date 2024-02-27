package com.example.cab

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneNumberFormattingTextWatcher(private val mEditText: EditText) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // No action needed here
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        // No action needed here
    }

    override fun afterTextChanged(s: Editable) {
        val text = s.toString()
        if (text.length == 1 && !text.startsWith("+")) {
            mEditText.setText("+$text")
            mEditText.setSelection(mEditText.text.length)
        } else if (text.length > 4 && text[4] != ' ') {
            mEditText.setText(text.substring(0, 4) + " " + text.substring(4))
            mEditText.setSelection(mEditText.text.length)
        } else if (text.length > 7 && text[7] != ' ') {
            mEditText.setText(text.substring(0, 7) + " " + text.substring(7))
            mEditText.setSelection(mEditText.text.length)
        } else if (text.length > 11 && text[11] != ' ') {
            mEditText.setText(text.substring(0, 11) + " " + text.substring(11))
            mEditText.setSelection(mEditText.text.length)
        } else if (text.length > 14 && text[14] != ' ') {
            mEditText.setText(text.substring(0, 14) + " " + text.substring(14))
            mEditText.setSelection(mEditText.text.length)
        }
    }
}



