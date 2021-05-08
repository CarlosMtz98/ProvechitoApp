package com.itesm.equipo3.provechito.api.ResponseObjects

import com.itesm.equipo3.provechito.models.Recipe

data class RecipeListResponse(
    var recipes: ArrayList<Recipe>,
    var totalPages: Int?,
    var currentPage: Int?
)