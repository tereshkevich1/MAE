package com.example.minishop.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minishop.R
import com.example.minishop.adapters.CartListAdapter
import com.example.minishop.adapters.OnCartItemCallBack
import com.example.minishop.data.Data
import com.example.minishop.databinding.CartActivityBinding
import com.example.minishop.vm.CartVM

class CartActivity : AppCompatActivity() {

    private lateinit var binding: CartActivityBinding
    private lateinit var vm: CartVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.cart_activity)
        recyclerView = binding.cartRC

        vm = ViewModelProvider(this)[CartVM::class.java]

        vm.setTotalCount(intent.getIntExtra("totalCount", 0))

        vm.totalCount.observe(this){
            binding.totalCount.text = it.toString()
        }


        adapter = CartListAdapter(Data.userGoods, object : OnCartItemCallBack {

            override fun onPlusClick(position: Int) {
                vm.incCardItem(position)
            }

            override fun onMinusClick(position: Int) {
                vm.decCardItem(position)
            }

        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }


    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("qwe","2act - ${vm.totalCount}")
        val returnIntent = Intent()
        returnIntent.putExtra("totalCount", vm.totalCount.value)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}