package com.itesm.equipo3.provechito.interfaces

import android.content.Context
import com.itesm.equipo3.provechito.pojo.Like.LikeListResponse
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe

class ILike {
    interface View {
        fun showLikes(likesList: ArrayList<Recipe>)
    }

    interface Presenter {
        fun getLikes(context: Context);
        fun likesObtained(likesList: LikeListResponse)
    }

    interface Model {
        fun getLikes(context: Context)
    }
}