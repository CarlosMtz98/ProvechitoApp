package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
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
import com.google.gson.Gson
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.views.adapters.IngredientCardAdapter
import com.itesm.equipo3.provechito.views.adapters.RecipeCardAdapter
import com.itesm.equipo3.provechito.views.adapters.StepCardAdapter
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentRecipeDetailBinding
import com.itesm.equipo3.provechito.interfaces.RecipeInterface
import com.itesm.equipo3.provechito.models.IngredientCard
import com.itesm.equipo3.provechito.models.StepCard
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.pojo.Recipe.RecipeListResponse
import com.itesm.equipo3.provechito.presenters.RecipePresenter
import kotlin.properties.Delegates

class RecipeDetailFragment : Fragment(),  RecipeInterface.View, ClickListener {

    private var _binding: FragmentRecipeDetailBinding? = null
    private var presenter = RecipePresenter(this)
    private val binding get() = _binding!!
    private var recommendedRecipesList = ArrayList<Recipe>()
    private var arrIngredients = ArrayList<IngredientCard>()
    private lateinit var arrSteps: ArrayList<StepCard>
    private lateinit var listener: HomeClickListener
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
            val recipeData = bundle.getSerializable("recipeData") as Recipe
            arrIngredients = ArrayList<IngredientCard>()
            if (!recipeData.id.isNullOrEmpty()) {
                context?.let {
                    presenter.getRecipe(it, recipeData.id!!)
                    presenter.getRecipes(it, 2)
                }
            } else {
                // Todo: handle error where there is no id from the previews fragemnt
            }
        }

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

    private fun setupRecipeDetails(recipe: Recipe) {
        binding.recipeTitleTV.text = recipe.name
        binding.categoryBtn.text = recipe.categories?.firstOrNull()?.name ?: "Sin categoría"
        setImage(recipe.imageUrls?.firstOrNull() ?: "")
/*        AndroidNetworking.get(recipe.imageUrls?.firstOrNull() ?: "")
                .build()
                .getAsBitmap(object : BitmapRequestListener {
                    override fun onResponse(response: Bitmap?) {
                        binding.tvBgImg.setImageBitmap(response)
                    }

                    override fun onError(anError: ANError?) {
                        println(anError?.message.toString())
                    }
                })*/

    }

    fun setImage(imgUri: String) {
        AndroidNetworking.get(imgUri)
            .build()
            .getAsBitmap(object: BitmapRequestListener {
                override fun onResponse(response: Bitmap?) {
                    binding.recipeHeaderImageView.setImageBitmap(response)
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

    private fun setupRecommendedRecipeRV(recipeList: ArrayList<Recipe>) {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvSimilares.layoutManager = layout
        val adaptador = RecipeCardAdapter(recipeList)
        binding.rvSimilares.adapter = adaptador
        adaptador.listener = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun recipeClicked(tarjeta: com.itesm.equipo3.provechito.pojo.Recipe.Recipe) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }

    override fun showRecipe(recipe: Recipe) {
        setupRecipeDetails(recipe)
    }

    override fun showRecipes(recipeResponseList: RecipeListResponse, type: Int) {
        Log.i("recipes list", " ${Gson().toJson(recipeResponseList)}")
        when (type) {
            2 -> {
                recipeResponseList.recipes?.let {
                    recommendedRecipesList.addAll(it)
                    setupRecommendedRecipeRV(recommendedRecipesList)
                }
            }
        }
    }
}