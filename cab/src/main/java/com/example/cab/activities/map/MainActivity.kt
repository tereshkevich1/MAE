package com.example.cab.activities.map

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cab.R
import com.example.cab.activities.map.constants.IntentKeys

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var marker: Location? = null
        var userLoc: Location? = null
        val textV1 = findViewById<TextView>(R.id.textView)
        val textV2 = findViewById<TextView>(R.id.textView3)
        val button: Button = findViewById(R.id.button)
        val textV3: TextView = findViewById(R.id.textView4)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, UserDataActivity::class.java)
            intent.putExtra(IntentKeys.USERNAME, "Терешкевич Серега")
            intent.putExtra(IntentKeys.PHONE, "")
            startActivity(intent)
            finish()
        }
        userLoc = intent.getParcelableExtra("user")
        marker = intent.getParcelableExtra("marker")
        textV1.text = marker.toString() + " marker"
        textV2.text = userLoc.toString() + " user"
        textV3.text = userLoc?.let { marker?.distanceTo(it).toString() }
    }
}