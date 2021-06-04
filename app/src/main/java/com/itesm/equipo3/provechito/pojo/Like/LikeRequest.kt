package com.itesm.equipo3.provechito.pojo.Like

class LikeRequest(recipeId: String) {
    var recipeId: String? = null

    init {
        this.recipeId = recipeId
    }
}