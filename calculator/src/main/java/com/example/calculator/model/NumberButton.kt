package com.example.calculator.model

import android.app.Activity
import android.view.View
import android.widget.EditText
import com.google.android.material.button.MaterialButton


class NumberButton(id: Int, activity: Activity){
    private val buttonView = activity.findViewById<MaterialButton>(id)
    private val numericValue: String = buttonView.text.toString()
    fun setOnClickListener(editText: EditText) {
        buttonView.setOnClickListener(onNumericButtonClickListener(editText))
    }
    private fun onNumericButtonClickListener(
        editText: EditText
    ): View.OnClickListener =
        View.OnClickListener {
            editText.setText(numericValue)
        }
}
