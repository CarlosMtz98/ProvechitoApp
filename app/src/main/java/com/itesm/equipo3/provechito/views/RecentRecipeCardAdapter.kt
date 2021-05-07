package com.itesm.equipo3.provechito.views

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.itesm.equipo3.provechito.databinding.RecentRecipeCardViewBinding
import com.itesm.equipo3.provechito.models.RecentRecipeCard

class RecentRecipeCardAdapter (val arrRecentRecipeCard: ArrayList<RecentRecipeCard>) : RecyclerView.Adapter<RecentRecipeCardAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RecentRecipeCardViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun set (cardItem: RecentRecipeCard){
            binding.tvRecentRecipeNameCard.text = cardItem.name
            binding.tvRecentRecipeCardCategory.text = cardItem.category
            binding.tvDifficulty.text = cardItem.difficulty
            AndroidNetworking.get(cardItem.imgUri)
                .build()
                .getAsBitmap(object: BitmapRequestListener{
                    override fun onResponse(response: Bitmap?) {
                        binding.recentRecipeCardImage.setImageBitmap(response)
                    }

                    override fun onError(anError: ANError?) {
                        println(anError?.message.toString())
                    }
                })
        }
    }

    var listener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentRecipeCardAdapter.ViewHolder {
        return ViewHolder(
                RecentRecipeCardViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return arrRecentRecipeCard.size
    }

    override fun onBindViewHolder(holder: RecentRecipeCardAdapter.ViewHolder, position: Int) {
        val tarjeta = arrRecentRecipeCard[position]
                holder.set(tarjeta)
        holder.binding.recentRecipeCardImage.setOnClickListener{
            listener?.recipeClicked(position)
        }
    }
}