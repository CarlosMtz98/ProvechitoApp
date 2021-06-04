package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentSearchBinding
import com.itesm.equipo3.provechito.interfaces.ICategory
import com.itesm.equipo3.provechito.interfaces.IRecipe
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Category.CategoryListResponse
import com.itesm.equipo3.provechito.views.adapters.CategorySectionCardAdapter
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse
import com.itesm.equipo3.provechito.presenters.CategoryPresenter
import com.itesm.equipo3.provechito.presenters.RecipePresenter


class SearchFragment : Fragment(), IRecipe.View, ClickListener, ICategory.View {
    private val recipePresenter: RecipePresenter = RecipePresenter(this)
    private val categoryPresenter: CategoryPresenter = CategoryPresenter(this)

    private lateinit var listener: HomeClickListener
    private var arrCategories = ArrayList<Category>()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiClient: ApiClient


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
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        context?.let {
            val context = it
            categoryPresenter.getCategories(it)
            binding.btnRandomRecipe.setOnClickListener {
                recipePresenter.getRandomRecipe(context)
            }
        }

        return binding.root
    }

    private fun configureRVCategoryShop(categoryCardList: ArrayList<Category>) {
        val layout = GridLayoutManager(requireContext(), 2)
        binding.rvCategoryCardsSearch.layoutManager = layout

        val adaptador = CategorySectionCardAdapter(categoryCardList)
        binding.rvCategoryCardsSearch.adapter = adaptador

        adaptador.listener = this
    }

    override fun recipeClicked(tarjeta: Recipe) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(category: Category) {
        listener.onCategoryCardClicked(category)
    }

    override fun showRecipe(recipe: Recipe) {
        throw NotImplementedError()
    }

    override fun showRecipes(recipeList: RecipeListResponse, type: Int) {
        throw NotImplementedError()
    }

    override fun showCategory(category: Category) {
        throw NotImplementedError()
    }

    override fun showCategories(categoryList: CategoryListResponse) {
        categoryList.categories?.let {
            arrCategories = categoryList.categories!!
            configureRVCategoryShop(arrCategories)
        }
    }
}
