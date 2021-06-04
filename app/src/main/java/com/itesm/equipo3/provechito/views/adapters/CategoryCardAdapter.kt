package com.itesm.equipo3.provechito.views.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.CategoryCardViewBinding
import com.itesm.equipo3.provechito.models.CategoryCard

class CategoryCardAdapter (val arrCatogoryCard: ArrayList<CategoryCard>) : RecyclerView.Adapter<CategoryCardAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CategoryCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: CategoryCard) {
            binding.tvCategoryCardName.text = cardItem.name
            AndroidNetworking.get(cardItem.imgUrl)
                .build()
                .getAsBitmap(object: BitmapRequestListener {
                    override fun onResponse(response: Bitmap?) {
                        binding.categoryCardImage.setImageBitmap(response)
                    }
                    override fun onError(anError: ANError?) {
                        println(anError?.message.toString())
                    }
                })
        }
    }

    var listener: ClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CategoryCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrCatogoryCard.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = arrCatogoryCard[position]
        holder.set(category)

        holder.binding.categoryCardImage.setOnClickListener {
            listener?.categoryClicked(position)
            println("Hizo click $position")
        }
    }
}
