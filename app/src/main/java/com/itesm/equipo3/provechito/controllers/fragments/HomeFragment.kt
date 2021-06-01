package com.itesm.equipo3.provechito.controllers.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.CategoryListResponse
import com.itesm.equipo3.provechito.api.ResponseObjects.RecipeListResponse
import com.itesm.equipo3.provechito.controllers.adapters.CategoryCardAdapter
import com.itesm.equipo3.provechito.controllers.adapters.RecipeCardAdapter
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.controllers.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentHomeBinding
import com.itesm.equipo3.provechito.models.CategoryCard
import com.itesm.equipo3.provechito.models.RecipeCard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), ClickListener {
    private lateinit var listener: HomeClickListener
    private lateinit var arrRecipeCard: ArrayList<RecipeCard>
    private lateinit var arrCategoryCard: ArrayList<CategoryCard>
    private lateinit var arrRecentRecipes: ArrayList<RecipeCard>
    private lateinit var apiClient: ApiClient

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        getHomeRecipe()
        getRecentRecipes()
        getHomeCategory()
        binding.btnRecomendations.setOnClickListener {
            val recommendedRecipesFragment = RecommendedRecipesFragment()
            println("Go to recommended")
            listener.onRecommendedClicked()
        }

        binding.btnRecentRecipes.setOnClickListener {
            val recentRecipesFragment = RecentRecipesFragment()
            listener.onRecentClicked()
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

    private fun setupRecipeCardRV(arr: ArrayList<RecipeCard>) {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL

        binding.rvRecipieCards.layoutManager = layout
        val adaptador = RecipeCardAdapter(arr)
        binding.rvRecipieCards.adapter = adaptador

        adaptador.listener = this
    }

    private fun setupRecentRecipesRV(arr: ArrayList<RecipeCard>) {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL

        binding.rvRecentRecipies.layoutManager = layout
        val adaptador = RecipeCardAdapter(arr)
        binding.rvRecentRecipies.adapter = adaptador

        adaptador.listener = this
    }

    private fun configureCategoryCardRV(arr: ArrayList<CategoryCard>){
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvCategoryCards.layoutManager = layout

        val adaptador = CategoryCardAdapter(arr)
        binding.rvCategoryCards.adapter = adaptador

        adaptador.listener = this
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
                    val categorytResponse = response.body()
                    if (response.isSuccessful && categorytResponse?.categories != null) {
                        for (category in categorytResponse.categories)
                        {
                            results.add(CategoryCard(category.name!!, category.thumbnailUrl!!))
                        }
                    } else {
                        // @TODO add alert that the request did not work
                    }
                    arrCategoryCard = results
                    configureCategoryCardRV(results)
                }
            })
    }

    private fun getHomeRecipe() {
        val results = ArrayList<RecipeCard>()
        apiClient.getApiService(this.context!!).getRecipes()
                .enqueue(object : Callback<RecipeListResponse> {
                    override fun onFailure(call: Call<RecipeListResponse>, t: Throwable) {
                        // Error fetching posts
                        //@TODO throw message that it could not fetch the data
                    }

                    override fun onResponse(call: Call<RecipeListResponse>, response: Response<RecipeListResponse>) {
                        val recipeListResponse = response.body()
                        if (response.isSuccessful && recipeListResponse?.recipes != null) {
                            for (recipe in recipeListResponse.recipes){
                                val recipeItem = RecipeCard(recipe.name!!, "Comida internacional", recipe.thumbnailUrl!!, "${recipe.duration.toString()} minutos")
                                results.add(recipeItem)
                            }
                        } else {
                            // @TODO add alert that the request did not work
                        }
                        arrRecipeCard = results
                        setupRecipeCardRV(arrRecipeCard)
                    }
                })
    }

    private fun getRecentRecipes() {
        val results = ArrayList<RecipeCard>()
        apiClient.getApiService(this.context!!).getRecentRecipes()
                .enqueue(object : Callback<RecipeListResponse> {
                    override fun onFailure(call: Call<RecipeListResponse>, t: Throwable) {
                        // Error fetching posts
                        //@TODO throw message that it could not fetch the data
                    }

                    override fun onResponse(call: Call<RecipeListResponse>, response: Response<RecipeListResponse>) {
                        val recipeListResponse = response.body()
                        if (response.isSuccessful && recipeListResponse?.recipes != null) {
                            for (recipe in recipeListResponse.recipes){
                                val recipeItem = RecipeCard(recipe.name!!, "Comida internacional", recipe.thumbnailUrl!!, "${recipe.duration.toString()} minutos")
                                results.add(recipeItem)
                            }
                        } else {
                            // @TODO add alert that the request did not work
                        }
                        arrRecentRecipes = results
                        setupRecentRecipesRV(arrRecentRecipes)
                    }
                })
    }

    override fun recipeClicked(position: Int) {
        val recipeCard = arrRecipeCard[position]
        println("posicion: $recipeCard")
        listener.onRecipeCardClicked(recipeCard.name, recipeCard.category, recipeCard.imgUri)
    }

    override fun categoryClicked(position: Int) {
        val categoryCard = arrCategoryCard[position]
        println("posicion: $categoryCard")
        listener.onCategoryCardClicked(categoryCard.name)
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}