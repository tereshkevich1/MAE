package com.example.minishop.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minishop.data.Data
import com.example.minishop.models.CartItem
import com.google.android.material.snackbar.Snackbar

class ProductListVM : ViewModel() {

    private var _count = MutableLiveData(1)
    val count: LiveData<Int> get() = _count

    private var _totalCount = MutableLiveData(0)
    val totalCount: LiveData<Int> get() = _totalCount

    fun addNewCardItem(position: Int) {
        val product = Data.products[position]
        val existingCardItem = Data.userGoods.find { it.product == product }

        if (existingCardItem != null) {
            existingCardItem.let {
                _totalCount.value = _totalCount.value?.plus(count.value!!)
                it.count = it.count + count.value!!
            }
        } else if (count.value != 0) {
            Data.userGoods.add(CartItem(product, count.value!!))
            _totalCount.value = _totalCount.value?.plus(count.value!!)
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

    fun setTotalCount(totalCount: Int){
        _totalCount.value = totalCount
    }
}