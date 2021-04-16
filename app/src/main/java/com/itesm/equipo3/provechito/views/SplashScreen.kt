package com.itesm.equipo3.provechito.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.itesm.equipo3.provechito.R

// Autor: Diego Pb

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val timer = object: CountDownTimer(1000, 50) {
            override fun onTick(millisUntilFinished: Long) {
                return
            }

            override fun onFinish() {
                val i = Intent(baseContext, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        timer.start()

    }
}