package com.example.minishop.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minishop.Contract
import com.example.minishop.ProductSnackbar
import com.example.minishop.R
import com.example.minishop.adapters.OnItemClickCallBack
import com.example.minishop.adapters.ProductListAdapter
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
    private lateinit var countMessage: String


    private val activityLaunch = registerForActivityResult(Contract()) {
        when (it) {
            null -> {
                vm.setTotalCount(0)
            }
            else -> {
                vm.setTotalCount(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countMessage = this@ProductListActivity.getString(R.string.count_items).dropLast(1)
        binding = DataBindingUtil.setContentView(this, R.layout.product_list_activity)
        vm = ViewModelProvider(this)[ProductListVM::class.java]
        snackbar = ProductSnackbar(binding.root)

        setUpRecyclerViewAdapter()
        setUpRecyclerView()

        vm.totalCount.observe(this) { productCount ->
            adapter.updateFooterCount(
                countMessage + productCount.toString()
            )
        }
    }

    private fun setUpRecyclerViewAdapter() {

        val onItemClickCallBack = object : OnItemClickCallBack {
            override fun onAddButton(position: Int) {

                vm.normalizeToOne()

                val onClickSnackbarButtonsCallBack =
                    object : ProductSnackbar.OnClickButtonsCallBack {
                        override fun onMinusClick(snackbar: Snackbar) {
                            vm.decCardItem(snackbar)
                        }

                        override fun onPlusClick() {
                            vm.incCardItem()
                        }

                        override fun observer(textView: TextView) {
                            vm.count.observe(this@ProductListActivity) {
                                textView.text = vm.count.value?.toString()
                            }
                        }

                        override fun onDismissed() {
                            vm.addNewCardItem(position)
                        }
                    }
                snackbar.showSnackbar(onClickSnackbarButtonsCallBack)
            }

            override fun onShowButton() {
                val intent = Intent(this@ProductListActivity, CartActivity::class.java)
                intent.putExtra("totalCount", vm.totalCount.value)
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

}