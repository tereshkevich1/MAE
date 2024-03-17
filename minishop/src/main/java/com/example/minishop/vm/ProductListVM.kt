package com.example.minishop.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minishop.data.Data
import com.example.minishop.models.CardItem
import com.google.android.material.snackbar.Snackbar

class ProductListVM : ViewModel() {

    private var _newCardItem = MutableLiveData<CardItem>()
    val newCardItem: LiveData<CardItem> get() = _newCardItem

    fun addNewCardItem(position: Int) {
        val product = Data.products[position]
        _newCardItem.value = CardItem(product, 1)
        Data.userGoods.add(_newCardItem.value!!)
    }

    fun incCardItem() {
        _newCardItem.value?.count = _newCardItem.value?.count!!.inc()
    }

    fun decCardItem(snackbar: Snackbar) {
        if (newCardItem.value?.count!! > 1) {
            _newCardItem.value?.count = _newCardItem.value?.count!!.dec()
        } else {
            Data.userGoods.remove(newCardItem.value)
            snackbar.dismiss()
        }
    }
}