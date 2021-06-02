package com.itesm.equipo3.provechito.api

import com.itesm.equipo3.provechito.api.RequestObjects.GoogleAuthRequest
import com.itesm.equipo3.provechito.api.RequestObjects.LoginRequest
import com.itesm.equipo3.provechito.api.RequestObjects.SignupRequest
import com.itesm.equipo3.provechito.api.ResponseObjects.AuthResponse
import com.itesm.equipo3.provechito.api.ResponseObjects.CategoryListResponse
import com.itesm.equipo3.provechito.api.ResponseObjects.RecipeListResponse
import com.itesm.equipo3.provechito.models.Category
import com.itesm.equipo3.provechito.models.Recipe
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // Auth
    @POST("/api/auth/login")
    fun authUserLogin(@Body request: LoginRequest): Call<AuthResponse>

    @POST("/api/auth/signup")
    fun autUserSignUp(@Body request: SignupRequest): Call<AuthResponse>

    @POST("/api/auth/googleAuthToken")
    fun authGoogleUser(@Body request: GoogleAuthRequest): Call<AuthResponse>


    // Recipes
    @GET("/api/recipe/")
    fun getRecipes(): Call<RecipeListResponse>

    @GET("/api/recipe/recent")
    fun getRecentRecipes(): Call<RecipeListResponse>

    @GET("/api/recipe/recommended")
    fun getRecommendedRecipes(): Call<RecipeListResponse>

    @GET("/api/recipe/{id}")
    fun getRecipe(@Path("id") id: String): Call<Recipe>


    // Categories
    @GET("/api/category/")
    fun getCategories(): Call<CategoryListResponse>

    @GET("/api/category/{id}")
    fun getCategory(@Path("id") id: String): Call<Category>

    // Products


    // Likes


    // Profile
}