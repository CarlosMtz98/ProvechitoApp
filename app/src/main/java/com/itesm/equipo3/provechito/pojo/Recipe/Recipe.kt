package com.itesm.equipo3.provechito.pojo.Recipe

import com.google.gson.annotations.SerializedName
import com.itesm.equipo3.provechito.models.Category
import java.io.Serializable

class Recipe : Serializable {
    @SerializedName("_id")
    var id: String? = null
    var name: String? = null
    var preparationSteps: ArrayList<String?>?= null
    var ingredients: ArrayList<String?>? = null
    var categories: ArrayList<Category>? = null
    var score: Int = 0
    var description: String? = null
    var duration: Int? = null
    var calories: Int? = null
    var cookingTime: Int? = null
    var difficulty: String? = null
    var price: String? = null
    var advice: String? = null
    var thumbnailUrl: String? = null
    var featureImageUrl: String? = null
    var imageUrls: ArrayList<String?>? = null
    var likes: Int = 0
    var hasUserLike: Boolean = false
}