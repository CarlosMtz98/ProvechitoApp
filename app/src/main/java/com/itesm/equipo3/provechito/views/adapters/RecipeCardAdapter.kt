package com.itesm.equipo3.provechito.views.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.views.listeners.LikeClickListener
import com.itesm.equipo3.provechito.databinding.RecipeCardViewBinding
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.like.LikeButton

import com.like.OnLikeListener

class RecipeCardAdapter(private val arrRecipeCard: ArrayList<Recipe>) : RecyclerView.Adapter<RecipeCardAdapter.ViewHolder>() {
    var listener: ClickListener? = null
    var likeListener: LikeClickListener? = null

    inner class ViewHolder(val binding: RecipeCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: Recipe) {
            binding.tvRecipeNameCard.text = cardItem.name
            binding.tvRecipeCardCategory.text = cardItem.categories?.firstOrNull()?.name ?: "Sin categoría"
            binding.tvRecipeDurationCard.text = "${cardItem.duration} mins"

            if (cardItem.hasUserLike) { binding.starButton.isLiked = true }

            AndroidNetworking.get(cardItem.thumbnailUrl)
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
        val recipeCard = arrRecipeCard[position]
        holder.set(recipeCard)
        holder.binding.recipeCardImage.setOnClickListener {
            if (listener != null) {
                listener?.recipeClicked(recipeCard)
            }
        }

        holder.binding.starButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                if (likeListener != null) {
                    likeListener!!.likeOnClick(arrRecipeCard[position].id!!)
                }
            }
            override fun unLiked(likeButton: LikeButton) {
                if (likeListener != null) {
                    likeListener!!.unlikeOnClick(arrRecipeCard[position].id!!, position)
                }
            }
        })
    }
}
