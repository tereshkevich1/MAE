package com.example.calculator.model

import android.app.Activity
import android.view.View
import com.google.android.material.button.MaterialButton


class NumberButton(id: Int, activity: Activity){
    private val buttonView = activity.findViewById<MaterialButton>(id)
    val numericValue: String = buttonView.text.toString()
    fun setOnClickListener(listener: View.OnClickListener) {
        buttonView.setOnClickListener(listener)
    }
}
