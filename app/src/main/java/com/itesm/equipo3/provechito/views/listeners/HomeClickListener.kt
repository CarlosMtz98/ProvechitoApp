package com.itesm.equipo3.provechito.views.listeners

import com.itesm.equipo3.provechito.models.RecipeCard
import java.util.*

interface HomeClickListener {
    fun onRecentClicked(arr: ArrayList<RecipeCard>)
    fun onRecommendedClicked(arr: ArrayList<RecipeCard>)
    fun onCategoriesLinkClicked()
    fun onCategoryCardClicked(name: String)
    fun onRecipeCardClicked(recipe: RecipeCard)
    fun onSettingsClicked()
    fun onAboutClicked()
    fun onBeginClicked()
    fun onNextClicked()
    fun onSendClicked()
}