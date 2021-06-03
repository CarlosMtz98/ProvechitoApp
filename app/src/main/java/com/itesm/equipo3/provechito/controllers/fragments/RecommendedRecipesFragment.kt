package com.itesm.equipo3.provechito.controllers.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.RecipeListResponse
import com.itesm.equipo3.provechito.controllers.adapters.RecipeCardFullAdapter
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.controllers.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentRecommendedRecipesBinding
import com.itesm.equipo3.provechito.models.RecipeCard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        arrRecommendedRecipes = arguments!!.getSerializable("arrRecipe") as ArrayList<RecipeCard>
        setupRVRecommendedRecipes()
        return binding.root
    }

    private fun setupRVRecommendedRecipes() {
        val layout = GridLayoutManager(requireContext(), 1)
        layout.orientation = GridLayoutManager.VERTICAL
        binding.rvRecommendedRecipes.layoutManager = layout


        val adaptador = RecipeCardFullAdapter(arrRecommendedRecipes)
        binding.rvRecommendedRecipes.adapter = adaptador

        adaptador.listener = this
    }

    companion object {
        fun newInstance() : RecommendedRecipesFragment {
            return RecommendedRecipesFragment()
        }
    }

    override fun recipeClicked(tarjeta: RecipeCard) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }
}