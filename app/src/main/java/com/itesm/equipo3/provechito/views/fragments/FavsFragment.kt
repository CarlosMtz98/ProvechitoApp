package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.RequestObjects.LikeRequest
import com.itesm.equipo3.provechito.api.ResponseObjects.DeleteResponse
import com.itesm.equipo3.provechito.api.ResponseObjects.LikesListResponse
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentFavsBinding
import com.itesm.equipo3.provechito.models.RecipeCard
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.views.adapters.RecipeCardFullAdapter
import com.itesm.equipo3.provechito.views.listeners.LikeClickListener
import com.itesm.equipo3.provechito.models.Like
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavsFragment : Fragment(), ClickListener, LikeClickListener {
    private var _binding: FragmentFavsBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecipeCard: ArrayList<RecipeCard>
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

        arrRecipeCard = ArrayList();
        apiClient.getApiService(this.context!!).getLikes()
                .enqueue(object : Callback<LikesListResponse> {
                    override fun onFailure(call: Call<LikesListResponse>, t: Throwable) {
                        // Error fetching posts
                        //@TODO throw message that it could not fetch the data
                    }

                    override fun onResponse(call: Call<LikesListResponse>, response: Response<LikesListResponse>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful && serviceResponse?.likes != null) {
                            for (like in serviceResponse.likes) {
                                if (like.recipe == null)
                                    continue
                                arrRecipeCard.add(RecipeCard(
                                        id = like.recipe._id!! ,
                                        name = like.recipe.name!!,
                                        imgUri = like.recipe.thumbnailUrl!!,
                                        category = like.recipe.categories.firstOrNull()?.name ?: "Sin categría",
                                        duration = like.recipe.duration.toString() + " min"
                                ))
                            }
                        } else {
                            // @TODO add alert that the request did not work
                            println("Failed response category: ${response.message()}")
                        }

                        setupRvFavsRecipe(arrRecipeCard)
                    }
                })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRvFavsRecipe(recipeCardList: ArrayList<RecipeCard>) {
        val gridLayout = GridLayoutManager(requireContext(), 1)
        gridLayout.orientation = GridLayoutManager.VERTICAL

        binding.rvFavsRecipeCard.layoutManager = gridLayout

        recipeCardList.forEach {
            it.liked = true
        }
        val adaptador = RecipeCardFullAdapter(recipeCardList)
        binding.rvFavsRecipeCard.adapter = adaptador

        adaptador.listener = this
        adaptador.likeListener = this
    }

    override fun recipeClicked(tarjeta: RecipeCard) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }

    override fun likeOnClick(recipeId: String) {
        println("Clicked like")
        apiClient.getApiService(this.context!!).addLike(LikeRequest(recipeId))
                .enqueue(object : Callback<Like> {
                    override fun onFailure(call: Call<Like>, t: Throwable) {
                        //@TODO throw message that it could not fetch the data
                    }

                    override fun onResponse(call: Call<Like>, response: Response<Like>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful && serviceResponse != null) {
                            arrRecipeCard.add(RecipeCard(
                                    id = serviceResponse.recipe._id!! ,
                                    name = serviceResponse.recipe.name!!,
                                    imgUri = serviceResponse.recipe.thumbnailUrl!!,
                                    category = serviceResponse.recipe.categories.firstOrNull()?.name ?: "Sin categría",
                                    duration = serviceResponse.recipe.duration.toString() + " min"
                            ))
                        } else {
                            // @TODO add alert that the request did not work
                            println("Failed response category: ${response.message()}")
                        }
                        setupRvFavsRecipe(arrRecipeCard)
                    }
                })
    }

    override fun unlikeOnClick(recipeId: String) {
        println("Clicked unlike")
        apiClient.getApiService(this.context!!).removeLike(recipeId)
                .enqueue(object : Callback<DeleteResponse> {
                    override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                        // Error fetching posts
                        //@TODO throw message that it could not fetch the data
                    }

                    override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful) {

                        } else {
                            // @TODO add alert that the request did not work
                            println("Failed response category: ${response.message()}")
                        }

                    }
                })
    }
}