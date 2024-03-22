package com.example.minishop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.example.minishop.models.CartItem
import com.example.minishop.models.ResultData

class Contract : ActivityResultContract<Intent, ResultData?>() {

    override fun createIntent(context: Context, input: Intent): Intent {
        return input
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ResultData? {
        return if (resultCode != Activity.RESULT_OK) null
        else {
            Log.d("parseResult", "pla4u")
            val cartItems =
                intent?.getParcelableArrayListExtra<CartItem>("cartItemList")?.toMutableList()
                    ?: mutableListOf()
            Log.d("parseResult", "pla4u")
            val totalCount = intent?.getIntExtra("totalCount", 0) ?: 0
            Log.d("parseResult", "pla4u")

            return ResultData(cartItems, totalCount)
        }
    }
}