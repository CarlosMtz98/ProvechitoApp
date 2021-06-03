package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.RecipeListResponse
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentProfileBinding
import com.itesm.equipo3.provechito.models.RecipeCard
import com.itesm.equipo3.provechito.models.StatisticsCard
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.views.adapters.RecipeCardAdapter
import com.itesm.equipo3.provechito.views.adapters.StatisticsCardAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
    Autor: Zoe Caballero
 */
class ProfileFragment : Fragment(), ClickListener {
    private lateinit var listener: HomeClickListener
    private lateinit var arrStatisticsCard: ArrayList<StatisticsCard>
    private lateinit var arrLastRecipesCard: ArrayList<RecipeCard>
    private var _binding: FragmentProfileBinding? = null
    private lateinit var apiClient: ApiClient

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        getRecentRecipes()
        configureStadisticsRV()


        binding.imgButtonSettings.setOnClickListener {
            println("Go to recommended")
            listener.onSettingsClicked()
        }

        binding.imageButton3.setOnClickListener {
            val recentRecipesFragment = RecentRecipesFragment()
            listener.onRecentClicked(arrLastRecipesCard)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
            apiClient = ApiClient()
        } else {
            throw ClassCastException("$context must implement SignUpListener.")
        }
    }

    private fun configureLastRecipesRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvLastRecipesCards.layoutManager = layout

        val adaptador = RecipeCardAdapter(arrLastRecipesCard)
        binding.rvLastRecipesCards.adapter = adaptador

        adaptador.listener = this
    }

    private fun getRecentRecipes() {
        val results = ArrayList<RecipeCard>()
        apiClient.getApiService(this.context!!).getRecentRecipes()
                .enqueue(object : Callback<RecipeListResponse> {
                    override fun onFailure(call: Call<RecipeListResponse>, t: Throwable) {
                        // Error fetching posts
                        //@TODO throw message that it could not fetch the data
                    }

                    override fun onResponse(call: Call<RecipeListResponse>, response: Response<RecipeListResponse>) {
                        val recipeListResponse = response.body()
                        if (response.isSuccessful && recipeListResponse?.recipes != null) {
                            for (recipe in recipeListResponse.recipes){
                                val recipeItem = RecipeCard(name = recipe.name!!, category = "Comida internacional", imgUri = recipe.thumbnailUrl!!, duration = "${recipe.duration.toString()} minutos", id = recipe._id!!)
                                results.add(recipeItem)
                            }
                        } else {
                            // @TODO add alert that the request did not work
                        }
                        arrLastRecipesCard = results
                        configureLastRecipesRV()
                    }
                })
    }

    private fun getLastRecipes(): ArrayList<RecipeCard>{
        return arrayListOf(
            RecipeCard("Pasta arrabiata", "italiana", "https://images.unsplash.com/photo-1607375658859-39f31567ce13?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=740&q=80", "15min"),
            RecipeCard("Pizza napolitana", "italiana", "https://images.unsplash.com/photo-1589187151053-5ec8818e661b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "35min"),
            RecipeCard("Enchiladas verdes", "mexicana", "https://cdn.kiwilimon.com/recetaimagen/26245/38984.jpg", "35min"),
            RecipeCard("Gelato", "italiana", "https://images.unsplash.com/photo-1580915411954-282cb1b0d780?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "45min")
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

    override fun recipeClicked(tarjeta: RecipeCard) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }


}