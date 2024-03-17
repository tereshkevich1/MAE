package com.example.minishop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.minishop.databinding.CustomSnackbarLayoutBinding
import com.example.minishop.models.Product
import com.google.android.material.snackbar.Snackbar

class ProductSnackbar( private val view: View) {

    fun showSnackbar(product: Product){
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
            snackbar.dismiss()
        }

        binding.minusButton.setOnClickListener {

        }

        binding.plusButton.setOnClickListener {

        }

        snackbar.show()
    }
}