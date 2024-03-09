package com.example.cab.activities.registration.watchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneFormattingTextWatcher(private val editText: EditText) : TextWatcher {

    companion object {
        /**
        Positions of digits in unformatted phone, after which space have to be placed
        */
        private val POSITIONS_FOR_SPACES = listOf(3,5,8,10,12)

        /**
        Positions of digits right after spaces in formatted phone.
        Using to adjust cursor position
         */
        private val ON_ADJUSTING_POSITIONS = listOf(5,8,12,15)
    }

    private var isDeleting = false
    private var unformattedPhone = editText.text.toString()
    private var position = editText.selectionStart
    private val stringBuilder = StringBuilder()

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s != null) {
            unformattedPhone = s.toUnformatted().toString()
        }
        position = editText.selectionStart
    }

    override fun afterTextChanged(s: Editable) {
        editText.removeTextChangedListener(this)
        editText.setText(createFormattedNumber(unformattedPhone))
        editText.changeSelection()
        editText.addTextChangedListener(this)
    }

    // Clears non-digit characters from the input
    private fun CharSequence.toUnformatted(): CharSequence {
        return this.filter { it.isDigit() }
    }

    // Formats the input string as a phone number
    private fun createFormattedNumber(input: String): String {
        stringBuilder.clear().append("+")
        for ((index, digit) in input.withIndex()) {
            if (index in POSITIONS_FOR_SPACES) {
                stringBuilder.append(" ")
            }
            stringBuilder.append(digit)
        }
        return stringBuilder.toString()
    }

    private fun Int.setAsSelection() {
        editText.setSelection(this.coerceIn(0, editText.text.length))
    }

    // Adjusts the selection position based on whether text is being deleted or added
    private fun EditText.changeSelection(){
        this.adjustSelection()
        position.setAsSelection()
    }

    private fun EditText.adjustSelection(){
        if (!isDeleting) {
            position++
            if (position < this.text.length) {
                position--
                if (position in ON_ADJUSTING_POSITIONS){
                    position++
                }
            }
        }
    }
}


