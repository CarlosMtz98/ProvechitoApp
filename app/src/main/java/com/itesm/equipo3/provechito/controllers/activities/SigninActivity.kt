package com.itesm.equipo3.provechito.controllers.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.RequestObjects.LoginRequest
import com.itesm.equipo3.provechito.api.RequestObjects.SignupRequest
import com.itesm.equipo3.provechito.api.ResponseObjects.AuthResponse
import com.itesm.equipo3.provechito.controllers.fragments.SignInFragment
import com.itesm.equipo3.provechito.controllers.fragments.SignUpFragment
import com.itesm.equipo3.provechito.controllers.listeners.SignInClickListener
import com.itesm.equipo3.provechito.databinding.ActivitySigninBinding
import com.itesm.equipo3.provechito.models.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
* Autor: Zoe CD
 */

class SigninActivity() : AppCompatActivity(), SignInClickListener {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setDefaultFragment()

        apiClient = ApiClient()
        sessionManager = SessionManager(this)
    }

    private fun setDefaultFragment() {
        val signinFragment = SignInFragment.newInstance()
        setFragmentoOnActivity(signinFragment)
    }

    private fun setFragmentoOnActivity(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.signInFrameLayout, frag)
            .commit()
    }

    override fun onSignUpFragClicked() {
        val signUpFragment = SignUpFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.signInFrameLayout, signUpFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onSignInFragClicked() {
        val signInFragment = SignInFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.signInFrameLayout, signInFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onSignInButtonClicked(email: String, password: String){
        apiClient.getApiService(this).authUserLogin(LoginRequest(email, password))
            .enqueue(object : Callback<AuthResponse> {
                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    // @TODO add alert that the request did not work
                }

                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    val loginResponse = response.body()
                    println("Res: ${loginResponse.toString()}")
                    if (response.isSuccessful && loginResponse?.user != null) {
                        sessionManager.saveAuthToken(loginResponse.token)
                        val i = Intent(baseContext, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        // @TODO add alert that the request did not work
                    }
                }
            })
    }

    override fun onSignUpButtonClicked(name: String, email: String, password: String) {
        apiClient.getApiService(this).autUserSignUp(SignupRequest(name, email, password))
            .enqueue(object : Callback<AuthResponse> {
                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    // @TODO add alert that the request did not work
                }

                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    val signupResponse = response.body()
                    println("Res: ${signupResponse.toString()}")
                    if (response.isSuccessful && signupResponse?.user != null) {
                        println("Token: ${signupResponse}")
                        sessionManager.saveAuthToken(signupResponse.token)
                        val i = Intent(baseContext, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        println("Error: while signing the user")
                        // @TODO add alert that the request did not work
                    }
                }
            })
    }


}