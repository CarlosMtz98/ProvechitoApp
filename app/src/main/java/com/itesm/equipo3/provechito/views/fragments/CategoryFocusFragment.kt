package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.views.adapters.RecipeCardFullAdapter
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentCategoryFocusBinding
import com.itesm.equipo3.provechito.models.RecipeCard
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe

class CategoryFocusFragment : Fragment(), ClickListener {

    private var _binding: FragmentCategoryFocusBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecipeCard: ArrayList<Recipe>
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
        val layout = GridLayoutManager(requireContext(), 1)
        binding.rvRecetas.layoutManager = layout
        arrRecipeCard = getHomeRecipe()
        val adaptador = RecipeCardFullAdapter(arrRecipeCard)
        binding.rvRecetas.adapter = adaptador
        adaptador.listener = this
    }

    private fun getHomeRecipe(): ArrayList<Recipe> {
        return arrayListOf()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun recipeClicked(tarjeta: Recipe) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(position: Int) {

    }
}