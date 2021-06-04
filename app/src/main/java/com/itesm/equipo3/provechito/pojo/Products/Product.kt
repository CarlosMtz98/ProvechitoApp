package com.itesm.equipo3.provechito.pojo.Products

import com.google.gson.annotations.SerializedName
import java.util.*

class Product(name: String, description: String) {
    @SerializedName("_id")
    val id: String? = null
    var name: String? = null
    var description: String? = null
    @SerializedName("updatedAt")
    val updatedDate: Date? = null

    init {
        this.name = name
        this.description = description
    }
}