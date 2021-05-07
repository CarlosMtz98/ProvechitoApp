package com.itesm.equipo3.provechito.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), HomeClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidNetworking.initialize(getApplicationContext());
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAppNavigationMenu()
        setDefaultFragment()
    }

    private fun setDefaultFragment() {
        val homeFragment = HomeFragment.newInstance()
        setFragmentoOnActivity(homeFragment)
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

    override fun onRecentClicked() {
        val recentRecipesFragment = RecentRecipesFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout, recentRecipesFragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onRecommendedClicked() {
        val recommendedRecipesFragment = RecommendedRecipesFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout, recommendedRecipesFragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onCategoryCardClicked() {
        val categoriesFragment = CategoryFocusFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, categoriesFragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onRecipeCardClicked() {
        val recipieDetailFragment = RecipeDetailFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout, recipieDetailFragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onBeginClicked() {
        val detailedStep = FragmentDetailedStep()
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout, detailedStep)
                .addToBackStack(null)
                .commit()
    }

    override fun onNextClicked() {
        val reviewFragment = ReviewFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout, reviewFragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onSettingsClicked() {
        val settingsFragment = SettingsFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, settingsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onSendClicked() {
        val homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, homeFragment)
            .addToBackStack(null)
            .commit()
    }

}