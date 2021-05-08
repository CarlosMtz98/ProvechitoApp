package com.itesm.equipo3.provechito.views

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.itesm.equipo3.provechito.databinding.FragmentRecipeDetailBinding
import com.itesm.equipo3.provechito.models.IngredientCard
import com.itesm.equipo3.provechito.models.RecipeCard

class RecipeDetailFragment : Fragment(), ClickListener{

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecipeCard: ArrayList<RecipeCard>
    private lateinit var arrIngredients: ArrayList<IngredientCard>
    private lateinit var listener: HomeClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        binding.btnBegin.setOnClickListener {
            val detailedStep = FragmentDetailedStep()
            listener.onBeginClicked()
        }
        val name = arguments!!.getString("NAME")
        val category = arguments!!.getString("CAT")
        val imgUri = arguments!!.getString("IMG")
        val duration = arguments!!.getString("DUR")
        val liked = arguments!!.getBoolean("LIK")
        binding.tvRecipe.text = name
        binding.btnCategory.text = category
        if (imgUri != null) {
            setImage(imgUri)
        }
        setupRecipeCardRV()
        setupIngredientRV()
        return binding.root
    }

    fun setImage(imgUri: String) {
        AndroidNetworking.get(imgUri)
            .build()
            .getAsBitmap(object: BitmapRequestListener {
                override fun onResponse(response: Bitmap?) {
                    binding.tvBgImg.setImageBitmap(response)
                }
                override fun onError(anError: ANError?) {
                    println(anError?.message.toString())
                }
            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement HomeClickListner.")
        }
    }

    private fun setupIngredientRV() {
        val layout = GridLayoutManager(requireContext(), 1)
        layout.orientation = GridLayoutManager.VERTICAL
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
            RecipeCard(
                "Pasta arrabiata",
                "italiana",
                "https://images.unsplash.com/photo-1607375658859-39f31567ce13?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=740&q=80",
                "15min"
            ),
            RecipeCard(
                "Pizza napolitana",
                "italiana",
                "https://images.unsplash.com/photo-1589187151053-5ec8818e661b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80",
                "35min"
            ),
            RecipeCard(
                "Gelato",
                "italiana",
                "https://images.unsplash.com/photo-1580915411954-282cb1b0d780?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80",
                "45min"
            )
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
        println("Clicked $position")
    }

}