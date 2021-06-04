package com.itesm.equipo3.provechito.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.views.fragments.*
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.ActivityMainBinding
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe


class MainActivity : AppCompatActivity(), HomeClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidNetworking.initialize(applicationContext)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAppNavigationMenu()
        setDefaultFragment()
    }

    private fun setDefaultFragment() {
        val homeFragment = HomeFragment.newInstance()
        setFragmentOnActivity(homeFragment)
    }

    private fun setupAppNavigationMenu() {
        binding.appNavigationMenu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val homeFragment = HomeFragment()
                    setFragmentOnActivity(homeFragment)
                }
                R.id.nav_shop -> {
                    val shopFragment = ShopFragment()
                    setFragmentOnActivity(shopFragment)
                }
                R.id.nav_search -> {
                    val searchFragment = SearchFragment()
                    setFragmentOnActivity(searchFragment)
                }
                R.id.nav_favs -> {
                    val favsFragment = FavsFragment()
                    setFragmentOnActivity(favsFragment)
                }
                R.id.nav_profile -> {
                    val profileFragment = ProfileFragment()
                    setFragmentOnActivity(profileFragment)
                }
            }
            true
        }
    }

    private fun setFragmentOnActivity(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, frag)
            .commit()
    }

    override fun onRecentClicked(arr: ArrayList<Recipe>) {
        val recentRecipesFragment = RecentRecipesFragment.newInstance()
        val arguments = Bundle()
        arguments.putSerializable("arrRecent", arr)
        recentRecipesFragment.arguments = arguments
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout, recentRecipesFragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onRecommendedClicked(arr: ArrayList<Recipe>) {
        val recommendedRecipesFragment = RecommendedRecipesFragment.newInstance()
        val arguments = Bundle()
        arguments.putSerializable("arrRecipe", arr)
        recommendedRecipesFragment.arguments = arguments
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout, recommendedRecipesFragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onCategoriesLinkClicked() {
        val categoriesFragment = CategoriesFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, categoriesFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCategoryCardClicked(name: String) {
        val categoriesFragment = CategoryFocusFragment()
        val arguments = Bundle()
        arguments.putString("NAME", name)
        categoriesFragment.arguments = arguments
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, categoriesFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onRecipeCardClicked(recipe: Recipe) {
        val recipeDetailFragment = RecipeDetailFragment()
        val arguments = Bundle()
        arguments.putSerializable("recipeData", recipe)
        recipeDetailFragment.arguments = arguments
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, recipeDetailFragment)
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

    override fun onAboutClicked() {
        val aboutFragment = AboutFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, aboutFragment)
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