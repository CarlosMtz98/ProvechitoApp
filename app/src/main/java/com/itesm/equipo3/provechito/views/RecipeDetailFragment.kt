package com.itesm.equipo3.provechito.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentRecipeDetailBinding
import com.itesm.equipo3.provechito.models.IngredientCard
import com.itesm.equipo3.provechito.models.RecipeCard
import kotlin.ClassCastException

class RecipeDetailFragment : Fragment(), ClickListener{

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecipeCard: ArrayList<RecipeCard>
    private lateinit var arrIngredients: ArrayList<IngredientCard>
    private lateinit var listener: ClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        setupRecipeCardRV()
        setupIngredientRV()
        return binding.root
    }

    private fun setupIngredientRV() {
        val layout = GridLayoutManager(requireContext(), 2)
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvIngredientsRecipe.layoutManager = layout
        arrIngredients = getIngredients()
        val adaptador = IngredientCardAdapter(arrIngredients)
        binding.rvIngredientsRecipe.adapter = adaptador
        adaptador.listener = this
    }

    private fun getIngredients(): ArrayList<IngredientCard> {
        return arrayListOf(
                IngredientCard("Queso Parmesano"),
                IngredientCard("Cilantro"),
                IngredientCard("Aceite de oliva"),
                IngredientCard("Queso Cheddar"),
                IngredientCard("Ajo"),
                IngredientCard("Pollo")
        )
    }

    private fun setupRecipeCardRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvSimilares.layoutManager = layout
        arrRecipeCard = getHomeRecipe()
        val adaptador = RecipeCardAdapter(arrRecipeCard)
        binding.rvSimilares.adapter = adaptador
        adaptador.listener = this
    }

    private fun getHomeRecipe(): ArrayList<RecipeCard> {
        return arrayListOf(
            RecipeCard("Pasta arrabiata", "italiana", "https://shorturl.at/oFLOX", "15min"),
            RecipeCard("Pizza napolitana", "italiana", "https://shorturl.at/jyU27", "35min"),
            RecipeCard("Gelato", "italiana", "https://shorturl.at/uFKNO", "45min")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ClickListener){
            listener = context
        }else{
            throw ClassCastException("${context.toString()} should be recipe detail Clicklistener")
        }
    }

    override fun clicked(position: Int) {
        print("Jajas")
    }

}