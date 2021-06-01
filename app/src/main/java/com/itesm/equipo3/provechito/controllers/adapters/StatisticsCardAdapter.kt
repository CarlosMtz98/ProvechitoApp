package com.itesm.equipo3.provechito.controllers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.StatisticsCardViewBinding

import com.itesm.equipo3.provechito.models.StatisticsCard

class StatisticsCardAdapter (val arrStatisticsCard: ArrayList<StatisticsCard>) : RecyclerView.Adapter<StatisticsCardAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: StatisticsCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: StatisticsCard) {
            binding.tvStatisticsNameCard.text = cardItem.name
            binding.tvStadistic.text = cardItem.statistic
        }
    }

    var listener: ClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StatisticsCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrStatisticsCard.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarjeta = arrStatisticsCard[position]
        holder.set(tarjeta)

        holder.binding.root.setOnClickListener {
            listener?.recipeClicked(position)
            println("Hizo click ${position}")
        }
    }
}