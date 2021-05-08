package com.itesm.equipo3.provechito.models

import com.google.gson.annotations.SerializedName

class User (
    @SerializedName("_id") val id: String?,
    val userName: String?,
    @SerializedName("name") val firstName: String?,
    val lastName: String?,
    val countryCode: String?,
    val language: String?,
    val rol: String?
)