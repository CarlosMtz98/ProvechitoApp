package com.itesm.equipo3.provechito.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentSearchBinding
import com.itesm.equipo3.provechito.models.CategoryCard


class SearchFragment : Fragment(), ClickListener {

    private lateinit var listener: HomeClickListener
    private lateinit var arrCategoryCardShop: ArrayList<CategoryCard>

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement HomeClickListner.")
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        configureRVCategoryShop()

        binding.arrowSearchImgButton.setOnClickListener{
            val categoriesFragment = CategoriesFragment()
            listener.onCategoryCardClicked()
        }

        return binding.root
    }

    private fun configureRVCategoryShop() {
        val layout = GridLayoutManager(requireContext(), 2)
        binding.rvCategoryCardsSearch.layoutManager = layout

        arrCategoryCardShop = getCategory()
        val adaptador = CategoryCardAdapter(arrCategoryCardShop)
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


    override fun recipeClicked(position: Int) {
        println("Clicked $position")
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }
}