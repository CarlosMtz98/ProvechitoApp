package com.itesm.equipo3.provechito.models

data class RecipeCard (val name: String, val category: String, val imgUri: String, val duration: String, var liked : Boolean = false)