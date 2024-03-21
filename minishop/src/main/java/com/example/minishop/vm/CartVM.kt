package com.example.minishop.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minishop.adapters.CartListAdapter
import com.example.minishop.data.Data

class CartVM : ViewModel() {

    private var _totalCount = MutableLiveData(0)
    val totalCount get() = _totalCount

    fun incCardItem(position: Int) {
        Data.userGoods[position].count++
        _totalCount.value = _totalCount.value?.inc()
    }

    fun decCardItem(position: Int, adapter: CartListAdapter) {
        if (Data.userGoods[position].count == 1) {
            adapter.notifyItemRemoved(position)
            Data.userGoods.removeAt(position)
            adapter.notifyItemRangeChanged(
                position,
                Data.userGoods.size
            )
            _totalCount.value = _totalCount.value?.dec()
        } else {
            Data.userGoods[position].count--
            _totalCount.value = _totalCount.value?.dec()
        }
    }

    fun setTotalCount(totalCount: Int) {
        _totalCount.value = totalCount
    }
}

