package com.example.minishop

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minishop.models.Product

class ProductListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_list_activity)

        Log.d("onCreate0","1")
        val recyclerView: RecyclerView = findViewById(R.id.productRC)
        Log.d("onCreate0","1")

        val adapter = ProductListAdapter(
            mutableListOf(
                Product("a"),
                Product("b"),
                Product("c"),
                Product("d"),
                Product("e"),
                Product("h"),
                Product("f"),
                Product("uh"),
                Product("uh"),
                Product("hh"),
                Product("th"),
                Product("eh"),
                Product("sud")
            )
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}