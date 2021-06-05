package com.itesm.equipo3.provechito.api

import com.itesm.equipo3.provechito.api.RequestObjects.*
import com.itesm.equipo3.provechito.api.ResponseObjects.*
import com.itesm.equipo3.provechito.pojo.Category.CategoryListResponse
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.models.User
import com.itesm.equipo3.provechito.pojo.Like.Like
import com.itesm.equipo3.provechito.pojo.Like.LikeListResponse
import com.itesm.equipo3.provechito.pojo.Like.LikeRequest
import com.itesm.equipo3.provechito.pojo.Products.Product
import com.itesm.equipo3.provechito.pojo.Products.ProductListResponse
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse
import com.itesm.equipo3.provechito.pojo.User.UserRequestResponse
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
    fun getRecipes(@Query("page") page: Int = 1, @Query("limit") limit: Int = 20): Call<RecipeListResponse>

    @GET("/api/recipe/recent")
    fun getRecentRecipes(): Call<RecipeListResponse>

    @GET("/api/recipe/recommended")
    fun getRecommendedRecipes(): Call<RecipeListResponse>

    @GET("/api/recipe/{id}")
    fun getRecipe(@Path("id") id: String): Call<Recipe>

    @GET("/api/recipe/random")
    fun getRandomRecipe(): Call<Recipe>

    @GET("/api/recipe/by/category/{id}")
    fun getByCategory(@Path("id") id: String): Call<RecipeListResponse>

    @GET("/api/recipe/search")
    fun searchRecipes(@Query("recipeName") recipeName: String)


    // Categories
    @GET("/api/category/")
    fun getCategories(): Call<CategoryListResponse>

    @GET("/api/category/{id}")
    fun getCategory(@Path("id") id: String): Call<Category>


    // Products
    @GET("/api/product")
    fun getProducts(): Call<ProductListResponse>

    @POST("/api/product")
    fun addProduct(@Body newProduct: Product): Call<Product>

    @PATCH("/api/product/{id}")
    fun updateProduct(@Path("id") id: String, @Body updateProduct: Product ): Call<Product>

    @DELETE("/api/product/{id}")
    fun removeProduct(@Path("id") id: String): Call<DeleteResponse>


    // Likes
    @GET("/api/like")
    fun getLikes(): Call<LikeListResponse>

    @POST("/api/like")
    fun addLike(@Body request: LikeRequest): Call<Like>

    @DELETE("/api/like/{id}")
    fun removeLike(@Path("id") id: String): Call<DeleteResponse>

    @DELETE("/api/like/by/recipe/{id}")
    fun removeLikeByRecipe(@Path("id") recipeId: String): Call<DeleteResponse>


    // Profile
    @GET("/api/users/me")
    fun getUserData(): Call<UserRequestResponse>
}