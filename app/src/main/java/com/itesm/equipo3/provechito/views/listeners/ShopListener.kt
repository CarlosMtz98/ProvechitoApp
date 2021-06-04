package com.itesm.equipo3.provechito.views.listeners

import com.itesm.equipo3.provechito.views.components.slideLinearLayout.CustomViewModel

interface ShopListener {
    fun onClickLeft(item : CustomViewModel, position : Int)

    fun onClickRight(item : CustomViewModel, position : Int)

    fun ingredientClicked(position: Int)
}