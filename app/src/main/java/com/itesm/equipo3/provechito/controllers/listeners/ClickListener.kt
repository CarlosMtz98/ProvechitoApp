package com.itesm.equipo3.provechito.controllers.listeners

import com.itesm.equipo3.provechito.models.RecipeCard

interface ClickListener {
    fun recipeClicked(tarjeta: RecipeCard)
    fun categoryClicked(position: Int)
}