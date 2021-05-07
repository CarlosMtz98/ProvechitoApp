package com.itesm.equipo3.provechito.api.ResponseObjects

import com.itesm.equipo3.provechito.models.Recipe

data class RecipeListResponse(
    var recipis: ArrayList<Recipe>,
    var tatalPages: Int?,
    var currentPage: Int?
)