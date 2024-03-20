package com.example.minishop.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minishop.data.Data

class CartVM: ViewModel() {

    private var _totalCount = MutableLiveData(0)
    val totalCount get() = _totalCount

    fun incCardItem(position: Int) {
        Data.userGoods[position].count = Data.userGoods[position].count.inc()
        _totalCount.value = _totalCount.value?.inc()
    }

    fun decCardItem(position: Int) {
        Data.userGoods[position].count = Data.userGoods[position].count.dec()
        _totalCount.value = _totalCount.value?.dec()
    }

    fun setTotalCount(totalCount: Int){
        _totalCount.value = totalCount
    }
}