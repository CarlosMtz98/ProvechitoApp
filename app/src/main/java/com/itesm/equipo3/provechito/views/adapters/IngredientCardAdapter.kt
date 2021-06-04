package com.itesm.equipo3.provechito.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.IngredientCardViewBinding
import com.itesm.equipo3.provechito.models.IngredientCard
import com.itesm.equipo3.provechito.pojo.Products.Product
import com.itesm.equipo3.provechito.views.listeners.RecipeDetailsClickListener

class IngredientCardAdapter( val arrIngredients: ArrayList<IngredientCard>): RecyclerView.Adapter<IngredientCardAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: IngredientCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: IngredientCard) {
            binding.tvIngredientName.text = cardItem.name
        }
    }

    var listener: ClickListener? = null
    var ingredientsListener: RecipeDetailsClickListener? = null
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
        val ingredient = arrIngredients[position]
        holder.set(ingredient)
        holder.binding.imageButton4.setOnClickListener {
            Log.i("ItemCardAdded", "Ingredient: ${ingredient.name}")
            if (ingredientsListener != null) {
                ingredientsListener!!.clickRecipeIngredient(Product(ingredient.name, ""))
            }
        }
    }

    override fun getItemCount(): Int {
        return arrIngredients.size
    }
}