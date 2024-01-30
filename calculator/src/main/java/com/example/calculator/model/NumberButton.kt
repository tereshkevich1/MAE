package com.example.calculator.model

import android.app.Activity
import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import com.example.calculator.R
import com.example.calculator.vm.CalculatorViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar


class NumberButton(id: Int, activity: Activity){
    private val buttonView = activity.findViewById<MaterialButton>(id)
    private val numericValue: String = buttonView.text.toString()

    fun setOnClickListener(vm: CalculatorViewModel) {
        buttonView.setOnClickListener(onNumericButtonClickListener(vm))
    }
    private fun onNumericButtonClickListener(
        vm: CalculatorViewModel
    ): View.OnClickListener =
        View.OnClickListener {
            val inputFilter = NumberInputFilter(buttonView.context)
            val filters = arrayOf(inputFilter)
            vm.appendDigit(numericValue)
        }
}

class NumberInputFilter(private val context: Context) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val input = (dest?.subSequence(0, dstart).toString()
                + source?.subSequence(start, end).toString()
                + dest?.subSequence(dend, dest.length).toString())
        if (input.matches(Regex("\\d{0,9}"))) {
            return null
        } else {
            val view = View.inflate(context, R.layout.activity_main, null)
            Snackbar.make(view, "Maximum number length is 9 digits", Snackbar.LENGTH_SHORT).show()
            return ""
        }
    }
}
