package com.itesm.equipo3.provechito.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentProfileBinding
import com.itesm.equipo3.provechito.models.RecipeCard
import com.itesm.equipo3.provechito.models.StatisticsCard

/*
    Autor: Zoe Caballero
 */
class ProfileFragment : Fragment(), ClickListener {
    private lateinit var listener: HomeClcikListener
    private lateinit var arrStatisticsCard: ArrayList<StatisticsCard>
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

        binding.imgButtonSettings.setOnClickListener {
            val recommendedRecipesFragment = SettingsFragment()
            println("Go to recommended")
            listener.onSettingsClicked()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is HomeClcikListener) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement SignUpListener.")
        }

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
        binding.rvStatisticsCards.layoutManager = layout

        arrStatisticsCard = getProfileStatistics()
        val adaptador = StatisticsCardAdapter(arrStatisticsCard)
        binding.rvStatisticsCards.adapter = adaptador

        adaptador.listener = this
    }

    private fun getProfileStatistics(): ArrayList<StatisticsCard>{
        return arrayListOf(
            StatisticsCard("Recetas Realizadas", "10"),
            StatisticsCard("Recetas Dominadas", "10")
        )
    }

    override fun clicked(posicion: Int) {
        val stadisticsCard = arrStatisticsCard[posicion]
        println("posicion: ${stadisticsCard}")
    }


}