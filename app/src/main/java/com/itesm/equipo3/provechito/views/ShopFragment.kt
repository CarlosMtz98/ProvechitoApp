package com.itesm.equipo3.provechito.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentShopBinding
import com.itesm.equipo3.provechito.models.Ingredient
import java.util.*
import kotlin.collections.ArrayList

class ShopFragment : Fragment(), ClickListener, CustomListeners {
    private lateinit var arrIngredients: ArrayList<Ingredient>
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val TAG : String = ShopFragment::class.java.getSimpleName()
        fun newIntent(context : Context) : Intent {
            val intent : Intent = Intent(context, ShopFragment::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }

    private lateinit var adapter : CustomAdapter
    private lateinit var itemList : MutableList<CustomViewModel>

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

    override fun onStart() {
        super.onStart()
    }

    override fun clicked(posicion: Int) {
        val ingredient = arrIngredients[posicion]
        println("posicion: ${ingredient}")
    }

    override fun onClickLeft(item: CustomViewModel, position: Int) {
        println("Pico a la izquierda $position")
    }

    override fun onClickRight(item: CustomViewModel, position: Int) {
        println("Pico a la derecha $position")
    }
}