package com.itesm.equipo3.provechito.models

import java.io.Serializable

data class RecipeCard (
        val name: String,
        val category: String,
        val imgUri: String,
        val duration: String,
        val liked : Boolean = false,
        val id: String = ""
) : Serializable