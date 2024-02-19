package com.example.cab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        finish()
        val intent = Intent(this@MainActivity,UserDataActivity::class.java)
        intent.putExtra("USERNAME","Терешкевич Серега")
        intent.putExtra("PHONE","")
        startActivity(intent)
    }
}