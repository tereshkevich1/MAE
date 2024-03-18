package com.example.minishop.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minishop.data.Data
import com.example.minishop.models.CardItem
import com.google.android.material.snackbar.Snackbar

class ProductListVM : ViewModel() {

    private var _count = MutableLiveData(1)
    val count: LiveData<Int> get() = _count

    fun addNewCardItem(position: Int) {
        val product = Data.products[position]
        val existingCardItem = Data.userGoods.find { it.product == product }

        if (existingCardItem != null) {
            existingCardItem.let {
                it.count = it.count + count.value!!
            }
        } else if (count.value != 0) {
            Data.userGoods.add(CardItem(product, count.value!!))
        }

    }

    fun incCardItem() {
        _count.value = _count.value?.inc()
    }

    fun decCardItem(snackbar: Snackbar) {
        if (count.value!! > 1) {
            _count.value = _count.value?.dec()
        } else {
            _count.value = 0
            snackbar.dismiss()
        }
    }

    fun normalizeToOne() {
        _count.value = 1
    }
}