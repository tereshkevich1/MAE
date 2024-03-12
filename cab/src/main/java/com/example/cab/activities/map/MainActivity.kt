package com.example.cab.activities.map

import android.content.Intent
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



        var distance: Float? = null
        val textV1 = findViewById<TextView>(R.id.textView)
        val textV2 = findViewById<TextView>(R.id.textView3)
        val button: Button = findViewById(R.id.button)
        val textV3: TextView = findViewById(R.id.textView4)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, UserDataActivity::class.java)
            intent.putExtra(IntentKeys.USERNAME, "Терешкевич Сергей")
            intent.putExtra(IntentKeys.PHONE, "")
            startActivity(intent)
            finish()
        }
        distance = intent.getFloatExtra(IntentKeys.DISTANCE,0f)

        textV3.text = if (distance<1000) "$distance м"  else "${distance/1000} км"
    }
}