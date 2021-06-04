package com.itesm.equipo3.provechito.models

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.interfaces.ICategory
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Category.CategoryListResponse
import com.itesm.equipo3.provechito.presenters.CategoryPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class CategoryModel (val presenter: CategoryPresenter):  ICategory.Model {
    private val apiClient = ApiClient()
    
    override fun getCategory(context: Context, categoryId: String) {
        apiClient.getApiService(context).getCategory(categoryId)
            .enqueue(object : Callback<Category> {
                override fun onFailure(call: Call<Category>, t: Throwable) {
                    Log.e("RecipeModel", "Failed")
                }

                override fun onResponse(call: Call<Category>, response: Response<Category>) {
                    val categoryData = response.body()
                    if (response.isSuccessful) {
                        Log.i("RecipeModel|GetRecipe", " ${Gson().toJson(categoryData)}")
                        categoryData?.let { presenter.categoryDetailResponse(it) }
                    } else {
                        Log.e("RecipeModel", response.message())
                    }
                }
            })
    }

    override fun getCategories(context: Context) {
        apiClient.getApiService(context).getCategories()
            .enqueue(object : Callback<CategoryListResponse> {
                override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                    Log.e("CategoryModel", "Failed")
                }
                override fun onResponse(call: Call<CategoryListResponse>, response: Response<CategoryListResponse>) {
                    val categoryListResponse = response.body()
                    if (response.isSuccessful) {
                        Log.i("CategoryModel|GetIes", " ${Gson().toJson(categoryListResponse)}")
                        categoryListResponse?.let { presenter.categoriesObtained(it) }
                    } else {
                        Log.e("getCategories", response.message())
                    }
                }
            })
    }
}