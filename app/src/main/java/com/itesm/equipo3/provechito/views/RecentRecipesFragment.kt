package com.itesm.equipo3.provechito.views


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentRecentRecipesBinding
import com.itesm.equipo3.provechito.models.RecipeCard

class RecentRecipesFragment : Fragment(), ClickListener {
    private var _binding: FragmentRecentRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecentRecipeCard: ArrayList<RecipeCard>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecentRecipesBinding.inflate(inflater, container, false)
        setupRVRecentRecipes()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRVRecentRecipes() {
        val layout = GridLayoutManager(requireContext(), 2)
        layout.orientation = GridLayoutManager.VERTICAL
        binding.rvRecentRecipes.layoutManager = layout

        arrRecentRecipeCard = getRecentRecipes()
        val adaptador = RecipeCardAdapter(arrRecentRecipeCard)
        binding.rvRecentRecipes.adapter = adaptador

        adaptador.listener = this
    }

    private fun getRecentRecipes(): ArrayList<RecipeCard> {
        return arrayListOf(
            RecipeCard("Pastel de chocolate", "Repostería", "https://images.unsplash.com/photo-1614786482494-7fc57abd0074?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "Fácil"),
            RecipeCard("Enchiladas verdes", "mexicana", "https://cdn.kiwilimon.com/recetaimagen/26245/38984.jpg", "Medio"),
            RecipeCard("Pizza napolitana", "italiana", "https://images.unsplash.com/photo-1589187151053-5ec8818e661b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "Difícil")
        )
    }

    companion object {
        fun newInstance() : RecentRecipesFragment {
            return RecentRecipesFragment()
        }
    }

    override fun recipeClicked(position: Int) {
        println("Clicked $position")
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }
}