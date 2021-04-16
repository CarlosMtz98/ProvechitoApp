package com.itesm.equipo3.provechito.views

interface CustomListeners {
    public fun onClickLeft(item : CustomViewModel, position : Int)

    public fun onClickRight(item : CustomViewModel, position : Int)
}