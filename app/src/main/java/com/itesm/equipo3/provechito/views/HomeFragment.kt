package com.itesm.equipo3.provechito.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentHomeBinding

import com.itesm.equipo3.provechito.models.RecipeCard


class HomeFragment : Fragment(), ClickListener {

    private lateinit var arrRecipeCard: ArrayList<RecipeCard>
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        configuarRV()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configuarRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvRecipieCards.layoutManager = layout

        arrRecipeCard = getHomeRecipe()
        val adaptador = RecipeCardAdapter(arrRecipeCard)
        binding.rvRecipieCards.adapter = adaptador

        adaptador.listener = this
    }

    private fun getHomeRecipe(): ArrayList<RecipeCard> {
        return arrayListOf(
            RecipeCard("Pasta arrabiata", "italiana", "https://shorturl.at/oFLOX", "15min"),
            RecipeCard("Pizza napolitana", "italiana", "https://shorturl.at/jyU27", "35min"),
            RecipeCard("Gelato", "italiana", "https://shorturl.at/uFKNO", "45min")
        )
    }

    override fun clicked(posicion: Int) {
        val recipeCard = arrRecipeCard[posicion]
        println("posicion: ${recipeCard}")
    }

   /* override fun clicked(posicion: Int) {
        val tarjeta = arrTarjetas[posicion]
        val url = Uri.parse("https://www.google.com/search?q=${tarjeta.banco}+${tarjeta.banco}")
        val intBuscar = Intent(Intent.ACTION_VIEW, url)
        startActivity(intBuscar)
    }*/
}