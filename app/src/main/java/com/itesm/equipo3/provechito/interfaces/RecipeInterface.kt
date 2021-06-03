package com.itesm.equipo3.provechito.interfaces

import android.content.Context
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse

class RecipeInterface {
    interface View {
        fun showRecipe(recipe: Recipe)
        fun showRecipes(recipeList: RecipeListResponse, type: Int = 0)
    }

    interface Presenter {
        fun getRecipe(context: Context, recipeId: String)
        fun getRecipes(context: Context, type: Int = 0)
        fun addLike(context: Context, recipeId: String)
        fun recipeDetailResponse(recipe: Recipe)
        fun recipesObtained(recipeList: RecipeListResponse, type: Int)
    }

    interface Model {
        fun getRecipe(context: Context, recipeId: String)
        fun getRecipes(context: Context)
        fun getRecentRecipes(context: Context)
        fun getRecommendedRecipes(context: Context)
        fun addLike(context: Context, recipeId: String)
    }
}