package com.itesm.equipo3.provechito.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itesm.equipo3.provechito.databinding.ActivityMainBinding
import com.itesm.equipo3.provechito.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity(), HomeClcikListener {
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onRecentClicked() {
        TODO("Not yet implemented")
    }

    override fun onRecommendedClicked() {
        TODO("Not yet implemented")
    }

    override fun onCategoryClicked() {
        TODO("Not yet implemented")
    }
}