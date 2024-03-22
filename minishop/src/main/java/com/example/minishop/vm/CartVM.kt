package com.example.minishop.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minishop.adapters.CartListAdapter
import com.example.minishop.models.CartItem

interface CartInteractionListener {
    fun showDeleteConfirmationDialog(position: Int)
}

class CartVM : ViewModel() {

    private var _totalCount = MutableLiveData(-1)
    val totalCount get() = _totalCount

    var cartInteractionListener: CartInteractionListener? = null
    var userGoods = mutableListOf<CartItem>()


    fun incCardItem(position: Int) {
        userGoods[position].count++
        _totalCount.value = _totalCount.value?.inc()
    }

    fun decCardItem(position: Int) {
        if (userGoods[position].count > 1) {

            userGoods[position].count--
            _totalCount.value = _totalCount.value?.dec()

        } else {
            cartInteractionListener?.showDeleteConfirmationDialog(position)
        }
    }

    fun deleteCartItem(position: Int, adapter: CartListAdapter) {
        adapter.notifyItemRemoved(position)
        userGoods.removeAt(position)
        adapter.notifyItemRangeChanged(
            position,
            userGoods.size
        )
        _totalCount.value = _totalCount.value?.dec()
    }

    fun setTotalCount(totalCount: Int) {
        _totalCount.value = totalCount
    }

    fun setList(goods: MutableList<CartItem>) {
        userGoods = goods
    }
}

