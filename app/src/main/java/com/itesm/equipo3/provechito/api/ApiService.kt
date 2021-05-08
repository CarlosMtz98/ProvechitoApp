package com.itesm.equipo3.provechito.api

import com.itesm.equipo3.provechito.api.RequestObjects.LoginRequest
import com.itesm.equipo3.provechito.api.RequestObjects.SignupRequest
import com.itesm.equipo3.provechito.api.ResponseObjects.AuthResponse
import com.itesm.equipo3.provechito.api.ResponseObjects.CategoryListResponse
import com.itesm.equipo3.provechito.api.ResponseObjects.RecipeListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/login")
    fun authUserLogin(@Body request: LoginRequest): Call<AuthResponse>

    @POST("/api/auth/signup")
    fun autUserSignUp(@Body request: SignupRequest): Call<AuthResponse>

    // Recipes
    @GET("/api/recipe/")
    fun getRecipes(): Call<RecipeListResponse>

    @GET("/api/recipe/recent")
    fun getRecentRecipes(): Call<RecipeListResponse>

    @GET("/api/recipe/recommended")
    fun getRecommendedRecipes(): Call<RecipeListResponse>

    // Categories
    @GET("/api/category/")
    fun getCategories(): Call<CategoryListResponse>
}