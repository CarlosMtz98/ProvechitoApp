package com.itesm.equipo3.provechito.pojo.Recipe

import com.google.gson.annotations.SerializedName
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe

class RecipeListResponse {
    @SerializedName("recipes")
    var recipes: ArrayList<Recipe>? = null
    @SerializedName("totalPages")
    var totalPages: Int = 0
    @SerializedName("currentPage")
    var currentPage: Int = 0
}