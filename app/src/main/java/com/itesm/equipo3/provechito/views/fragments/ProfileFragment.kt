package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentProfileBinding
import com.itesm.equipo3.provechito.interfaces.IRecipe
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse
import com.itesm.equipo3.provechito.presenters.RecipePresenter
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.views.adapters.RecipeCardFullAdapter

/*
    Autor: Zoe Caballero
 */
class ProfileFragment : Fragment(), IRecipe.View, ClickListener {
    private lateinit var listener: HomeClickListener
    private val presenter = RecipePresenter(this)
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var arrLastRecipesList = ArrayList<Recipe>()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement SignUpListener.")
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        context?.let {
            presenter.getRecipes(it, 2)
        }

        binding.imgButtonSettings.setOnClickListener {
            listener.onSettingsClicked()
        }

        binding.imageButton3.setOnClickListener {
            val recentRecipesFragment = RecentRecipesFragment()
            listener.onRecentClicked(arrLastRecipesList)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configureLastRecipesRV(recipeList: ArrayList<Recipe>) {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvLastRecipesCards.layoutManager = layout

        val recipeAdapter = RecipeCardFullAdapter(recipeList)
        binding.rvLastRecipesCards.adapter = recipeAdapter

        recipeAdapter.listener = this
    }

    override fun recipeClicked(recipe: Recipe) {
        listener.onRecipeCardClicked(recipe)
    }

    override fun categoryClicked(category: Category) {
        throw NotImplementedError()
    }

    override fun showRecipe(recipe: Recipe) {
        throw NotImplementedError()
    }

    override fun showRecipes(recipeList: RecipeListResponse, type: Int) {
        Log.i("recipes list", " ${Gson().toJson(recipeList)}")
        when (type) {
            2 -> {
                recipeList.recipes?.let {
                    arrLastRecipesList.addAll(it)
                    configureLastRecipesRV(arrLastRecipesList)
                }
            }
        }
    }


}