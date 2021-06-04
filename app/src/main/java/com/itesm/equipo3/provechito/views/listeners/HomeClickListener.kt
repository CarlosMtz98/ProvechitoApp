package com.itesm.equipo3.provechito.views.listeners

import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import java.util.*

interface HomeClickListener {
    fun onRecentClicked(arr: ArrayList<Recipe>)
    fun onRecommendedClicked(arr: ArrayList<Recipe>)
    fun onCategoriesLinkClicked()
    fun onCategoryCardClicked(name: Category)
    fun onRecipeCardClicked(recipe: Recipe)
    fun onSettingsClicked()
    fun onAboutClicked()
    fun onBeginClicked()
    fun onNextClicked()
    fun onSendClicked()
}