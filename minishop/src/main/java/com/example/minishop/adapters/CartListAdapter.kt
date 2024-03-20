package com.example.minishop.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minishop.R
import com.example.minishop.databinding.CartItemBinding
import com.example.minishop.models.CardItem


interface OnCartItemCallBack {
    fun onPlusClick(position: Int)
    fun onMinusClick(position: Int)
}

class CartListAdapter(
    private val cartList: MutableList<CardItem>,
    private val listener: OnCartItemCallBack
) : RecyclerView.Adapter<CartListAdapter.CartViewHolder>() {

    private lateinit var cartBinding: CartItemBinding

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameProductTextView)
        val countTextView: TextView = itemView.findViewById(R.id.countProductTextView)
        val plusButton: Button = itemView.findViewById(R.id.plusButton)
        val minusButton: Button = itemView.findViewById(R.id.minusButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        cartBinding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(cartBinding.root)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.countTextView.text = cartList[position].count.toString()
        holder.nameTextView.text = cartList[position].product.name
        holder.plusButton.setOnClickListener {
            listener.onPlusClick(position)
            this.notifyItemChanged(position, UPDATE_COUNTER_PAYLOAD)
        }
        holder.minusButton.setOnClickListener {
            listener.onMinusClick(position)
            this.notifyItemChanged(position, UPDATE_COUNTER_PAYLOAD)
        }
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        Log.d("payLoads","w metode")
        if (payloads.isNotEmpty()) {
            for (payload in payloads) {
                if (payload == UPDATE_COUNTER_PAYLOAD) {
                    Log.d("payLoads","set")
                    holder.countTextView.text = cartList[position].count.toString()
                }
            }
        } else {
            Log.d("payLoads","w metode super")
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    companion object {
        const val UPDATE_COUNTER_PAYLOAD = "update_counter"
    }
}