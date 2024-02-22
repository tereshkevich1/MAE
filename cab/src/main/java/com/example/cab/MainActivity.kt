package com.example.cab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cab.constants.IntentKeys

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        finish()
        val intent = Intent(this@MainActivity,UserDataActivity::class.java)
        intent.putExtra(IntentKeys.USERNAME,"Терешкевич Серега")
        intent.putExtra(IntentKeys.PHONE,"")
        startActivity(intent)
    }
}