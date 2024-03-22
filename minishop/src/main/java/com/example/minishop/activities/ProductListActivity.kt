package com.example.minishop.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minishop.Contract
import com.example.minishop.IntentKeys
import com.example.minishop.ProductSnackbar
import com.example.minishop.R
import com.example.minishop.adapters.OnItemClickCallBack
import com.example.minishop.adapters.ProductListAdapter
import com.example.minishop.adapters.ProductListAdapter.Companion.UPDATE_COUNTER
import com.example.minishop.data.Data
import com.example.minishop.databinding.ProductListActivityBinding
import com.example.minishop.vm.ProductListVM
import com.google.android.material.snackbar.Snackbar

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ProductListActivityBinding
    private lateinit var vm: ProductListVM
    private lateinit var adapter: ProductListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var snackbar: ProductSnackbar

    private val activityLaunch = registerForActivityResult(Contract()) {
        when (it) {
            null -> {
                vm.setTotalCount(0)
                vm.setList(mutableListOf())
            }

            else -> {
                vm.setTotalCount(it.totalCount)
                vm.setList(it.userGoods)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.product_list_activity)
        snackbar = ProductSnackbar(binding.root)

        setUpViewModel()
        setUpRecyclerViewAdapter()
        setUpRecyclerView()

    }

    private fun setUpRecyclerViewAdapter() {

        val onItemClickCallBack = object : OnItemClickCallBack {
            override fun onAddButton(position: Int) {

                vm.normalizeToOne()

                val onClickSnackbarButtonsCallBack =
                    object : ProductSnackbar.OnClickButtonsCallBack {
                        override fun onMinusClick(snackbar: Snackbar) {
                            vm.decCardItem { snackbar.dismiss() }
                        }

                        override fun onPlusClick() {
                            vm.incCardItem()
                        }

                        override fun observer(textView: TextView, button: Button) {
                            vm.count.observe(this@ProductListActivity) {
                                textView.text = vm.count.value?.toString()
                                button.isEnabled = it != 9
                            }
                        }

                        override fun onDismissed() {
                            vm.addNewCardItem(position)
                        }
                    }
                snackbar.showSnackbar(onClickSnackbarButtonsCallBack, Data.products[position].name)
            }

            override fun onShowButton() {
                val intent = Intent(this@ProductListActivity, CartActivity::class.java)
                intent.putExtra(IntentKeys.TOTAL_COUNT, vm.totalCount.value)
                intent.putParcelableArrayListExtra(IntentKeys.CART_ITEMS, ArrayList(vm.userGoods))
                activityLaunch.launch(intent)
            }
        }

        adapter = ProductListAdapter(Data.products, onItemClickCallBack)

    }

    private fun setUpRecyclerView() {
        recyclerView = binding.productRC
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setUpViewModel() {
        vm = ViewModelProvider(this)[ProductListVM::class.java]

        vm.totalCount.observe(this@ProductListActivity) {
            adapter.totalCount = it
            adapter.notifyItemChanged(adapter.itemCount - 1, UPDATE_COUNTER)

        }
    }
}