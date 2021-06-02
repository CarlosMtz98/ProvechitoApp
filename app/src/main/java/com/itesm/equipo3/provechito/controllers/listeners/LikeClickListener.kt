package com.itesm.equipo3.provechito.controllers.listeners

interface LikeClickListener {
    fun likeOnClick(recipeId: String)
    fun unlikeOnClick(recipeId: String)
}