package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.DeleteResponse
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentFavsBinding
import com.itesm.equipo3.provechito.interfaces.ILike
import com.itesm.equipo3.provechito.models.StatisticsCard
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Like.Like
import com.itesm.equipo3.provechito.pojo.Products.Product
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.views.adapters.RecipeCardFullAdapter
import com.itesm.equipo3.provechito.views.listeners.LikeClickListener
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.presenters.LikePresenter
import com.itesm.equipo3.provechito.views.adapters.ProductCardAdapter
import com.itesm.equipo3.provechito.views.adapters.StatisticsCardAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavsFragment : Fragment(), ClickListener, LikeClickListener, ILike.View {
    private var likePresenter = LikePresenter(this)
    private var arrRecipeCard = ArrayList<Recipe>()
    private var _binding: FragmentFavsBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: HomeClickListener
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavsBinding.inflate(inflater, container, false)

        context?.let { likePresenter.getLikes(it) }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLikesRV(recipeCardList: ArrayList<Recipe>) {
        val gridLayout = GridLayoutManager(requireContext(), 1)
        gridLayout.orientation = GridLayoutManager.VERTICAL

        binding.rvFavsRecipeCard.layoutManager = gridLayout

        recipeCardList.forEach {
            it.hasUserLike = true
        }

        if (recipeCardList.size > 0) {
            val likeRecipeAdapter = RecipeCardFullAdapter(recipeCardList)
            binding.rvFavsRecipeCard.adapter = likeRecipeAdapter
            likeRecipeAdapter.listener = this
            likeRecipeAdapter.likeListener = this
        } else {
            val products = ArrayList<StatisticsCard>()
            products.add(StatisticsCard("No likes :(", "Go like some things and come back"))
            binding.rvFavsRecipeCard.adapter = StatisticsCardAdapter(products)
        }
    }

    override fun recipeClicked(recipe: Recipe) {
        listener.onRecipeCardClicked(recipe)
    }

    override fun categoryClicked(category: Category) {
        throw NotImplementedError()
    }

    override fun likeOnClick(recipeId: String) {
        println("Clicked like")
    }

    override fun unlikeOnClick(recipeId: String) {
        println("Clicked unlike")
        apiClient.getApiService(this.requireContext()).removeLike(recipeId)
                .enqueue(object : Callback<DeleteResponse> {
                    override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                        // Error fetching posts
                        //@TODO throw message that it could not fetch the data
                    }

                    override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful) {
                            println(serviceResponse)
                        } else {
                            // @TODO add alert that the request did not work
                            println("Failed response category: ${response.message()}")
                        }

                    }
                })
    }

    override fun showLikes(likedRecipes: ArrayList<Recipe>) {
        setupLikesRV(likedRecipes)
    }
}