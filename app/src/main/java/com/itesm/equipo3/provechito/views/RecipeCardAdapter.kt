package com.itesm.equipo3.provechito.views

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.itesm.equipo3.provechito.databinding.RecipeCardViewBinding
import com.itesm.equipo3.provechito.models.RecipeCard

class RecipeCardAdapter (val arrRecipeCard: ArrayList<RecipeCard>) : RecyclerView.Adapter<RecipeCardAdapter.ViewHolder>() {
    var listener: ClickListener? = null

    inner class ViewHolder(val binding: RecipeCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: RecipeCard) {
            binding.tvRecipeNameCard.text = cardItem.name
            binding.tvRecipeCardCategory.text = cardItem.category
            binding.tvRecipeDurationCard.text = cardItem.duration
            AndroidNetworking.get(cardItem.imgUri)
                .build()
                .getAsBitmap(object: BitmapRequestListener {
                    override fun onResponse(response: Bitmap?) {
                        binding.recipeCardImage.setImageBitmap(response)
                    }
                    override fun onError(anError: ANError?) {
                        println(anError?.message.toString())
                    }
                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecipeCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrRecipeCard.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarjeta = arrRecipeCard[position]

        holder.set(tarjeta)

        holder.binding.recipeCardImage.setOnClickListener {
            if (listener != null) {
                println("Hellow ")
                listener?.clicked(position)
            }
        }
    }


}
