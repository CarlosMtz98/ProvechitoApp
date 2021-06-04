package com.itesm.equipo3.provechito.interfaces

import android.content.Context
import com.itesm.equipo3.provechito.pojo.Products.Product
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse

class IRecipe {
    interface View {
        fun showRecipe(recipe: Recipe)
        fun showRecipes(recipeList: RecipeListResponse, type: Int = 0)
        fun likeRecipeAdded(recipe: Recipe)
        fun removedLike(recipeId: String)
        fun recipeProductAdded(product: Product){}
    }

    interface Presenter {
        fun getRecipe(context: Context, recipeId: String)
        fun getRecipes(context: Context, type: Int = 0)
        fun getRandomRecipe(context: Context)
        fun getByCategories(context: Context, categoryId: String)
        fun recipeDetailResponse(recipe: Recipe)
        fun recipesObtained(recipeList: RecipeListResponse, type: Int)
        fun addLike(context: Context, recipeId: String)
        fun likeAdded(recipe: Recipe)
        fun addProduct(context: Context, product: Product)
        fun productAdded(product: Product)
        fun removeLike(context: Context, recipeId: String)
        fun likeRemoved(recipeId: String)
    }

    interface Model {
        fun getRecipe(context: Context, recipeId: String)
        fun getRecipes(context: Context)
        fun getRecentRecipes(context: Context)
        fun getByCategories(context: Context, categoryId: String)
        fun getRecommendedRecipes(context: Context)
        fun getRandomRecipe(context: Context)
        fun addLike(context: Context, recipeId: String)
        fun addProduct(context: Context, product: Product)
        fun removeLike(context: Context, recipeId: String)
    }
}