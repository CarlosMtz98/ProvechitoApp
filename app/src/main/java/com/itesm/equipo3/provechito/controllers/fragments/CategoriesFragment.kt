package com.itesm.equipo3.provechito.controllers.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentCategoriesBinding
import com.itesm.equipo3.provechito.models.CategoryCard
import com.itesm.equipo3.provechito.controllers.adapters.CategoryCardAdapter
import com.itesm.equipo3.provechito.controllers.adapters.CategorySectionCardAdapter
import com.itesm.equipo3.provechito.controllers.listeners.HomeClickListener
import com.itesm.equipo3.provechito.models.RecipeCard


class CategoriesFragment : Fragment(), ClickListener {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrCategories: ArrayList<CategoryCard>
    private lateinit var listener: HomeClickListener

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
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        setupRVCategories()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRVCategories() {
        val layout = GridLayoutManager(requireContext(),2)
        layout.orientation = GridLayoutManager.VERTICAL
        binding.rvCategoryCards.layoutManager = layout

        arrCategories = getCategories()
        val adaptador = CategorySectionCardAdapter(arrCategories)
        binding.rvCategoryCards.adapter = adaptador

        adaptador.listener = this
    }

    private fun getCategories(): ArrayList<CategoryCard> {
        return arrayListOf(
            CategoryCard("Comida Italiana", "https://images.unsplash.com/photo-1551183053-bf91a1d81141?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80"),
            CategoryCard("Comida Mexicana", "https://images.unsplash.com/photo-1582234372722-50d7ccc30ebd?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80"),
            CategoryCard("Comida Argentina", "https://images.unsplash.com/photo-1544025162-d76694265947?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=640&q=80")
        )
    }

    companion object {
        fun newInstance() : CategoriesFragment {
            return CategoriesFragment()
        }
    }

    override fun recipeClicked(tarjeta: RecipeCard) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(position: Int) {
        val recipeCard = arrCategories[position]
        println("posicion: $recipeCard")
        listener.onCategoryCardClicked(arrCategories[position].name)
    }
}