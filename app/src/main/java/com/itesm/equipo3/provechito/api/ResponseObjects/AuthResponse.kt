package com.itesm.equipo3.provechito.api.ResponseObjects

import com.itesm.equipo3.provechito.models.User

data class AuthResponse (
    var statusCode: Int,
    var token: String,
    var user: User
)