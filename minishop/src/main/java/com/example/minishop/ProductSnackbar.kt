package com.example.minishop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.minishop.databinding.CustomSnackbarLayoutBinding
import com.google.android.material.snackbar.Snackbar

class ProductSnackbar(private val view: View) {


    interface OnClickButtonsCallBack {
        fun onMinusClick(snackbar: Snackbar)
        fun onPlusClick()
        fun observer(textView: TextView, button: Button)
        fun onDismissed()
    }

    fun showSnackbar(listener: OnClickButtonsCallBack, productName: String) {
        val snackView =
            LayoutInflater.from(view.context).inflate(R.layout.custom_snackbar_layout, null)
        val binding = CustomSnackbarLayoutBinding.bind(snackView)
        binding.plusButton.setIconResource(R.drawable.button_selector)
        binding.prodName.text = productName

        val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)
        (snackbar.view as ViewGroup).removeAllViews()
        (snackbar.view as ViewGroup).addView(snackView)
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                view.context,
                R.color.md_theme_light_secondary
            )
        )

        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                listener.onDismissed()
            }
        })

        binding.okButton.setOnClickListener {
            snackbar.dismiss()
        }

        binding.minusButton.setOnClickListener {
            listener.onMinusClick(snackbar)
            listener.observer(binding.count, binding.plusButton)
        }

        binding.plusButton.setOnClickListener {
            listener.onPlusClick()
            listener.observer(binding.count, binding.plusButton)
        }

        snackbar.show()
    }
}

