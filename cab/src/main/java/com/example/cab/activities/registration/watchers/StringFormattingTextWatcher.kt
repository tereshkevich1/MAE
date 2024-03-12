package com.example.cab.activities.registration.watchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class StringFormattingTextWatcher(private val editText: EditText) : TextWatcher {

    private var fieldText = editText.text.toString()
    private var position = editText.selectionStart

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // No action needed here
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        fieldText = s.toString()
        position = editText.selectionStart
    }

    override fun afterTextChanged(s: Editable) {
        editText.removeTextChangedListener(this)
        editText.formatString(s)
        editText.setSelection(position)
        editText.addTextChangedListener(this)
    }

    private fun EditText.formatString(editable: Editable){
        this.deleteFirstSpaces(editable)
        this.deleteDots(editable)
        this.deleteDoubleSpaces(editable)
    }

    private fun EditText.deleteFirstSpaces(editable: Editable){
        if (editable.startsWith(" ")) {
            this.setText(fieldText.trimStart())
            position = 0
        }
    }

    private fun EditText.deleteDoubleSpaces(editable: Editable){
        if (editable.contains("  ")) {
            this.setText(fieldText.replace("  ", " "))
            position--
        }
    }

    private fun EditText.deleteDots(editable: Editable){
        if (editable.contains(".")){
            this.setText(fieldText.replace(".", ""))
            position--
        }
    }
}
