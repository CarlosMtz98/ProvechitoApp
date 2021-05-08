package com.itesm.equipo3.provechito.views

/*
* Autor: Zoe CD
 */
interface SignInClickListener {
    fun onSignUpFragClicked()
    fun onSignInFragClicked()
    fun onSignInButtonClicked(email: String, password: String)
    fun onSignUpButtonClicked(name: String, email: String, password: String)
}