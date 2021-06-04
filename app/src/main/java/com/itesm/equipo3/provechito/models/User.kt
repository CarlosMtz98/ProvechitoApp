package com.itesm.equipo3.provechito.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User(): Serializable {
    @SerializedName("_id")
    var id: String? = null
    var name: String? = null
    var email: String? = null
}