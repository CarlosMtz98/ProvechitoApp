package com.itesm.equipo3.provechito.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itesm.equipo3.provechito.databinding.ProductItemViewBinding
import com.itesm.equipo3.provechito.models.ProductCard

class ProductCardAdapter(val arrProducts: ArrayList<ProductCard>) : RecyclerView.Adapter<ProductCardAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ProductItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: ProductCard) {
            binding.tvProductName.text = cardItem.name
            binding.tvDescription.text = cardItem.description
            binding.tvDateAdded.text = cardItem.dateAdded
        }
    }
    var listener: ClickListener? = null
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
        val tarjeta = arrProducts[position]
        holder.set(tarjeta)

        holder.binding.root.setOnClickListener {
            listener?.recipeClicked(position)
            println("Hizo click ${position}")
        }
        holder.binding.btnOptions.setOnClickListener {
            listener?.recipeClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return arrProducts.size
    }
}