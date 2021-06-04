package com.itesm.equipo3.provechito.models

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.interfaces.IUser
import com.itesm.equipo3.provechito.pojo.User.UserRequestResponse
import com.itesm.equipo3.provechito.presenters.UserPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserModel (val presenter: UserPresenter):  IUser.Model {
    private val apiClient = ApiClient()

    override fun getUserData(context: Context) {
        apiClient.getApiService(context).getUserData()
                .enqueue(object : Callback<UserRequestResponse> {
                    override fun onFailure(call: Call<UserRequestResponse>, t: Throwable) {
                        Log.e("UserModel", "Failed")
                    }

                    override fun onResponse(call: Call<UserRequestResponse>, response: Response<UserRequestResponse>) {
                        val userData = response.body()
                        if (response.isSuccessful) {
                            Log.i("UserModel|getUserData", " ${Gson().toJson(userData)}")
                            userData?.let { presenter.userResponse(userData) }
                        } else {
                            Log.e("UserModel", response.message())
                        }
                    }
                })
    }
}