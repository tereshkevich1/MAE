package com.example.minishop.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minishop.CartListAdapter
import com.example.minishop.R
import com.example.minishop.data.Data
import com.example.minishop.databinding.CartActivityBinding

class CartActivity: AppCompatActivity() {

    private lateinit var binding: CartActivityBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.cart_activity)
        recyclerView = binding.cartRC
        adapter = CartListAdapter(Data.userGoods)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



    }
}