package com.example.minishop

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.minishop.models.CartItem
import com.example.minishop.models.Product
import com.example.minishop.vm.CartVM
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val vm = CartVM()
        val list = mutableListOf(CartItem(Product("x"),2),CartItem(Product("x"),2))
        assertEquals(vm.decCardItem(position = 1), appContext.packageName)
    }
}