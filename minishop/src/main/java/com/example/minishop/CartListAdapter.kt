package com.example.minishop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minishop.databinding.CartItemBinding
import com.example.minishop.models.CardItem

class CartListAdapter(private val cartList: MutableList<CardItem>): RecyclerView.Adapter<CartListAdapter.CartViewHolder>() {

    private lateinit var cartBinding: CartItemBinding

    class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        cartBinding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(cartBinding.root)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

    }
}