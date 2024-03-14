package com.example.minishop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.minishop.models.Product

class ProductListAdapter(private val productList: MutableList<Product>) :
    RecyclerView.Adapter<ViewHolder>() {

    class ProductViewHolder(itemView: View) : ViewHolder(itemView) {
    }

    class FooterProductViewHolder(itemView: View) : ViewHolder(itemView) {
    }

    class HeaderProductViewHolder(itemView: View) : ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewTypes.Normal -> ProductViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_list_item, parent, false)
            )

            ViewTypes.Header -> HeaderProductViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_product_list, parent, false)
            )

            else ->
                FooterProductViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.footer_product_list, parent, false)
                )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> {

            }

            is HeaderProductViewHolder -> {

            }

            is FooterProductViewHolder -> {

            }
        }
    }

    override fun getItemCount(): Int {
        return productList.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ViewTypes.Header
            productList.size + 1 -> ViewTypes.Footer
            else -> ViewTypes.Normal
        }
    }

}