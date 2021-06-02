package com.itesm.equipo3.provechito.controllers.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentFavsBinding
import com.itesm.equipo3.provechito.models.RecipeCard
import com.itesm.equipo3.provechito.controllers.listeners.HomeClickListener
import com.itesm.equipo3.provechito.controllers.adapters.RecipeCardAdapter
import com.itesm.equipo3.provechito.controllers.adapters.RecipeCardFullAdapter

class FavsFragment : Fragment(), ClickListener {
    private var _binding: FragmentFavsBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecipeCard: ArrayList<RecipeCard>
    private lateinit var listener: HomeClickListener

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
        _binding = FragmentFavsBinding.inflate(inflater, container, false)
        setupRvFavsRecipe()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRvFavsRecipe() {
        val gridLayout = GridLayoutManager(requireContext(), 1)
        gridLayout.orientation = GridLayoutManager.VERTICAL

        binding.rvFavsRecipeCard.layoutManager = gridLayout

        arrRecipeCard = getLikedRecipes()
        arrRecipeCard.forEach {
            it.liked = true
        }
        val adaptador = RecipeCardFullAdapter(arrRecipeCard)
        binding.rvFavsRecipeCard.adapter = adaptador

        adaptador.listener = this
    }

    private fun getLikedRecipes(): ArrayList<RecipeCard> {
        return arrayListOf(
            RecipeCard("Pasta arrabiata", "italiana", "https://images.unsplash.com/photo-1607375658859-39f31567ce13?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=740&q=80", "15min"),
            RecipeCard("Pizza napolitana", "italiana", "https://images.unsplash.com/photo-1589187151053-5ec8818e661b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "35min"),
            RecipeCard("Enchiladas verdes", "mexicana", "https://cdn.kiwilimon.com/recetaimagen/26245/38984.jpg", "35min"),
            RecipeCard("Gelato", "italiana", "https://images.unsplash.com/photo-1580915411954-282cb1b0d780?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "45min")
        )
    }

    override fun recipeClicked(tarjeta: RecipeCard) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }

}