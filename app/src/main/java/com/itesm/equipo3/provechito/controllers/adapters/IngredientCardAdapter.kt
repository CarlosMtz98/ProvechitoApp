package com.itesm.equipo3.provechito.controllers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.IngredientCardViewBinding
import com.itesm.equipo3.provechito.models.IngredientCard

class IngredientCardAdapter( val arrIngredients: ArrayList<IngredientCard>): RecyclerView.Adapter<IngredientCardAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: IngredientCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: IngredientCard) {
            binding.tvIngredientName.text = cardItem.name
        }
    }

    var listener: ClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                IngredientCardViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarjeta = arrIngredients[position]
        holder.set(tarjeta)

        holder.binding.root.setOnClickListener {
            listener?.recipeClicked(position)
            println("Hizo click ${position}")
        }
    }

    override fun getItemCount(): Int {
        return arrIngredients.size
    }
}