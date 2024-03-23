package com.example.minishop.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(val product: Product, var count: Int): Parcelable