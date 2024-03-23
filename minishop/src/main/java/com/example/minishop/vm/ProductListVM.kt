package com.example.minishop.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minishop.data.Data
import com.example.minishop.models.CartItem

class ProductListVM : ViewModel() {

    private var _count = MutableLiveData(1)
    val count: LiveData<Int> get() = _count

    private var _totalCount = MutableLiveData(0)
    val totalCount: LiveData<Int> get() = _totalCount

    var userGoods = mutableListOf<CartItem>()

    fun addNewCardItem(position: Int) {
        count.value?.let { countValue ->
            val product = Data.products[position]
            val existingCardItem = userGoods.find { it.product == product }

            if (existingCardItem != null) {
                existingCardItem.let {
                    _totalCount.value = _totalCount.value?.plus(countValue)
                    it.count += countValue
                }
            } else if (count.value != 0) {
                userGoods.add(CartItem(product, countValue))
                _totalCount.value = _totalCount.value?.plus(countValue)
            }
        }
    }

    fun incCardItem() {
        _count.value = _count.value?.inc()
    }

    fun decCardItem(changeView: () -> Unit) {
        count.value?.let { countValue ->
            if (countValue > 1) {
                _count.value = _count.value?.dec()
            } else {
                _count.value = 0
                changeView()
            }
        }
    }

    fun normalizeToOne() {
        _count.value = 1
    }

    fun setTotalCount(totalCount: Int) {
        _totalCount.value = totalCount
    }

    fun setList(goods: MutableList<CartItem>) {
        userGoods = goods
    }
}