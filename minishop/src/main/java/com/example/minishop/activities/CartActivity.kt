package com.example.minishop.activities

import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_POSITIVE
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minishop.DeleteDialog
import com.example.minishop.IntentKeys
import com.example.minishop.R
import com.example.minishop.adapters.CartListAdapter
import com.example.minishop.adapters.OnCartItemCallBack
import com.example.minishop.databinding.CartActivityBinding
import com.example.minishop.models.CartItem
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

        supportFragmentManager.setFragmentResultListener(IntentKeys.RESPONSE, this) { _, result ->
            when (result.getInt(IntentKeys.RESPONSE)) {
                BUTTON_POSITIVE -> {
                    vm.deleteCartItem(result.getInt(IntentKeys.POSITION), adapter)
                }
            }
        }

        setUpViewModel()
        setUpAdapter()
        setUpRecyclerView()
    }


    private fun setUpViewModel() {
        vm = ViewModelProvider(this)[CartVM::class.java]
        if (vm.totalCount.value == -1) {
            vm.setTotalCount(intent.getIntExtra(IntentKeys.TOTAL_COUNT, 0))

            val cartItems =
                intent.getParcelableArrayListExtra<CartItem>(IntentKeys.CART_ITEMS)?.toMutableList()
            vm.setList(cartItems!!)

        }
        vm.cartInteractionListener = this

        vm.totalCount.observe(this) {
            val totalCountTemplate = getString(R.string.total_count_template)
            val newText = "$totalCountTemplate $it"
            binding.totalCount.text = newText

            val countCartsTemplate = getString(R.string.count_items)
            val newCount = "$countCartsTemplate ${vm.userGoods.size}"
            binding.numberCarts.text = newCount

        }
    }

    private fun setUpAdapter() {
        adapter = CartListAdapter(vm.userGoods, object : OnCartItemCallBack {

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
        returnIntent.putExtra(IntentKeys.TOTAL_COUNT, vm.totalCount.value)
        returnIntent.putParcelableArrayListExtra(IntentKeys.CART_ITEMS, ArrayList(vm.userGoods))
        setResult(RESULT_OK, returnIntent)
        super.onBackPressed()
    }

    override fun showDeleteConfirmationDialog(position: Int) {
        val dialog = DeleteDialog()
        dialog.listener = DialogInterface.OnClickListener { _, _ ->
            supportFragmentManager.setFragmentResult(
                IntentKeys.RESPONSE,
                bundleOf(IntentKeys.RESPONSE to BUTTON_POSITIVE, IntentKeys.POSITION to position)
            )
        }
        dialog.show(supportFragmentManager, "DELETE")
    }
}