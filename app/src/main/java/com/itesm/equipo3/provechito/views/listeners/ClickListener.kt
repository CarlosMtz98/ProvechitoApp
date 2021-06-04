package com.itesm.equipo3.provechito.views.listeners

import com.itesm.equipo3.provechito.pojo.Recipe.Recipe

interface ClickListener {
    fun recipeClicked(recipe: Recipe)
    fun categoryClicked(category: Category)
}