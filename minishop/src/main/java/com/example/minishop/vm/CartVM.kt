package com.example.minishop.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minishop.adapters.CartListAdapter
import com.example.minishop.data.Data

interface CartInteractionListener {
    fun showDeleteConfirmationDialog(position: Int)
}

class CartVM : ViewModel() {

    private var _totalCount = MutableLiveData(0)
    val totalCount get() = _totalCount
    var cartInteractionListener: CartInteractionListener? = null


    fun incCardItem(position: Int) {
        Log.d("incCardItem","inc")
        Data.userGoods[position].count++
        _totalCount.value = _totalCount.value?.inc()
    }

    fun decCardItem(position: Int) {
        if (Data.userGoods[position].count == 1) {

            cartInteractionListener?.showDeleteConfirmationDialog(position)

        } else {
            Data.userGoods[position].count--
            _totalCount.value = _totalCount.value?.dec()
        }
    }

    fun deleteCartItem(position: Int, adapter: CartListAdapter) {
        adapter.notifyItemRemoved(position)
        Data.userGoods.removeAt(position)
        adapter.notifyItemRangeChanged(
            position,
            Data.userGoods.size
        )
        _totalCount.value = _totalCount.value?.dec()
    }

    fun setTotalCount(totalCount: Int) {
        _totalCount.value = totalCount
    }
}

