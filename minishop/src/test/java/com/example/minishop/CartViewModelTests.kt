package com.example.minishop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.DataBindingUtil
import com.example.minishop.adapters.CartListAdapter
import com.example.minishop.adapters.OnCartItemCallBack
import com.example.minishop.databinding.ProductListActivityBinding
import com.example.minishop.models.CartItem
import com.example.minishop.models.Product
import com.example.minishop.vm.CartInteractionListener
import com.example.minishop.vm.CartVM
import com.example.minishop.vm.ProductListVM
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CartViewModelTests {
    @get:Rule
   val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var adapter: CartListAdapter
    private lateinit var productListViewModel: ProductListVM
    private lateinit var cartViewModel: CartVM
    @Before
    fun init() {
        productListViewModel = ProductListVM()
        cartViewModel = CartVM()
        productListViewModel.addNewCardItem(1)
        cartViewModel.setList(mutableListOf(CartItem(Product("text"), 1)))

        adapter = CartListAdapter(cartViewModel.userGoods, object : OnCartItemCallBack {

            override fun onPlusClick(position: Int) {
                cartViewModel.incCardItem(position)
            }

            override fun onMinusClick(position: Int) {
                cartViewModel.decCardItem(position)
            }

        })
    }

    @Test
    fun addNewItemTest(){
        assertEquals(1, productListViewModel.totalCount.value)
    }

    @Test
    fun addTwoItemsTest(){
        productListViewModel.addNewCardItem(2)
        assertEquals(2, productListViewModel.totalCount.value)
    }

    @Test
    fun incItemInListTest(){
        productListViewModel.addNewCardItem(1)
        productListViewModel.incCardItem()
        assertEquals(2, productListViewModel.count.value)
    }

    @Test
    fun addThreeItemsTest(){
        productListViewModel.incCardItem()
        productListViewModel.addNewCardItem(2)
        assertEquals(3, productListViewModel.totalCount.value)
    }

    @Test
    fun incItemInCartTest(){
        cartViewModel.setList(productListViewModel.userGoods)
        productListViewModel.totalCount.value?.let { cartViewModel.setTotalCount(it) }
        cartViewModel.incCardItem(0)
        assertEquals(2, cartViewModel.totalCount.value)
    }

    @Test
    fun decItemInCartTest(){
        cartViewModel.setList(productListViewModel.userGoods)
        productListViewModel.totalCount.value?.let { cartViewModel.setTotalCount(it) }
        cartViewModel.incCardItem(0)
        cartViewModel.decCardItem(0)
        assertEquals(1, cartViewModel.totalCount.value)
    }


    @Test
    fun decItemInListTest(){
        productListViewModel.addNewCardItem(1)
        productListViewModel.incCardItem()
        productListViewModel.decCardItem({ empty() })
        assertEquals(1, productListViewModel.count.value)
    }

    @Test
    fun decToZeroItemInListTest(){

        productListViewModel.decCardItem({ empty() })
        assertEquals(0, productListViewModel.count.value)
    }

    private fun empty() {
    }

}