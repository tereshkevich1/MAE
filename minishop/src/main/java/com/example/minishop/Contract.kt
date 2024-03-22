package com.example.minishop

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.minishop.models.CartItem
import com.example.minishop.models.ResultData

@Suppress("DEPRECATION")
class Contract : ActivityResultContract<Intent, ResultData?>() {

    override fun createIntent(context: Context, input: Intent): Intent {
        return input
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ResultData? {
        return if (resultCode != Activity.RESULT_OK) null
        else {
            val cartItems =
                intent?.getParcelableArrayListExtra<CartItem>(IntentKeys.CART_ITEMS)?.toMutableList()
                    ?: mutableListOf()
            val totalCount = intent?.getIntExtra(IntentKeys.TOTAL_COUNT, 0) ?: 0
            return ResultData(cartItems, totalCount)
        }
    }
}