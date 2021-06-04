package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentProfileBinding
import com.itesm.equipo3.provechito.interfaces.IUser
import com.itesm.equipo3.provechito.interfaces.IRecipe
import com.itesm.equipo3.provechito.models.User
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse
import com.itesm.equipo3.provechito.presenters.RecipePresenter
import com.itesm.equipo3.provechito.presenters.UserPresenter
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.views.adapters.RecipeCardAdapter
import com.itesm.equipo3.provechito.views.adapters.StatisticsCardAdapter
import com.itesm.equipo3.provechito.views.listeners.LikeClickListener
import com.itesm.equipo3.provechito.views.adapters.RecipeCardFullAdapter

/*
    Autor: Zoe Caballero
 */
class ProfileFragment : Fragment(), IRecipe.View, LikeClickListener, IUser.View, ClickListener {
    private lateinit var listener: HomeClickListener
    private val presenter = RecipePresenter(this)
    private val userPresenter = UserPresenter(this)
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
            userPresenter.getUserData(it)
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

        val recipeAdapter = RecipeCardAdapter(recipeList)
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

    override fun likeRecipeAdded(recipe: Recipe) {
        Toast.makeText(this.context, "Receta ${recipe.name} añadida a favoritas", Toast.LENGTH_SHORT).show()
    }

    override fun removedLike(recipeId: String) {
        Toast.makeText(this.context, "Receta removida de favoritos", Toast.LENGTH_SHORT).show()
    }

    override fun showProfile(user: User) {
        if (!user.name.isNullOrEmpty()) {
            binding.tvProfileName.text = user.name
        } else if (!user.email.isNullOrEmpty()) {
            binding.tvProfileName.text = user.email
        }
        binding.imgButtonSettings.setOnClickListener {
            listener.onSettingsClicked(user)
        }
    }

    override fun likeOnClick(recipeId: String) {
        Log.i("LikeClicked", "RecipeId: $recipeId")
        context?.let { presenter.addLike(it, recipeId) }
    }

    override fun unlikeOnClick(recipeId: String, index: Int) {
        Log.i("LikeUnClicked", "RecipeId: $recipeId, Index:")
        context?.let { presenter.removeLike(it, recipeId) }
    }
}