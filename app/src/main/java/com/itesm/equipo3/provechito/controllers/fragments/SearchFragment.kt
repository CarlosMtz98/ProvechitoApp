package com.itesm.equipo3.provechito.controllers.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.CategoryListResponse
import com.itesm.equipo3.provechito.controllers.listeners.ClickListener
import com.itesm.equipo3.provechito.controllers.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentSearchBinding
import com.itesm.equipo3.provechito.models.CategoryCard
import com.itesm.equipo3.provechito.controllers.adapters.CategorySectionCardAdapter
import com.itesm.equipo3.provechito.models.RecipeCard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(), ClickListener {

    private lateinit var listener: HomeClickListener
    private lateinit var arrCategoriesList: ArrayList<CategoryCard>

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiClient: ApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
            apiClient = ApiClient()
        } else {
            throw ClassCastException("$context must implement HomeClickListner.")
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        arrCategoriesList = ArrayList()
        apiClient.getApiService(this.context!!).getCategories()
                .enqueue(object : Callback<CategoryListResponse> {
                    override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                        // Error fetching posts
                        //@TODO throw message that it could not fetch the data
                    }

                    override fun onResponse(call: Call<CategoryListResponse>, response: Response<CategoryListResponse>) {
                        val categoryResponse = response.body()
                        if (response.isSuccessful && categoryResponse?.categories != null) {
                            for (category in categoryResponse.categories) {
                                arrCategoriesList.add(CategoryCard(category.name!!, category.thumbnailUrl!!))
                            }
                        } else {
                            // @TODO add alert that the request did not work
                            println("Failed response category: ${response.message()}")
                        }
                        configureRVCategoryShop(arrCategoriesList)
                    }

                })

        return binding.root
    }

    private fun configureRVCategoryShop(categoryCardList: ArrayList<CategoryCard>) {
        val layout = GridLayoutManager(requireContext(), 2)
        binding.rvCategoryCardsSearch.layoutManager = layout

        val adaptador = CategorySectionCardAdapter(categoryCardList)
        binding.rvCategoryCardsSearch.adapter = adaptador

        adaptador.listener = this
    }

    private fun getCategory(): ArrayList<CategoryCard> {
        return arrayListOf(
            CategoryCard("Italiana", "https://images.unsplash.com/photo-1551183053-bf91a1d81141?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80"),
            CategoryCard("Mexicana", "https://images.unsplash.com/photo-1582234372722-50d7ccc30ebd?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80"),
            CategoryCard("Argentina", "https://images.unsplash.com/photo-1544025162-d76694265947?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=640&q=80"),
            CategoryCard("Tailandesa", "https://images.unsplash.com/photo-1569562211093-4ed0d0758f12?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80")
        )
    }

    override fun recipeClicked(tarjeta: RecipeCard) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(position: Int) {
        val recipeCard = arrCategoriesList[position]
        println("posicion: $recipeCard")
        listener.onCategoryCardClicked(arrCategoriesList[position].name)
    }
}