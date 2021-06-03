package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.CategoryListResponse
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentCategoriesBinding
import com.itesm.equipo3.provechito.models.CategoryCard
import com.itesm.equipo3.provechito.views.adapters.CategorySectionCardAdapter
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.models.RecipeCard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoriesFragment : Fragment(), ClickListener {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrCategories: ArrayList<CategoryCard>
    private lateinit var listener: HomeClickListener
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
        arrCategories = ArrayList()
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)

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
                                arrCategories.add(CategoryCard(category.name!!, category.thumbnailUrl!!))
                            }
                        } else {
                            // @TODO add alert that the request did not work
                            println("Failed response category: ${response.message()}")
                        }
                        setupRVCategories(arrCategories)
                    }

                })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRVCategories(categoriesList: ArrayList<CategoryCard>) {
        val layout = GridLayoutManager(requireContext(),2)
        layout.orientation = GridLayoutManager.VERTICAL
        binding.rvCategoryCards.layoutManager = layout
        val adaptador = CategorySectionCardAdapter(categoriesList)
        binding.rvCategoryCards.adapter = adaptador

        adaptador.listener = this
    }


    companion object {
        fun newInstance() : CategoriesFragment {
            return CategoriesFragment()
        }
    }

    override fun recipeClicked(tarjeta: RecipeCard) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(position: Int) {
        val recipeCard = arrCategories[position]
        println("posicion: $recipeCard")
        listener.onCategoryCardClicked(arrCategories[position].name)
    }
}