package com.itesm.equipo3.provechito.controllers.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
//import com.google.android.gms.tasks.Tasks.await
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.RecipeListResponse
import com.itesm.equipo3.provechito.controllers.adapters.IngredientCardAdapter
import com.itesm.equipo3.provechito.controllers.adapters.RecipeCardAdapter
import com.itesm.equipo3.provechito.controllers.adapters.StepCardAdapter
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.controllers.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentRecipeDetailBinding
import com.itesm.equipo3.provechito.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class RecipeDetailFragment : Fragment(), ClickListener {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecipeCard: ArrayList<RecipeCard>
    private lateinit var arrIngredients: ArrayList<IngredientCard>
    private lateinit var arrSteps: ArrayList<StepCard>
    private lateinit var listener: HomeClickListener
    private lateinit var recipe: Recipe
    private lateinit var apiClient: ApiClient
    private lateinit var chronometer: Chronometer
    private var running = false
    private var pauseOffSet by Delegates.notNull<Long>()

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

        val bundle = this.arguments
        if (bundle != null) {
            val recipeCard = bundle.getSerializable("recipeData") as RecipeCard
            arrIngredients = ArrayList<IngredientCard>()
            arrSteps = ArrayList<StepCard>()
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
                                    var nSteps = 1
                                    for(step:String? in recipe.preparationSteps){
                                        //println(step)
                                        //println(nSteps)
                                        //println("FIN DEL PASO")
                                        if(!step.isNullOrEmpty()){
                                            arrSteps.add(StepCard(nSteps, step))
                                            //println(arrSteps[nSteps].description)
                                            nSteps += 1
                                        }
                                    }
                                    setupRecipeDetails()
                                    setupStepRV(arrSteps)
                                    setupIngredientRV(arrIngredients)
                                }
                                setupRecipeDetails()
                                setupIngredientRV(arrIngredients)
                            }
                        }
                    })
            } else {
                recipe = Recipe(recipeCard.name, recipeCard.category, recipeCard.imgUri)
                arrIngredients = arrayListOf(IngredientCard("N/A"))
                setupRecipeDetails()
            }
        }

        setupRecipeCardRV()

        pauseOffSet = 0
        chronometer = binding.chronometer
        chronometer.text = "00:00:00"
        chronometer.setOnChronometerTickListener{
            val time = SystemClock.elapsedRealtime() - chronometer.base
            val h = (time / 3600000).toInt()
            val m = (time - h * 3600000).toInt() / 60000
            val s = (time - h * 3600000 - m * 60000).toInt() / 1000
            val t = (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s
            chronometer.text = t

        }

        binding.imgButtonStart.setOnClickListener {
            startChronometer()
        }
        binding.imgButtonPause.setOnClickListener {
            pauseChronometer()
        }
        binding.imgButtonReset.setOnClickListener {
            resetChronometer()
        }
        return binding.root

        return binding.root
    }

    private fun startChronometer(){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet)
            chronometer.start()
            running = true
        }
    }

    private fun pauseChronometer(){
        if(running){
            chronometer.stop()
            pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase()
            running = false
        }
    }

    private fun resetChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime())
        pauseOffSet = 0
        chronometer.text = "00:00:00"
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

    private fun setupStepRV(stepCardList: ArrayList<StepCard>) {
        val layout = GridLayoutManager(requireContext(), 1)
        layout.orientation = GridLayoutManager.VERTICAL
        binding.rvStepsRecipe.layoutManager = layout

        val stepCardAdapter = StepCardAdapter(stepCardList)
        binding.rvStepsRecipe.adapter = stepCardAdapter
        stepCardAdapter.listener = this
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

    override fun recipeClicked(tarjeta: RecipeCard) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }

}