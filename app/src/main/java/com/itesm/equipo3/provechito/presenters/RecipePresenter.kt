package com.itesm.equipo3.provechito.presenters

import android.content.Context
import android.util.Log
import com.itesm.equipo3.provechito.interfaces.IRecipe
import com.itesm.equipo3.provechito.models.RecipeModel
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse

class RecipePresenter(val view: IRecipe.View) : IRecipe.Presenter {
    private val recipeModel = RecipeModel(this)

    override fun getRecipe(context: Context, recipeId: String) {
        recipeModel.getRecipe(context, recipeId)
    }

    override fun getRandomRecipe(context: Context){
        Log.i("Recipe Presenter",  "Running: Random")
        recipeModel.getRandomRecipe(context)
    }

    override fun getByCategories(context: Context, categoryId: String) {
        recipeModel.getByCategories(context, categoryId)
    }

    override fun getRecipes(context: Context, type: Int) {
        Log.i("Recipe Presenter",  "Running type: $type")
        when (type) {
            1 -> {
                Log.i("Recipe Presenter",  "Running: $type")
                recipeModel.getRecentRecipes(context)
            }
            2 -> {
                Log.i("Recipe Presenter",  "Running: $type")
                recipeModel.getRecommendedRecipes(context)
            }
            else -> recipeModel.getRecipes(context)
        }
    }

    override fun recipeDetailResponse(recipe: Recipe) {
        view.showRecipe(recipe)
    }

    override fun recipesObtained(recipeList: RecipeListResponse, type: Int) {
        view.showRecipes(recipeList, type)
    }

    override fun addLike(context: Context, recipeId: String) {
        TODO("Not yet implemented")
    }
}