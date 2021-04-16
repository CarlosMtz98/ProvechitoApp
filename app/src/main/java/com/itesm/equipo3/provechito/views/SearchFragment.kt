package com.itesm.equipo3.provechito.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.databinding.FragmentSearchBinding
import com.itesm.equipo3.provechito.models.CategoryCard


class SearchFragment : Fragment(), ClickListener {

    private lateinit var listener: HomeClcikListener
    private lateinit var arrCategoryCardShop: ArrayList<CategoryCard>

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClcikListener) {
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
            listener.onCategoryClicked()
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
                CategoryCard("Comida Mexicana",""),
                CategoryCard("Comida Italiana",""),
                CategoryCard("Comida Rusa",""),
                CategoryCard("Comida Francesa","")
        )
    }

    override fun clicked(posicion: Int) {
        TODO("Not yet implemented")
    }
}