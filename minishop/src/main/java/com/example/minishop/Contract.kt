package com.example.minishop

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class Contract : ActivityResultContract<Intent, Int?>() {

    override fun createIntent(context: Context, input: Intent): Intent {
        return input
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getIntExtra("totalCount", 0)
    }
}