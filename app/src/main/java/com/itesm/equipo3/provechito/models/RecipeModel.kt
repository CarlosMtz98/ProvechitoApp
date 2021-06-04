package com.itesm.equipo3.provechito.models

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.interfaces.IRecipe
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse
import com.itesm.equipo3.provechito.presenters.RecipePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeModel(val presenter: RecipePresenter) : IRecipe.Model {
    private val apiClient = ApiClient()

    override fun getRecipe(context: Context, recipeId: String) {
        apiClient.getApiService(context!!).getRecipe(recipeId)
                .enqueue(object : Callback<Recipe> {
                    override fun onFailure(call: Call<Recipe>, t: Throwable) {
                        Log.e("RecipeModel", "Failed")
                    }

                    override fun onResponse(call: Call<Recipe>, response: Response<Recipe>) {
                        val recipeData = response.body()
                        if (response.isSuccessful) {
                            Log.i("RecipeModel|GetRecipe", " ${Gson().toJson(recipeData)}")
                            recipeData?.let { presenter.recipeDetailResponse(it) }
                        } else {
                            Log.e("RecipeModel", response.message())
                        }
                    }
                })
    }

    override fun getRecipes(context: Context) {
        apiClient.getApiService(context).getRecipes()
                .enqueue(object : Callback<RecipeListResponse> {
                    override fun onFailure(call: Call<RecipeListResponse>, t: Throwable) {
                        Log.e("RecipeModel", "Failed")
                    }
                    override fun onResponse(call: Call<RecipeListResponse>, response: Response<RecipeListResponse>) {
                        val recipeListResponse = response.body()
                        if (response.isSuccessful) {
                            Log.i("RecipeModel|GetRecipes", " ${Gson().toJson(recipeListResponse)}")
                            recipeListResponse?.let { presenter.recipesObtained(it, 0) }
                        } else {
                            Log.e("getRecipes", response.message())
                        }
                    }
                })
    }

    override fun getRecentRecipes(context: Context) {
        apiClient.getApiService(context!!).getRecentRecipes()
                .enqueue(object : Callback<RecipeListResponse> {
                    override fun onFailure(call: Call<RecipeListResponse>, t: Throwable) {
                        Log.e("RecipeModel", "Failed")
                    }

                    override fun onResponse(call: Call<RecipeListResponse>, response: Response<RecipeListResponse>) {
                        val recipeListResponse = response.body()
                        if (response.isSuccessful && recipeListResponse?.recipes != null) {
                            Log.i("RecipeModel|Recommended", " ${Gson().toJson(recipeListResponse)}")
                            presenter.recipesObtained(recipeListResponse, 1)
                        } else {
                            Log.e("getRecommendedRecipes", response.message())
                        }
                    }
                })
    }

    override fun getRecommendedRecipes(context: Context) {
        apiClient.getApiService(context!!).getRecommendedRecipes()
                .enqueue(object : Callback<RecipeListResponse> {
                    override fun onFailure(call: Call<RecipeListResponse>, t: Throwable) {
                        Log.e("RecipeModel", "Failed")
                    }

                    override fun onResponse(call: Call<RecipeListResponse>, response: Response<RecipeListResponse>) {
                        val recipeListResponse = response.body()
                        if (response.isSuccessful && recipeListResponse?.recipes != null) {
                            Log.i("RecipeModel|Recommended", " ${Gson().toJson(recipeListResponse)}")
                            presenter.recipesObtained(recipeListResponse, 2)
                        } else {
                            Log.e("getRecommendedRecipes", response.message())
                        }
                    }
                })
    }

    override fun addLike(context: Context, recipeId: String) {

    }
}