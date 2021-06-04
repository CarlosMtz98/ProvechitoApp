package com.itesm.equipo3.provechito.presenters

import LikeModel
import android.content.Context
import com.itesm.equipo3.provechito.interfaces.ILike
import com.itesm.equipo3.provechito.pojo.Like.LikeListResponse
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe

class LikePresenter(val view: ILike.View) : ILike.Presenter {
    private val likeModel = LikeModel(this)

    override fun getLikes(context: Context) {
        likeModel.getLikes(context)
    }

    override fun likesObtained(likesList: LikeListResponse) {
        likesList.likes?.let {
            var likedRecipes = ArrayList<Recipe>()
            for (like in it)
                like.recipe?.let { it1 -> likedRecipes.add(it1) }
            view.showLikes(likedRecipes)
        }
    }

    override fun addNewLike(context: Context, recipeId: String) {
        likeModel.addLike(context, recipeId)
    }

    override fun removeLike(context: Context, recipeId: String, index: Int) {
        likeModel.removeLike(context, recipeId, index)
    }

    override fun popRemovedLike(index: Int) {
        view.removeLike(index)
    }

    override fun likeAdded(recipe: Recipe) {
        view.likeAdded(recipe)
    }

}