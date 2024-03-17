package com.example.minishop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.minishop.databinding.FooterProductListBinding
import com.example.minishop.databinding.HeaderProductListBinding
import com.example.minishop.databinding.ProductListItemBinding
import com.example.minishop.models.Product

interface OnItemClickCallBack {
    fun onAddButton(position: Int)
}

class ProductListAdapter(
    private val productList: MutableList<Product>,
    private val listener: OnItemClickCallBack
) :
    RecyclerView.Adapter<ViewHolder>() {

    private lateinit var headerBinding: HeaderProductListBinding
    private lateinit var footerBinding: FooterProductListBinding
    private lateinit var productBinding: ProductListItemBinding


    class ProductViewHolder(itemView: View) : ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        val addButton: Button = itemView.findViewById(R.id.addButton)
    }

    class FooterProductViewHolder(itemView: View) : ViewHolder(itemView) {
    }

    class HeaderProductViewHolder(itemView: View) : ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ViewTypes.Normal -> {
                productBinding = ProductListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ProductViewHolder(productBinding.root)
            }

            ViewTypes.Header -> {
                headerBinding = HeaderProductListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderProductViewHolder(headerBinding.root)
            }

            else -> {
                footerBinding = FooterProductListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FooterProductViewHolder(footerBinding.root)
            }
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> {
                holder.idTextView.text = position.toString()
                if (position > 0)
                    holder.nameTextView.text = productList[position.dec()].name
                holder.addButton.setOnClickListener {
                    listener.onAddButton(position.dec())
                    //snackbar.showSnackbar(productList[position.dec()])
                }
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