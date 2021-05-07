package com.itesm.equipo3.provechito.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.models.SessionManager

// Autor: Diego Pb

class SplashScreen : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
         sessionManager = SessionManager(this)


        val timer = object: CountDownTimer(1000, 50) {
            override fun onTick(millisUntilFinished: Long) {
                return
            }

            override fun onFinish() {
                val token: String? = sessionManager.fetchAuthToken()
                println("The token in this session is: $token")
                if (token.isNullOrEmpty()) {
                    val i = Intent(baseContext, OnboardingActivity::class.java)
                    startActivity(i)
                } else {
                    val i = Intent(baseContext, MainActivity::class.java)
                    startActivity(i)
                }
                finish()
            }
        }
        timer.start()

    }
}