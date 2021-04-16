package com.itesm.equipo3.provechito.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.databinding.ActivitySigninBinding

/*
* Autor: Zoe CD
 */

class SigninActivity() : AppCompatActivity(), SignInClickListener{
    private lateinit var binding: ActivitySigninBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySigninBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setDefaultFragment()
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

    override fun onSignInButtonClicked(){
        val i = Intent(baseContext, MainActivity::class.java)
        startActivity(i)
        finish()
    }


}