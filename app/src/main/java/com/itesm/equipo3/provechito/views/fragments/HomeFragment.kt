package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.views.adapters.CategoryCardAdapter
import com.itesm.equipo3.provechito.views.adapters.RecipeCardAdapter
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentHomeBinding
import com.itesm.equipo3.provechito.interfaces.ICategory
import com.itesm.equipo3.provechito.interfaces.IRecipe
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Category.CategoryListResponse
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse
import com.itesm.equipo3.provechito.presenters.CategoryPresenter
import com.itesm.equipo3.provechito.presenters.RecipePresenter


class HomeFragment : Fragment(), IRecipe.View, ICategory.View, ClickListener {
    private val recipePresenter: RecipePresenter = RecipePresenter(this)
    private val categoryPresenter: CategoryPresenter = CategoryPresenter(this)

    private var arrRecipeCard = ArrayList<Recipe>()
    private var arrRecentRecipes = ArrayList<Recipe>()
    private var arrCategoryCard = ArrayList<Category>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiClient: ApiClient

    private lateinit var listener: HomeClickListener


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
            recipePresenter.getRecipes(it, 1)
            recipePresenter.getRecipes(it, 2)
            categoryPresenter.getCategories(it)
        }
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnRecomendations.setOnClickListener {
            listener.onRecommendedClicked(arrRecipeCard)
        }

        binding.btnRecentRecipes.setOnClickListener {
            listener.onRecentClicked(arrRecentRecipes)
        }

        binding.buttonCategory.setOnClickListener {
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

    private fun configureCategoryCardRV(arr: ArrayList<Category>){
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvCategoryCards.layoutManager = layout

        val recipeCardAdapter = CategoryCardAdapter(arr)
        binding.rvCategoryCards.adapter = recipeCardAdapter

        recipeCardAdapter.listener = this
    }

    override fun recipeClicked(recipe: Recipe) {
        listener.onRecipeCardClicked(recipe)
    }

    override fun categoryClicked(category: Category) {
        listener.onCategoryCardClicked(category)
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun showRecipe(recipe: Recipe) {
        throw NotImplementedError()
    }

    override fun showRecipes(recipeList: RecipeListResponse, type: Int) {
        when (type) {
            1 -> {
                Log.i("Recipe fetch type", "$type")
                recipeList.recipes?.let {
                    arrRecipeCard.addAll(it)
                    setupRecipeCardRV(arrRecipeCard)
                }
            }
            2 -> {
                Log.e("Recipe fetch type", "$type")
                recipeList.recipes?.let {
                    arrRecentRecipes.addAll(it)
                    setupRecentRecipesRV(arrRecentRecipes)
                }
            }
        }
    }

    override fun showCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override fun showCategories(categoryList: CategoryListResponse) {
        categoryList.categories?.let {
            arrCategoryCard.addAll(it)
            configureCategoryCardRV(arrCategoryCard)
        }
    }
}