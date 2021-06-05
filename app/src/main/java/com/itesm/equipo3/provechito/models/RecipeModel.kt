package com.itesm.equipo3.provechito.models

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.DeleteResponse
import com.itesm.equipo3.provechito.interfaces.IRecipe
import com.itesm.equipo3.provechito.pojo.Like.Like
import com.itesm.equipo3.provechito.pojo.Like.LikeRequest
import com.itesm.equipo3.provechito.pojo.Products.Product
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse
import com.itesm.equipo3.provechito.presenters.RecipePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeModel(val presenter: RecipePresenter) : IRecipe.Model {
    private val apiClient = ApiClient()

    override fun getRecipe(context: Context, recipeId: String) {
        apiClient.getApiService(context).getRecipe(recipeId)
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
        apiClient.getApiService(context).getRecipes(1, 200)
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
        apiClient.getApiService(context).getRecentRecipes()
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

    override fun getByCategories(context: Context, categoryId: String) {
        apiClient.getApiService(context).getByCategory(categoryId)
            .enqueue(object : Callback<RecipeListResponse> {
                override fun onFailure(call: Call<RecipeListResponse>, t: Throwable) {
                    Log.e("RecipeModel", "$categoryId failed, exception: ${t.message}")
                }

                override fun onResponse(call: Call<RecipeListResponse>, response: Response<RecipeListResponse>) {
                    val recipeListResponse = response.body()
                    if (response.isSuccessful && recipeListResponse?.recipes != null) {
                        Log.i("RecipeModel|ByCategory", " ${Gson().toJson(recipeListResponse)}")
                        presenter.recipesObtained(recipeListResponse, 0)
                    } else {
                        Log.e("getByCategory", response.message())
                    }
                }
            })
    }

    override fun getRecommendedRecipes(context: Context) {
        apiClient.getApiService(context).getRecommendedRecipes()
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

    override fun getRandomRecipe(context: Context) {
        apiClient.getApiService(context).getRandomRecipe()
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

    override fun addLike(context: Context, recipeId: String) {
        apiClient.getApiService(context).addLike(LikeRequest(recipeId))
                .enqueue(object : Callback<Like> {
                    override fun onFailure(call: Call<Like>, t: Throwable) {
                        Log.e("RecipeModel", "AddLike response failed STATUS: ${t.message}")
                    }

                    override fun onResponse(call: Call<Like>, response: Response<Like>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful && serviceResponse != null) {
                            Log.i("RecipeModel", "AddLike response Success")
                            serviceResponse.recipe?.let { presenter.likeAdded(it) }

                        } else {
                            Log.e("RecipeModel", "AddLike response failed STATUS: ${response.isSuccessful}")
                        }
                    }
                })
    }

    override fun addProduct(context: Context, product: Product) {
        apiClient.getApiService(context).addProduct(product)
                .enqueue(object: Callback<Product> {
                    override fun onFailure(call: Call<Product>, t: Throwable) {
                        Log.e("RecipeModel", "AddProduct Failed Response")
                    }

                    override fun onResponse(call: Call<Product>, response: Response<Product>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful) {
                            Log.i("RecipeModel", "AddProduct Success Response")
                            if (serviceResponse != null) {
                                presenter.view.recipeProductAdded(serviceResponse)
                            }

                        } else {
                            Log.e("RecipeModel", "AddProduct Failed Response")
                        }
                    }

                })
    }

    override fun removeLike(context: Context, recipeId: String) {
            apiClient.getApiService(context).removeLikeByRecipe(recipeId)
                    .enqueue(object : Callback<DeleteResponse> {
                        override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                            Log.e("LikeModel", "RemoveLike response failed STATUS: ${t.message}")
                        }

                        override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                            val serviceResponse = response.body()
                            if (response.isSuccessful) {
                                Log.i("LikeModel", "RemoveLike response Success")
                                presenter.view.removedLike(recipeId)
                            } else {
                                Log.e("LikeModel", "RemoveLike response failed STATUS: ${response.isSuccessful}")
                            }
                        }
                    })

    }
}