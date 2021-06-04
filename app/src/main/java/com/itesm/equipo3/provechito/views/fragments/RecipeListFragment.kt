package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentRecipeListBinding
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.views.adapters.RecipeCardAdapter
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener

class RecipeListFragment : Fragment(), ClickListener {
    private lateinit var listener: HomeClickListener
    private lateinit var recipeList: ArrayList<Recipe>
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement SignUpListener.")
        }
    }

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

    private fun getRecipes(): ArrayList<Recipe> {
        return arrayListOf()
    }

    override fun recipeClicked(tarjeta: Recipe) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(category: Category) {
        throw NotImplementedError()
    }
}