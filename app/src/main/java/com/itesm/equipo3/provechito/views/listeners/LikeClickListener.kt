package com.itesm.equipo3.provechito.views.listeners

interface LikeClickListener {
    fun likeOnClick(recipeId: String)
    fun unlikeOnClick(recipeId: String, index: Int)
}