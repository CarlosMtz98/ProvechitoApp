package com.itesm.equipo3.provechito.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itesm.equipo3.provechito.databinding.StadisticsCardViewBinding

import com.itesm.equipo3.provechito.models.StadisticsCard

class StadisticsCardAdapter (val arrStadisticsCard: ArrayList<StadisticsCard>) : RecyclerView.Adapter<StadisticsCardAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: StadisticsCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: StadisticsCard) {
            binding.tvStadisticsNameCard.text = cardItem.name
            binding.tvStadistic.text = cardItem.stadistic
        }
    }

    var listener: ClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StadisticsCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrStadisticsCard.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarjeta = arrStadisticsCard[position]
        holder.set(tarjeta)

        holder.binding.root.setOnClickListener {
            listener?.clicked(position)
            println("Hizo click ${position}")
        }
    }
}