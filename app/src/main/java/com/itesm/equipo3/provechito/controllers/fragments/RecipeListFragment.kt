package com.itesm.equipo3.provechito.controllers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentRecipeListBinding
import com.itesm.equipo3.provechito.models.RecipeCard
import com.itesm.equipo3.provechito.controllers.adapters.RecipeCardAdapter

class RecipeListFragment : Fragment(), ClickListener {
    private lateinit var recipeList: ArrayList<RecipeCard>
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        setupRecipeCardRV()
        return binding.root
    }

    companion object {
        fun newInstance() : RecipeListFragment {
            return RecipeListFragment()
        }
    }

    private fun setupRecipeCardRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL

        binding.rvRecipeList.layoutManager = layout

        recipeList = getRecipes()
        val adaptador = RecipeCardAdapter(recipeList)
        binding.rvRecipeList.adapter = adaptador

        adaptador.listener = this
    }

    private fun getRecipes(): ArrayList<RecipeCard> {
        return arrayListOf(
                RecipeCard("Pasta arrabiata", "italiana", "https://images.unsplash.com/photo-1607375658859-39f31567ce13?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=740&q=80", "15min"),
                RecipeCard("Pizza napolitana", "italiana", "https://images.unsplash.com/photo-1589187151053-5ec8818e661b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "35min"),
                RecipeCard("Gelato", "italiana", "https://images.unsplash.com/photo-1580915411954-282cb1b0d780?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "45min")
        )
    }

    override fun recipeClicked(position: Int) {
        println("Hello")
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }
}