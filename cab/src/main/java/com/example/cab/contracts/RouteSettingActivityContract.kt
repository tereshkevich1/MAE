package com.example.cab.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.cab.RouteSettingActivity
import com.example.cab.constants.IntentKeys

class RouteSettingActivityContract : ActivityResultContract<String, Int?>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, RouteSettingActivity::class.java).putExtra(IntentKeys.INPUT_KEY, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getIntExtra(IntentKeys.RESULT_KEY, 0)
    }
}