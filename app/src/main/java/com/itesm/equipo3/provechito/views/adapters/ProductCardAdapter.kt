package com.itesm.equipo3.provechito.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itesm.equipo3.provechito.databinding.ProductItemViewBinding
import com.itesm.equipo3.provechito.pojo.Products.Product
import com.itesm.equipo3.provechito.views.listeners.ShopListener
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ProductCardAdapter(private val productList: ArrayList<Product>) : RecyclerView.Adapter<ProductCardAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ProductItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val localeSpanish = Locale("es", "ES")
        private val dateFormat = SimpleDateFormat("EEEE d 'de' MMMM 'del' yyyy", localeSpanish)
        fun set(cardItem: Product) {
            binding.tvProductName.text = cardItem.name
            binding.tvDescription.text = cardItem.description
            binding.tvDateAdded.text =  dateFormat.format(cardItem.updatedDate)
        }
    }
    var listener: ShopListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ProductItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.set(product)
        holder.binding.btnOptions.setOnClickListener {
            if (listener != null) {
                listener?.ingredientClicked(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}