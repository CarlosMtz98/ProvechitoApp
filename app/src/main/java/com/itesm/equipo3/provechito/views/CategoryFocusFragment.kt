package com.itesm.equipo3.provechito.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentCategoryFocusBinding
import com.itesm.equipo3.provechito.models.RecipeCard

class CategoryFocusFragment : Fragment(), ClickListener {

    private var _binding: FragmentCategoryFocusBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecipeCard: ArrayList<RecipeCard>
    private lateinit var listener: HomeClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryFocusBinding.inflate(inflater, container, false)
        val name = arguments!!.getString("NAME")
        binding.etCategoryName.text = name
        setupRecipeCardRV()
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

    private fun setupRecipeCardRV() {
        val layout = GridLayoutManager(requireContext(), 2)
        binding.rvRecetas.layoutManager = layout
        arrRecipeCard = getHomeRecipe()
        val adaptador = RecipeCardAdapter(arrRecipeCard)
        binding.rvRecetas.adapter = adaptador
        adaptador.listener = this
    }

    private fun getHomeRecipe(): ArrayList<RecipeCard> {
        return arrayListOf(
            RecipeCard("Pasta arrabiata", "italiana", "https://images.unsplash.com/photo-1607375658859-39f31567ce13?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=740&q=80", "15min"),
            RecipeCard("Pizza napolitana", "italiana", "https://images.unsplash.com/photo-1589187151053-5ec8818e661b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "35min"),
            RecipeCard("Gelato", "italiana", "https://images.unsplash.com/photo-1580915411954-282cb1b0d780?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "45min"),
            RecipeCard("Gelato", "italiana", "https://images.unsplash.com/photo-1580915411954-282cb1b0d780?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "45min"),
            RecipeCard("Gelato", "italiana", "https://images.unsplash.com/photo-1580915411954-282cb1b0d780?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "45min"),
            RecipeCard("Gelato", "italiana", "https://images.unsplash.com/photo-1580915411954-282cb1b0d780?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "45min"),
            RecipeCard("Gelato", "italiana", "https://images.unsplash.com/photo-1580915411954-282cb1b0d780?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "45min")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun recipeClicked(position: Int) {
        val recipeCard = arrRecipeCard[position]
        println("posicion: $recipeCard")
        listener.onRecipeCardClicked(recipeCard.name, recipeCard.category, recipeCard.imgUri)
    }

    override fun categoryClicked(position: Int) {
        println("jajas")
    }
}