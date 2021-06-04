package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.DeleteResponse
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentFavsBinding
import com.itesm.equipo3.provechito.interfaces.ILike
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Like.Like
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.views.adapters.RecipeCardFullAdapter
import com.itesm.equipo3.provechito.views.listeners.LikeClickListener
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.presenters.LikePresenter
import com.itesm.equipo3.provechito.views.adapters.ProductCardAdapter
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

        recipeCardList.forEach { it.hasUserLike = true }

        val likeRecipeAdapter = RecipeCardFullAdapter(recipeCardList)
        binding.rvFavsRecipeCard.adapter = likeRecipeAdapter

        likeRecipeAdapter.listener = this
        likeRecipeAdapter.likeListener = this
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

    override fun unlikeOnClick(recipeId: String, index: Int) {
        context?.let { likePresenter.removeLike(it, recipeId, index) }
    }

    override fun showLikes(likedRecipes: ArrayList<Recipe>) {
        arrRecipeCard.addAll(likedRecipes)
        setupLikesRV(arrRecipeCard)
    }

    override fun removeLike(index: Int) {
        arrRecipeCard.removeAt(index)
        Toast.makeText(this.context, "Se eliminó el like de tú lista de favoritos", Toast.LENGTH_LONG).show()
        reloadLikesList()
    }

    override fun likeAdded(recipe: Recipe) {
        arrRecipeCard.add(recipe)
        reloadLikesList()
    }


    override fun reloadLikesList() {
        val adapter = RecipeCardFullAdapter(arrRecipeCard)
        binding.rvFavsRecipeCard.adapter = adapter
        adapter.listener = this
    }
}