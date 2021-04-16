package com.itesm.equipo3.provechito.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.databinding.FragmentFavsBinding
import com.itesm.equipo3.provechito.databinding.FragmentHomeBinding
import com.itesm.equipo3.provechito.models.RecipeCard

class FavsFragment : Fragment(), ClickListener {
    private var _binding: FragmentFavsBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecipeCard: ArrayList<RecipeCard>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavsBinding.inflate(inflater, container, false)
        setupRvFavsRecipe()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRvFavsRecipe() {
        val gridLayout = GridLayoutManager(requireContext(), 2)
        gridLayout.orientation = GridLayoutManager.HORIZONTAL

        binding.rvFavsRecipeCard.layoutManager = gridLayout

        arrRecipeCard = getLikedRecipes()
        val adaptador = RecipeCardAdapter(arrRecipeCard)
        binding.rvFavsRecipeCard.adapter = adaptador

        adaptador.listener = this
    }

    private fun getLikedRecipes(): ArrayList<RecipeCard> {
        return arrayListOf(
                RecipeCard("Pasta arrabiata", "italiana", "https://shorturl.at/oFLOX", "15min"),
                RecipeCard("Pizza napolitana", "italiana", "https://shorturl.at/jyU27", "35min"),
                RecipeCard("Enchiladas verdes", "mexicana", "https://shorturl.at/uFKNO", "35min"),
                RecipeCard("Gelato", "italiana", "https://shorturl.at/uFKNO", "45min")
        )
    }

    override fun clicked(posicion: Int) {
        println("Clicked $posicion")
    }

}