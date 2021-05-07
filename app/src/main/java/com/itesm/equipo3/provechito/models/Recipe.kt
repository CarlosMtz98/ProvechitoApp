package com.itesm.equipo3.provechito.models

data class Recipe (
    var _id: String?,
    var name: String?,
    var preparationSteps: ArrayList<String?>,
    var ingredints: ArrayList<String?>,
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
    var imagesUrls: ArrayList<String?>,
    var likes: Int?
)