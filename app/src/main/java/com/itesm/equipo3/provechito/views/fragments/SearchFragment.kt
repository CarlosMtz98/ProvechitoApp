package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentSearchBinding
import com.itesm.equipo3.provechito.interfaces.IRecipe
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse
import com.itesm.equipo3.provechito.presenters.RecipePresenter
import com.itesm.equipo3.provechito.views.adapters.RecipeCardAdapter
import com.itesm.equipo3.provechito.views.adapters.RecipeCardFullAdapter
import com.itesm.equipo3.provechito.views.listeners.LikeClickListener
import com.itesm.equipo3.provechito.views.adapters.RecipeCardSearchAdapter


class SearchFragment : Fragment(), IRecipe.View, ClickListener {
    private val recipePresenter: RecipePresenter = RecipePresenter(this)

    private lateinit var listener: HomeClickListener
    private var arrSearchedRecipes = ArrayList<Recipe>()
    private var arrRecipes = ArrayList<Recipe>()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
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
            recipePresenter.getRecipes(it, 0)
            binding.btnRandomRecipe.setOnClickListener {
                recipePresenter.getRandomRecipe(context)
            }

            binding.editTextSample.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    println(s)
                    arrSearchedRecipes.clear()
                    if (count > 0) {
                        val re = Regex("$s", RegexOption.IGNORE_CASE)
                        for (recipe in arrRecipes) {
                            recipe.name?.let { it1 ->
                                if (re.containsMatchIn(it1)) {
                                    arrSearchedRecipes.add(recipe)
                                }
                            }
                        }
                    } else {
                        arrSearchedRecipes.addAll(arrRecipes)
                    }
                    configureRV(arrSearchedRecipes)
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }


        return binding.root
    }

    private fun configureRV(recipeList: ArrayList<Recipe>) {
        val layout = LinearLayoutManager(requireContext())
        binding.rvCategoryCardsSearch.layoutManager = layout

        val adaptador = RecipeCardSearchAdapter(recipeList)
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
        listener.onRecipeCardClicked(recipe)
    }

    override fun showRecipes(recipeList: RecipeListResponse, type: Int) {
        recipeList.recipes?.let {
            arrRecipes = recipeList.recipes!!
            configureRV(arrRecipes)
        }

    }

    override fun likeRecipeAdded(recipe: Recipe) {
        Toast.makeText(this.context, "Receta ${recipe.name} añadida a favoritas", Toast.LENGTH_SHORT).show()
    }

    override fun removedLike(recipeId: String) {
        Toast.makeText(this.context, "Receta removida de favoritos", Toast.LENGTH_SHORT).show()
    }
}
