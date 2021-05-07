package com.itesm.equipo3.provechito.api.RequestObjects

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String
)