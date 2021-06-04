package com.itesm.equipo3.provechito.pojo.Like

import com.google.gson.annotations.SerializedName
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe

class Like {
    @SerializedName("_id")
    var id: String? = null
    var recipe: Recipe? = null
}