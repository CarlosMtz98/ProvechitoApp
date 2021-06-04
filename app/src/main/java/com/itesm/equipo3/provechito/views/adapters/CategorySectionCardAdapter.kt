package com.itesm.equipo3.provechito.views.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.CategorySectionCardViewBinding
import com.itesm.equipo3.provechito.pojo.Category.Category

class CategorySectionCardAdapter(val arrCatogoryCard: ArrayList<Category>) : RecyclerView.Adapter<CategorySectionCardAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CategorySectionCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: Category) {
            binding.tvCategoryName.text = cardItem.name
            AndroidNetworking.get(cardItem.thumbnailUrl)
                .build()
                .getAsBitmap(object: BitmapRequestListener {
                    override fun onResponse(response: Bitmap?) {
                        binding.imgCategorySection.setImageBitmap(response)
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
            CategorySectionCardViewBinding.inflate(
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

        holder.binding.imgCategorySection.setOnClickListener {
            listener?.categoryClicked(category)
        }
    }
}
