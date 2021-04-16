package com.itesm.equipo3.provechito.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itesm.equipo3.provechito.databinding.IngredientItemViewBinding
import com.itesm.equipo3.provechito.models.Ingredient

class IngredientCardAdapter(val arrIngredient: ArrayList<Ingredient>) : RecyclerView.Adapter<IngredientCardAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: IngredientItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: Ingredient) {
            binding.tvIngredientName.text = cardItem.name
            binding.tvDescription.text = cardItem.description
            binding.tvDateAdded.text = cardItem.dateAdded
        }
    }
    var listener: ClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            IngredientItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarjeta = arrIngredient[position]
        holder.set(tarjeta)

        holder.binding.root.setOnClickListener {
            listener?.clicked(position)
            println("Hizo click ${position}")
        }
    }

    override fun getItemCount(): Int {
        return arrIngredient.size
    }
}