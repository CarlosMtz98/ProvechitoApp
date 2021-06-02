package com.itesm.equipo3.provechito.api

import com.itesm.equipo3.provechito.api.RequestObjects.*
import com.itesm.equipo3.provechito.api.ResponseObjects.DeleteResponse
import com.itesm.equipo3.provechito.api.ResponseObjects.*
import com.itesm.equipo3.provechito.models.*
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

    @GET("/api/recipe/random")
    fun getRandomRecipe(): Call<Recipe>


    // Categories
    @GET("/api/category/")
    fun getCategories(): Call<CategoryListResponse>

    @GET("/api/category/{id}")
    fun getCategory(@Path("id") id: String): Call<Category>


    // Products
    @GET("/api/product")
    fun getProducts(): Call<ProductListResponse>

    @POST("/api/product")
    fun addProduct(@Body request: ProductRequest): Call<Product>

    @PATCH("/api/product/{id}")
    fun updateProduct(@Path("id") id: String): Call<Product>

    @DELETE("/api/product/{id}")
    fun removeProduct(@Path("id") id: String): Call<DeleteResponse>


    // Likes
    @GET("/api/like")
    fun getLikes(): Call<LikesListResponse>

    @POST("/api/like")
    fun addLike(@Body request: LikeRequest): Call<Like>

    @DELETE("/api/like/{id}")
    fun removeLike(@Path("id") id: String): Call<DeleteResponse>


    // Profile
    @GET("/api/users/me")
    fun getUserData(): Call<User>
}