package com.itesm.equipo3.provechito.views.listeners

import com.itesm.equipo3.provechito.views.components.slideLinearLayout.CustomViewModel

interface CustomListeners {
    public fun onClickLeft(item : CustomViewModel, position : Int)

    public fun onClickRight(item : CustomViewModel, position : Int)

    fun ingredientClicked(position: Int)
}