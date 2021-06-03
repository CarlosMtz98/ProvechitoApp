package com.itesm.equipo3.provechito.models

import java.io.Serializable

data class RecipeMode (
    var _id: String?,
    var name: String?,
    var preparationSteps: ArrayList<String?>,
    var ingredients: ArrayList<String?>,
    var categories: ArrayList<Category>,
    var score: Int?,
    var description: String?,
    var duration: Int?,
    var calories: Int?,
    var cookingTime: Int?,
    var difficulty: String?,
    var price: String?,
    var advice: String?,
    var thumbnailUrl: String?,
    var featureImageUrl: String?,
    var imageUrls: ArrayList<String?>,
    var likes: Int?
) : Serializable {
    constructor(name: String, category: String, thumbnailUrl: String?) : this(
            "", name, arrayListOf("N/A"), arrayListOf("N/A"), arrayListOf(Category(category)),
            0, "", 0, 0, 0, "N/A", "N/A",
            "N/A", thumbnailUrl, thumbnailUrl, arrayListOf(thumbnailUrl), 0)
}