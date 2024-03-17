package com.example.minishop

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.minishop.data.Data
import com.example.minishop.databinding.CustomSnackbarLayoutBinding
import com.google.android.material.snackbar.Snackbar

class ProductSnackbar( private val view: View) {


    interface OnClickButtonsCallBack{
        fun onMinusClick(snackbar: Snackbar)
        fun onPlusClick()
        fun observer(textView: TextView)
    }

    fun showSnackbar(listener:OnClickButtonsCallBack){
        val snackView = LayoutInflater.from(view.context).inflate(R.layout.custom_snackbar_layout,null)
        val binding = CustomSnackbarLayoutBinding.bind(snackView)

        val snackbar = Snackbar.make(view,"",Snackbar.LENGTH_INDEFINITE)
        (snackbar.view as ViewGroup).removeAllViews()
        (snackbar.view as ViewGroup).addView(snackView)
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                view.context,
                R.color.md_theme_light_secondary
            )
        )

        binding.okButton.setOnClickListener {
            Log.d("GoodsList", Data.userGoods.toString())
            snackbar.dismiss()
        }

        binding.minusButton.setOnClickListener {
            listener.onMinusClick(snackbar)
            listener.observer(binding.count)
        }

        binding.plusButton.setOnClickListener {
            listener.onPlusClick()
            listener.observer(binding.count)
        }

        snackbar.show()
    }
}

