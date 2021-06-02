package com.itesm.equipo3.provechito.models

import java.io.Serializable

data class RecipeCard (
        val name: String,
        val category: String,
        val imgUri: String,
        val duration: String,
        var liked : Boolean = false,
        val id: String = "",
        val precio: String = "N/A",
        val dificultad: String = "N/A"
) : Serializable