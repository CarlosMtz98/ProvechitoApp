package com.itesm.equipo3.provechito.controllers.fragments

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
import com.google.android.gms.tasks.Tasks.await
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.RecipeListResponse
import com.itesm.equipo3.provechito.controllers.adapters.IngredientCardAdapter
import com.itesm.equipo3.provechito.controllers.adapters.RecipeCardAdapter
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.controllers.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentRecipeDetailBinding
import com.itesm.equipo3.provechito.models.IngredientCard
import com.itesm.equipo3.provechito.models.Recipe
import com.itesm.equipo3.provechito.models.RecipeCard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeDetailFragment : Fragment(), ClickListener {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecipeCard: ArrayList<RecipeCard>
    private lateinit var arrIngredients: ArrayList<IngredientCard>
    private lateinit var listener: HomeClickListener
    private lateinit var recipe: Recipe
    private lateinit var apiClient: ApiClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
            apiClient = ApiClient()
        } else {
            throw ClassCastException("$context must implement HomeClickListner.")
        }
    }

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

        val bundle = this.arguments
        if (bundle != null) {
            val recipeCard = bundle.getSerializable("recipeData") as RecipeCard
            arrIngredients = ArrayList<IngredientCard>()
            if (recipeCard.id.isNotEmpty()) {
                println(recipeCard.id)
                apiClient.getApiService(this.context!!).getRecipe(recipeCard.id)
                        .enqueue(object : Callback<Recipe> {
                            override fun onFailure(call: Call<Recipe>, t: Throwable) {
                                //@TODO throw message that it could not fetch the data
                                println("Fail")
                            }

                            override fun onResponse(call: Call<Recipe>, response: Response<Recipe>) {
                                val recipeData = response.body()
                                println(recipeData.toString())
                                if (!response.isSuccessful || recipeData == null) {
                                    // @TODO add alert that the request did not work
                                    println("Empty recipe details")
                                } else {
                                    recipe = recipeData!!
                                    for (ingredient: String? in recipe.ingredients) {
                                        if (!ingredient.isNullOrEmpty()) {
                                            arrIngredients.add(IngredientCard(ingredient))
                                        }
                                    }
                                    setupRecipeDetails()
                                    setupIngredientRV(arrIngredients)
                                }
                            }
                        })
            }
        }

        setupRecipeCardRV()

        return binding.root
    }

    private fun setupRecipeDetails() {
        binding.tvRecipe.text = recipe.name
        binding.btnCategory.text = recipe.categories.firstOrNull()?.name ?: "Sin categoría"
        AndroidNetworking.get(recipe.imageUrls.firstOrNull() ?: "" )
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

    private fun setupIngredientRV(recipeCardList: ArrayList<IngredientCard>) {
        val layout = GridLayoutManager(requireContext(), 1)
        layout.orientation = GridLayoutManager.VERTICAL
        binding.rvIngredientsRecipe.layoutManager = layout

        val ingredientCardAdapter = IngredientCardAdapter(recipeCardList)
        binding.rvIngredientsRecipe.adapter = ingredientCardAdapter
        ingredientCardAdapter.listener = this
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
        //listener.onRecipeCardClicked(recipeCard.name, recipeCard.category, recipeCard.imgUri)
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }

    private fun fetchRecipeDetails(id: String) {

    }

}