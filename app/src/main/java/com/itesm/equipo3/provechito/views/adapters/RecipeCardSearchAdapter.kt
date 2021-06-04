package com.itesm.equipo3.provechito.views.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.itesm.equipo3.provechito.databinding.RecipeCardSearchBinding
import com.itesm.equipo3.provechito.databinding.RecipeCardViewFullBinding
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.views.listeners.LikeClickListener
import com.like.LikeButton
import com.like.OnLikeListener

// Autor: Diego PB
class RecipeCardSearchAdapter(private val recipeList: ArrayList<Recipe>) : RecyclerView.Adapter<RecipeCardSearchAdapter.ViewHolder>() {
    var listener: ClickListener? = null
    var likeListener: LikeClickListener? = null

    inner class ViewHolder(val binding: RecipeCardSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: Recipe) {
            binding.etRecipeName.text = cardItem.name
            binding.etRecipeCategoria.text = cardItem.categories?.firstOrNull()?.name ?: "Sin categoría"

            AndroidNetworking.get(cardItem.thumbnailUrl)
                .build()
                .getAsBitmap(object : BitmapRequestListener {
                    override fun onResponse(response: Bitmap?) {
                        binding.imgRecipeCard.setImageBitmap(response)
                    }

                    override fun onError(anError: ANError?) {
                        println(anError?.message.toString())
                    }
                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecipeCardSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarjeta = recipeList[position]

        holder.set(tarjeta)

        holder.binding.cardButton.setOnClickListener {
            if (listener != null) {
                listener!!.recipeClicked(tarjeta)
            }
        }
    }
}