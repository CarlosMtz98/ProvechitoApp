package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.views.adapters.RecipeCardFullAdapter
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentCategoryFocusBinding
import com.itesm.equipo3.provechito.interfaces.ICategory
import com.itesm.equipo3.provechito.interfaces.IRecipe
import com.itesm.equipo3.provechito.models.IngredientCard
import com.itesm.equipo3.provechito.models.RecipeModel
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Category.CategoryListResponse
import com.itesm.equipo3.provechito.pojo.Products.Product
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse
import com.itesm.equipo3.provechito.presenters.RecipePresenter
import com.itesm.equipo3.provechito.views.listeners.LikeClickListener

class CategoryFocusFragment : Fragment(), IRecipe.View, ClickListener, LikeClickListener {
    private val recipePresenter: RecipePresenter = RecipePresenter(this)
    private var _binding: FragmentCategoryFocusBinding? = null
    private val binding get() = _binding!!
    var arrRecipeList = ArrayList<Recipe>()
    private lateinit var listener: HomeClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryFocusBinding.inflate(inflater, container, false)
        val bundle = this.arguments
        if (bundle != null) {
            val categoryData = bundle.getSerializable("categoryData") as Category
            binding.etCategoryName.text = categoryData.name
            if (!categoryData.id.isNullOrEmpty()) {
                context?.let {
                    recipePresenter.getByCategories(it, categoryData.id!!)
                }
            } else {
                // Todo: handle error where there is no id from the previews fragemnt
            }
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement HomeClickListner.")
        }
    }

    private fun setupRecipeCardRV(arrRecipeList: ArrayList<Recipe>) {
        val layout = GridLayoutManager(requireContext(), 1)
        binding.rvRecetas.layoutManager = layout
        val adaptador = RecipeCardFullAdapter(arrRecipeList)
        binding.rvRecetas.adapter = adaptador
        adaptador.listener = this
        adaptador.likeListener = this
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun recipeClicked(tarjeta: Recipe) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(category: Category) {
        TODO("Not yet implemented")
    }

    override fun showRecipe(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override fun showRecipes(recipeList: RecipeListResponse, type: Int) {
        recipeList.recipes?.let {
            arrRecipeList.addAll(it)
            setupRecipeCardRV(arrRecipeList)
        }
    }

    override fun likeRecipeAdded(recipe: Recipe) {
        Toast.makeText(this.context, "Receta ${recipe.name} añadida a favoritas", Toast.LENGTH_SHORT).show()
    }

    override fun removedLike(recipeId: String) {
        Toast.makeText(this.context, "Receta removida de favoritos", Toast.LENGTH_SHORT).show()
    }

    override fun likeOnClick(recipeId: String) {
        Log.i("LikeClicked", "RecipeId: $recipeId")
        context?.let { recipePresenter.addLike(it, recipeId) }
    }

    override fun unlikeOnClick(recipeId: String, index: Int) {
        Log.i("LikeUnClicked", "RecipeId: $recipeId, Index:")
        context?.let { recipePresenter.removeLike(it, recipeId) }
    }
}