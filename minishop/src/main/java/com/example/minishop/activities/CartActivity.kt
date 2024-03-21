package com.example.minishop.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minishop.DeleteDialog
import com.example.minishop.R
import com.example.minishop.adapters.CartListAdapter
import com.example.minishop.adapters.OnCartItemCallBack
import com.example.minishop.data.Data
import com.example.minishop.databinding.CartActivityBinding
import com.example.minishop.vm.CartInteractionListener
import com.example.minishop.vm.CartVM

class CartActivity : AppCompatActivity(), CartInteractionListener {

    private lateinit var binding: CartActivityBinding
    private lateinit var vm: CartVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.cart_activity)

        setUpViewModel()
        setUpAdapter()
        setUpRecyclerView()
    }

    private fun setUpViewModel() {
        vm = ViewModelProvider(this)[CartVM::class.java]
        vm.setTotalCount(intent.getIntExtra("totalCount", 0))
        vm.cartInteractionListener = this

        vm.totalCount.observe(this) {
            val totalCountTemplate = getString(R.string.total_count_template)
            val newText = "$totalCountTemplate $it"
            binding.totalCount.text = newText

        }
    }

    private fun setUpAdapter() {
        adapter = CartListAdapter(Data.userGoods, object : OnCartItemCallBack {

            override fun onPlusClick(position: Int) {
                vm.incCardItem(position)
            }

            override fun onMinusClick(position: Int) {
                vm.decCardItem(position)
            }

        })
    }

    private fun setUpRecyclerView() {
        recyclerView = binding.cartRC
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra("totalCount", vm.totalCount.value)
        setResult(RESULT_OK, returnIntent)
        super.onBackPressed()
    }

    override fun showDeleteConfirmationDialog(position: Int) {
        DeleteDialog { vm.deleteCartItem(position, adapter) }.show(
            this.supportFragmentManager,
            "DELETE_CART"
        )
    }
}