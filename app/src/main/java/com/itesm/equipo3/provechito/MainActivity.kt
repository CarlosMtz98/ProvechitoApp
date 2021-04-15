package com.itesm.equipo3.provechito

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.itesm.equipo3.provechito.databinding.ActivityMainBinding
import com.itesm.equipo3.provechito.views.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAppNavigationMenu()
        setDefaultFragment()
    }

    private fun setDefaultFragment() {
        setFragmentoOnActivity(HomeFragment())
    }

    private fun setupAppNavigationMenu() {
        binding.appNavigationMenu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val homeFragment = HomeFragment()
                    setFragmentoOnActivity(homeFragment)
                }
                R.id.nav_shop -> {
                    val shopFragment = ShopFragment()
                    setFragmentoOnActivity(shopFragment)
                }
                R.id.nav_search -> {
                    val searchFragment = SearchFragment()
                    setFragmentoOnActivity(searchFragment)
                }
                R.id.nav_favs -> {
                    val favsFragment = FavsFragment()
                    setFragmentoOnActivity(favsFragment)
                }
                R.id.nav_profile -> {
                    val profileFragment = ProfileFragment()
                    setFragmentoOnActivity(profileFragment)
                }
            }
            true
        }
    }

    private fun setFragmentoOnActivity(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, frag)
            .commit()
    }
}