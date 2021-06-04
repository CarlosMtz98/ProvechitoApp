package com.itesm.equipo3.provechito.pojo.Category

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Category : Serializable {
    @SerializedName("_id")
    var id: String? = null
    var name: String? = null
    var thumbnailUrl: String? = null
}