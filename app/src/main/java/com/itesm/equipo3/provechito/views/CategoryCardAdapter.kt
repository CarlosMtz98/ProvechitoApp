package com.itesm.equipo3.provechito.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itesm.equipo3.provechito.databinding.CategoryCardViewBinding
import com.itesm.equipo3.provechito.models.CategoryCard

class CategoryCardAdapter (val arrCatogoryCard: ArrayList<CategoryCard>) : RecyclerView.Adapter<CategoryCardAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CategoryCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: CategoryCard) {
            binding.tvCategoryCardName.text = cardItem.name
            //holder.binding.recipeCardImage.setImageResource()
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

        holder.binding.root.setOnClickListener {
            listener?.clicked(position)
            println("Hizo click ${position}")
        }
    }
}
