package com.itesm.equipo3.provechito.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.views.listeners.ShopListener
import com.itesm.equipo3.provechito.views.components.slideLinearLayout.BaseViewHolder
import com.itesm.equipo3.provechito.views.components.slideLinearLayout.CustomViewHolder
import com.itesm.equipo3.provechito.views.components.slideLinearLayout.CustomViewModel
import com.itesm.equipo3.provechito.views.components.slideLinearLayout.SwipeState

class CustomAdapter : RecyclerView.Adapter<BaseViewHolder> {

    companion object {
        private val TAG : String = CustomAdapter::class.java.getSimpleName()
    }
    /**Main */
    private val shopListener : ShopListener
    private val swipeState : SwipeState

    private var list : MutableList<CustomViewModel>

    constructor(shopListener : ShopListener, swipeState : SwipeState) : super() {
        this.shopListener = shopListener
        this.swipeState = swipeState
    }

    init {
        list = mutableListOf<CustomViewModel>()
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : BaseViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val view : View = layoutInflater.inflate(R.layout.product_item_view, parent, false)
        return CustomViewHolder(parent.getContext(), view, shopListener)
    }

    override fun onBindViewHolder(holder : BaseViewHolder, position : Int) {
        holder.bindDataToViewHolder(list[position], position, swipeState)
    }

    override fun getItemCount() : Int {
        return list.size
    }

    public fun setItems(items : MutableList<CustomViewModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun insertItem(item : CustomViewModel, position : Int) {
        list.add(position, item)
        notifyItemInserted(position)
    }

    fun insertItems(items : List<CustomViewModel>, position : Int) {
        list.addAll(items)
        notifyItemRangeChanged(position, itemCount)
    }

    fun updateItem(item : CustomViewModel, position : Int) {
        list[position] = item
        notifyItemChanged(position)
    }

    fun deleteItem(position : Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun deleteAllItems() {
        list.clear()
        notifyItemRangeRemoved(0, itemCount)
    }
}