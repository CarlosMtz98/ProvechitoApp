package com.itesm.equipo3.provechito.controllers.listeners

interface ClickListener {
    fun recipeClicked(position: Int)
    fun categoryClicked(position: Int)
}