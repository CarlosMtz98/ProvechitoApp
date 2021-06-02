package com.itesm.equipo3.provechito.controllers.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.IngredientCardViewBinding
import com.itesm.equipo3.provechito.databinding.StepCardViewBinding
import com.itesm.equipo3.provechito.models.IngredientCard
import com.itesm.equipo3.provechito.models.StepCard

class StepCardAdapter ( val arrSteps: ArrayList<StepCard>): RecyclerView.Adapter<StepCardAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: StepCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set(cardItem: StepCard) {
            binding.stepDescription.text = cardItem.description
        }
    }

    var listener: ClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StepCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarjeta = arrSteps[position]
        holder.set(tarjeta)

        holder.binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            holder.binding.stepDescription.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.stepDescription.setTextColor(Color.GRAY)
            holder.binding.checkBox.isEnabled = false
        }

        holder.binding.root.setOnClickListener {
            holder.binding.stepDescription.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.stepDescription.setTextColor(Color.GRAY)
            holder.binding.checkBox.isChecked = true
            holder.binding.checkBox.isEnabled = false
        }
    }

    override fun getItemCount(): Int {
        return arrSteps.count()
    }
}