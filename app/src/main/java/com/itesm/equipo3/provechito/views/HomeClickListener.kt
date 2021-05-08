package com.itesm.equipo3.provechito.views

import com.itesm.equipo3.provechito.models.RecipeCard
import java.util.*

interface HomeClickListener {
    fun onRecentClicked()
    fun onRecommendedClicked()
    fun onCategoriesLinkClicked()
    fun onCategoryCardClicked(name: String)
    fun onRecipeCardClicked(name: String, category: String, imgUri: String)
    fun onSettingsClicked()
    fun onBeginClicked()
    fun onNextClicked()
    fun onSendClicked()
}