package com.itesm.equipo3.provechito.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentProfileBinding
import com.itesm.equipo3.provechito.models.RecipeCard
import com.itesm.equipo3.provechito.models.StadisticsCard

/*
    Autor: Zoe Caballero
 */
class ProfileFragment : Fragment(), ClickListener {
    private lateinit var arrStadisticsCard: ArrayList<StadisticsCard>
    private lateinit var arrLastRecipesCard: ArrayList<RecipeCard>
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        configureStadisticsRV()
        configureLastRecipesRV()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configureLastRecipesRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvLastRecipesCards.layoutManager = layout

        arrLastRecipesCard = getLastRecipes()
        val adaptador = RecipeCardAdapter(arrLastRecipesCard)
        binding.rvLastRecipesCards.adapter = adaptador

        adaptador.listener = this
    }

    private fun getLastRecipes(): ArrayList<RecipeCard>{
        return arrayListOf(
            RecipeCard("Pasta arrabiata", "italiana", "https://shorturl.at/oFLOX", "15min"),
            RecipeCard("Pizza napolitana", "italiana", "https://shorturl.at/jyU27", "35min"),
            RecipeCard("Gelato", "italiana", "https://shorturl.at/uFKNO", "45min")
        )
    }

    private fun configureStadisticsRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvStadisticsCards.layoutManager = layout

        arrStadisticsCard = getProfileStadistics()
        val adaptador = StadisticsCardAdapter(arrStadisticsCard)
        binding.rvStadisticsCards.adapter = adaptador

        adaptador.listener = this
    }

    private fun getProfileStadistics(): ArrayList<StadisticsCard>{
        return arrayListOf(
            StadisticsCard("Recetas Realizadas", "10"),
            StadisticsCard("Recetas Dominadas", "10")
        )
    }

    override fun clicked(posicion: Int) {
        val stadisticsCard = arrStadisticsCard[posicion]
        println("posicion: ${stadisticsCard}")
    }


}