package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.CategoryListResponse
import com.itesm.equipo3.provechito.views.adapters.CategoryCardAdapter
import com.itesm.equipo3.provechito.views.adapters.RecipeCardAdapter
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentHomeBinding
import com.itesm.equipo3.provechito.interfaces.RecipeInterface
import com.itesm.equipo3.provechito.models.CategoryCard
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse
import com.itesm.equipo3.provechito.presenters.RecipePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), RecipeInterface.View, ClickListener {
    private val recipePresenter: RecipePresenter = RecipePresenter(this)
    private var arrRecipeCard = ArrayList<Recipe>()
    private var arrRecentRecipes = ArrayList<Recipe>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiClient: ApiClient

    private lateinit var listener: HomeClickListener
    private lateinit var arrCategoryCard: ArrayList<CategoryCard>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
            apiClient = ApiClient()
        } else {
            throw ClassCastException("$context must implement HomeClickListner.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.let {
            if (arrRecentRecipes.isNullOrEmpty())
                recipePresenter.getRecipes(it, 1)
            if (arrRecentRecipes.isNullOrEmpty())
                recipePresenter.getRecipes(it, 2)
        }
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        getHomeCategory()

        binding.btnRecomendations.setOnClickListener {
            val recommendedRecipesFragment = RecommendedRecipesFragment()
            listener.onRecommendedClicked(arrRecipeCard)
        }

        binding.btnRecentRecipes.setOnClickListener {
            val recentRecipesFragment = RecentRecipesFragment()
            listener.onRecentClicked(arrRecentRecipes)
        }

        binding.buttonCategory.setOnClickListener {
            val categoriesFragment = CategoriesFragment()
            listener.onCategoriesLinkClicked()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecipeCardRV(arr: ArrayList<Recipe>) {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL

        binding.rvRecipieCards.layoutManager = layout
        val recipeCardAdapter = RecipeCardAdapter(arr)
        binding.rvRecipieCards.adapter = recipeCardAdapter

        recipeCardAdapter.listener = this
    }

    private fun setupRecentRecipesRV(arr: ArrayList<Recipe>) {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL

        binding.rvRecentRecipies.layoutManager = layout
        val recipeCardAdapter = RecipeCardAdapter(arr)
        binding.rvRecentRecipies.adapter = recipeCardAdapter

        recipeCardAdapter.listener = this
    }

    private fun configureCategoryCardRV(arr: ArrayList<CategoryCard>){
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvCategoryCards.layoutManager = layout

        val recipeCardAdapter = CategoryCardAdapter(arr)
        binding.rvCategoryCards.adapter = recipeCardAdapter

        recipeCardAdapter.listener = this
    }

    private fun getHomeCategory() {
        val results = ArrayList<CategoryCard>()
        apiClient.getApiService(this.context!!).getCategories()
            .enqueue(object : Callback<CategoryListResponse> {
                override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                    // Error fetching posts
                    //@TODO throw message that it could not fetch the data
                }

                override fun onResponse(call: Call<CategoryListResponse>, response: Response<CategoryListResponse>) {
                    val categoryResponse = response.body()
                    if (response.isSuccessful && categoryResponse?.categories != null) {
                        for (category in categoryResponse.categories) {
                            results.add(CategoryCard(category.name!!, category.thumbnailUrl!!))
                        }
                    } else {
                        // @TODO add alert that the request did not work
                        println("Failed response category: ${response.message()}")
                    }
                    arrCategoryCard = results
                    configureCategoryCardRV(results)
                }
            })
    }

    override fun recipeClicked(recipe: Recipe) {
        listener.onRecipeCardClicked(recipe)
    }

    override fun categoryClicked(position: Int) {
        val categoryCard = arrCategoryCard[position]
        listener.onCategoryCardClicked(categoryCard.name)
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun showRecipe(recipe: Recipe) {
        throw NotImplementedError()
    }

    override fun showRecipes(recipeResponseList: RecipeListResponse, type: Int) {
        Log.e("Recipe fetch", " ${Gson().toJson(recipeResponseList)}")
        when (type) {
            1 -> {
                Log.e("Recipe fetch type", "$type")
                recipeResponseList.recipes?.let {
                    arrRecipeCard.addAll(it)
                    setupRecipeCardRV(arrRecipeCard)
                }
            }
            2 -> {
                Log.e("Recipe fetch type", "$type")
                recipeResponseList.recipes?.let {
                    arrRecentRecipes.addAll(it)
                    setupRecentRecipesRV(arrRecentRecipes)
                }
            }
        }
    }
}