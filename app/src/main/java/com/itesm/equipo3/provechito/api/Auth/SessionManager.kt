package com.itesm.equipo3.provechito.api.Auth

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.itesm.equipo3.provechito.R


class SessionManager (context: Context) {
    private var preferences: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthToken(token: String) {
        val editor = preferences.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return preferences.getString(USER_TOKEN, null)
    }
}