package com.example.minishop

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.minishop.models.CartItem
import com.example.minishop.models.Product
import com.example.minishop.vm.CartVM
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    private lateinit var viewModel: CartVM
    private lateinit var list: MutableList<CartItem>

    @Before
    fun init(){
        viewModel  =  CartVM()
        list = mutableListOf(CartItem(Product("x"),2),CartItem(Product("x"),2))
        viewModel.setList(list)
    }

    @Test
    fun decCardItem_DecreaseCount() {

        viewModel.decCardItem(0)
        assertEquals(1, viewModel.userGoods[0].count)
    }
}