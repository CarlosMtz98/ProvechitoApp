package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentReviewBinding
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe

class ReviewFragment : Fragment(), ClickListener {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentReviewBinding.inflate(inflater, container, false)

        val bundle = this.arguments
        if (bundle != null) {
            val recipeData = bundle.getSerializable("recipeData") as Recipe
            binding.tvRecipeName.text = recipeData.name
            binding.tvCategorie.text = recipeData.categories?.firstOrNull()?.name ?: "Sin categoría"
        }

        binding.sendReviewButton.setOnClickListener {
            HomeFragment()
            listener.onSendClicked()
        }
        return binding.root
    }

    override fun recipeClicked(tarjeta: Recipe) {
        throw NotImplementedError()
    }

    override fun categoryClicked(category: Category) {
        throw NotImplementedError()
    }
}