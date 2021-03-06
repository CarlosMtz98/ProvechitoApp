package com.itesm.equipo3.provechito.interfaces

import android.content.Context
import com.itesm.equipo3.provechito.pojo.Like.LikeListResponse
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe

class ILike {
    interface View {
        fun showLikes(likesList: ArrayList<Recipe>)
        fun removeLike(index: Int)
        fun likeAdded(recipe: Recipe)
        fun reloadLikesList()
    }

    interface Presenter {
        fun getLikes(context: Context);
        fun addNewLike(context: Context, recipeId: String)
        fun removeLike(context: Context, recipeId: String, index: Int)
        fun popRemovedLike(index: Int)
        fun likeAdded(recipe: Recipe)
        fun likesObtained(likesList: LikeListResponse)
    }

    interface Model {
        fun getLikes(context: Context)
        fun addLike(context: Context, recipeId: String)
        fun removeLike(context: Context, recipeId: String, index: Int)
    }
}