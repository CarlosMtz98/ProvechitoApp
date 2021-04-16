package com.itesm.equipo3.provechito.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentShopBinding
import com.itesm.equipo3.provechito.models.Ingredient
import java.util.*
import kotlin.collections.ArrayList

class ShopFragment : Fragment(), ClickListener {
    private lateinit var arrIngredients: ArrayList<Ingredient>
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun configurarRV() {
        val layout = GridLayoutManager(requireContext(), 1)
        binding.rvIngredients.layoutManager = layout
        arrIngredients = createIngredientArr()
        val adapter = IngredientCardAdapter(arrIngredients)
        binding.rvIngredients.adapter = adapter
        adapter.listener = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun createIngredientArr(): ArrayList<Ingredient> {
        return arrayListOf(
            Ingredient("Queso", 1, "Lácteo", Calendar.getInstance().time.toString()),
            Ingredient("Cilantro", 2, "Verdura", Calendar.getInstance().time.toString()),
            Ingredient("Ajo", 3, "Verdura", Calendar.getInstance().time.toString()),
            Ingredient("Jitomate", 4, "Verdura", Calendar.getInstance().time.toString()),
            Ingredient("Cebolla", 5, "Verdura", Calendar.getInstance().time.toString())
        )
    }

    override fun onCreateView(
        iinflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopBinding.inflate(layoutInflater, container, false)
        configurarRV()
        return binding.root
    }

    override fun clicked(posicion: Int) {
        val ingredient = arrIngredients[posicion]
        println("posicion: ${ingredient}")
    }
}