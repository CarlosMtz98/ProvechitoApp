package com.itesm.equipo3.provechito.controllers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itesm.equipo3.provechito.databinding.LayoutScreenBinding
import com.itesm.equipo3.provechito.models.ScreenItem

class IntroViewPageAdapter(private val mContext: Context, val screenArrayList: ArrayList<ScreenItem> ) : RecyclerView.Adapter<IntroViewPageAdapter.PageHolder>(){

    inner class PageHolder(val binding: LayoutScreenBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(item : ScreenItem) {
            binding.introTitle.text = item.name
            binding.introDescription.text = item.description
            binding.introImg.setImageResource(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        return PageHolder(
            LayoutScreenBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return screenArrayList.size
    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        val screen = screenArrayList[position]
        holder.set(screen)

        holder.binding.root.setOnClickListener {
            println("Hizo click ${position}")
        }
    }

}