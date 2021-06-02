package com.itesm.equipo3.provechito.controllers.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.controllers.listeners.LikeClickListener
import com.itesm.equipo3.provechito.databinding.RecipeCardViewFullBinding
import com.itesm.equipo3.provechito.models.RecipeCard
import com.like.LikeButton

import com.like.OnLikeListener




class RecipeCardFullAdapter(private val arrRecipeCard: ArrayList<RecipeCard>) : RecyclerView.Adapter<RecipeCardFullAdapter.ViewHolder>() {
    var listener: ClickListener? = null
    var likeListener: LikeClickListener? = null

    inner class ViewHolder(val binding: RecipeCardViewFullBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: RecipeCard) {
            binding.tvRecipeNameCard.text = cardItem.name
            binding.tvRecipeCardCategory.text = cardItem.category
            binding.tvRecipeDurationCard.text = cardItem.duration
            binding.tvPrecio.text = cardItem.precio
            binding.tvDificultad.text = cardItem.dificultad
            if (cardItem.liked) {
                binding.starButton.setLiked(true)
            }
            binding.starButton.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton) {
                    likeListener?.likeOnClick(cardItem.id)
                }
                override fun unLiked(likeButton: LikeButton) {
                    likeListener?.unlikeOnClick(cardItem.id)
                }
            })
            AndroidNetworking.get(cardItem.imgUri)
                .build()
                .getAsBitmap(object : BitmapRequestListener {
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
            RecipeCardViewFullBinding.inflate(
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
                listener!!.recipeClicked(tarjeta)
            }
        }
    }


}
