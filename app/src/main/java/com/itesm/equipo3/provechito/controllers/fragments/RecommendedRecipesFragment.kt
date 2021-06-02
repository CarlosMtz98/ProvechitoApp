package com.itesm.equipo3.provechito.controllers.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.controllers.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentRecommendedRecipesBinding
import com.itesm.equipo3.provechito.models.RecipeCard
import com.itesm.equipo3.provechito.controllers.adapters.RecipeCardAdapter


class RecommendedRecipesFragment : Fragment(), ClickListener {
    private lateinit var listener: HomeClickListener

    private var _binding: FragmentRecommendedRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecommendedRecipes: ArrayList<RecipeCard>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement SignUpListener.")
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendedRecipesBinding.inflate(inflater, container, false)
        setupRVRecommendedRecipes()
        return binding.root
    }

    private fun setupRVRecommendedRecipes() {
        val layout = GridLayoutManager(requireContext(), 2)
        layout.orientation = GridLayoutManager.VERTICAL
        binding.rvRecommendedRecipes.layoutManager = layout

        arrRecommendedRecipes = getRecommendedRecipes()
        val adaptador = RecipeCardAdapter(arrRecommendedRecipes)
        binding.rvRecommendedRecipes.adapter = adaptador

        adaptador.listener = this
    }

    private fun getRecommendedRecipes(): java.util.ArrayList<RecipeCard> {
        return arrayListOf(
            RecipeCard("Pastel de chocolate", "Repostería", "https://images.unsplash.com/photo-1614786482494-7fc57abd0074?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "Fácil"),
            RecipeCard("Enchiladas verdes", "mexicana", "https://cdn.kiwilimon.com/recetaimagen/26245/38984.jpg", "Medio")
        )
    }

    companion object {
        fun newInstance() : RecommendedRecipesFragment {
            return RecommendedRecipesFragment()
        }
    }

    override fun recipeClicked(position: Int) {
        val recipeCard = arrRecommendedRecipes[position]
        println("posicion: $recipeCard")
        listener.onRecipeCardClicked(recipeCard)
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }
}