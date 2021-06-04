package com.itesm.equipo3.provechito.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.RequestObjects.GoogleAuthRequest
import com.itesm.equipo3.provechito.api.RequestObjects.LoginRequest
import com.itesm.equipo3.provechito.api.RequestObjects.SignupRequest
import com.itesm.equipo3.provechito.api.ResponseObjects.AuthResponse
import com.itesm.equipo3.provechito.views.fragments.SignInFragment
import com.itesm.equipo3.provechito.views.fragments.SignUpFragment
import com.itesm.equipo3.provechito.views.listeners.SignInClickListener
import com.itesm.equipo3.provechito.databinding.ActivitySigninBinding
import com.itesm.equipo3.provechito.api.Auth.SessionManager
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
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN: Int = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.server_client_id))
                    .requestEmail()
                    .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding = ActivitySigninBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setDefaultFragment()

        apiClient = ApiClient()
        sessionManager =
            SessionManager(this)
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        //println("Account: $account")
        // updateUI(account)
    }

    private fun setDefaultFragment() {
        val signinFragment = SignInFragment.newInstance()
        setFragmentOnActivity(signinFragment)
    }

    private fun setFragmentOnActivity(frag: Fragment) {
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
                    if (loginResponse == null) {
                        Log.e("SignIn", "Error cawn")
                        toastError()
                    }
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
                    if (signupResponse == null) {
                        Log.e("SignIn", "Error cawn")
                        toastError()
                    }
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

    override fun googleSingInClick() {
        signIn()
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account!!.idToken

            apiClient.getApiService(this).authGoogleUser(GoogleAuthRequest(idToken!!))
                .enqueue(object : Callback<AuthResponse> {
                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        // @TODO add alert that the request did not work
                    }
                    override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                        val signupResponse = response.body()
                        if (signupResponse == null) {
                            Log.e("SignIn", "Error cawn")
                            toastError()
                        }
                        println("Res2: $signupResponse")
                        if (response.isSuccessful && signupResponse?.user != null) {
                            println("Token: $signupResponse")
                            sessionManager.saveAuthToken(signupResponse.token)
                            val i = Intent(baseContext, MainActivity::class.java)
                            startActivity(i)
                            finish()
                        } else {
                            toastError()
                            println("Error: while signing the user")
                        }
                    }
                })
            //updateUI(account)
        } catch (e: ApiException) {
            Log.e("failed code=", e.statusCode.toString())
            toastError(e.statusCode.toString())
            //updateUI(null)
        }
    }

    fun toastError(e: String = ""){
        Toast.makeText(this, "Hubo un error, intenta de nuevo por favor.\n$e", Toast.LENGTH_LONG).show()
    }
}