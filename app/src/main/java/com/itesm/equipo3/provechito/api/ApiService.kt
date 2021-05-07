package com.itesm.equipo3.provechito.api

import com.itesm.equipo3.provechito.api.RequestObjects.LoginRequest
import com.itesm.equipo3.provechito.api.RequestObjects.SignupRequest
import com.itesm.equipo3.provechito.api.ResponseObjects.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/login")
    fun authUserLogin(@Body request: LoginRequest): Call<AuthResponse>

    @POST("/api/auth/signup")
    fun autUserSignUp(@Body request: SignupRequest): Call<AuthResponse>
}