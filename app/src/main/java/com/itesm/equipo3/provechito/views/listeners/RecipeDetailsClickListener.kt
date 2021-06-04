package com.itesm.equipo3.provechito.views.listeners

import com.itesm.equipo3.provechito.pojo.Products.Product

interface RecipeDetailsClickListener {
    fun clickRecipeIngredient(product: Product)
}