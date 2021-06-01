package com.itesm.equipo3.provechito.controllers.listeners

import com.itesm.equipo3.provechito.views.CustomViewModel

interface CustomListeners {
    public fun onClickLeft(item : CustomViewModel, position : Int)

    public fun onClickRight(item : CustomViewModel, position : Int)
}