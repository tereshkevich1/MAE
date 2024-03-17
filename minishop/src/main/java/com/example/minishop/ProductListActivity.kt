package com.example.minishop

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.product_list_activity)
        vm = ViewModelProvider(this)[ProductListVM::class.java]
        snackbar = ProductSnackbar(binding.root)

        Log.d("ssss", "create")
        setUpRecyclerViewAdapter()
        setUpRecyclerView()

    }

    private fun setUpRecyclerViewAdapter() {

        val onItemClickCallBack = object : OnItemClickCallBack {
            override fun onAddButton(position: Int) {

                vm.addNewCardItem(position)

                val onClickSnackbarButtonsCallBack =
                    object : ProductSnackbar.OnClickButtonsCallBack {
                        override fun onMinusClick(snackbar: Snackbar) {
                            vm.decCardItem(snackbar)
                        }

                        override fun onPlusClick() {
                            vm.incCardItem()
                        }

                        override fun observer(textView: TextView) {
                            vm.newCardItem.observe(this@ProductListActivity) {
                                textView.text = vm.newCardItem.value?.count.toString()
                            }
                        }

                    }
                snackbar.showSnackbar(onClickSnackbarButtonsCallBack)
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