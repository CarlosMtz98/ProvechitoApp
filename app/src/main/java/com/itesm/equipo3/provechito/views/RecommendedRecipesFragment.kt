package com.itesm.equipo3.provechito.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentRecommendedRecipesBinding
import com.itesm.equipo3.provechito.models.RecentRecipeCard


class RecommendedRecipesFragment : Fragment(), ClickListener {

    private var _binding: FragmentRecommendedRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecommendedRecipes: ArrayList<RecentRecipeCard>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendedRecipesBinding.inflate(inflater, container, false)
        setupRVRecommendedRecipes()
        return binding.root
    }

    private fun setupRVRecommendedRecipes() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvRecommendedRecipes.layoutManager = layout

        arrRecommendedRecipes = getRecommendedRecipes()
        val adaptador = RecentRecipeCardAdapter(arrRecommendedRecipes)
        binding.rvRecommendedRecipes.adapter = adaptador

        adaptador.listener = this
    }

    private fun getRecommendedRecipes(): java.util.ArrayList<RecentRecipeCard> {
        return arrayListOf(
            RecentRecipeCard("Pastel de chocolate", "Repostería", "https://images.unsplash.com/photo-1614786482494-7fc57abd0074?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "Fácil"),
            RecentRecipeCard("Enchiladas verdes", "mexicana", "https://cdn.kiwilimon.com/recetaimagen/26245/38984.jpg", "Medio")
        )
    }

    companion object {
        fun newInstance() : RecommendedRecipesFragment{
            return RecommendedRecipesFragment()
        }
    }

    override fun clicked(position: Int) {
        println("Clicked $position")
    }
}