package com.itesm.equipo3.provechito.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentShopBinding
import com.itesm.equipo3.provechito.models.ProductCard
import kotlin.collections.ArrayList

class ShopFragment : Fragment(), ClickListener, CustomListeners {
    private lateinit var arrProducts: ArrayList<ProductCard>
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
        binding.rvProducts.layoutManager = layout
        arrProducts = createProductArr()
        val adapter = ProductCardAdapter(arrProducts)
        binding.rvProducts.adapter = adapter
        adapter.listener = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun createProductArr(): ArrayList<ProductCard> {
        return arrayListOf(
            ProductCard("Queso", 1, "Lácteo", "Viernes 16 de abril"),
            ProductCard("Cilantro", 2, "Verdura", "Viernes 16 de abril"),
            ProductCard("Ajo", 3, "Verdura", "Viernes 16 de abril"),
            ProductCard("Jitomate", 4, "Verdura", "Viernes 16 de abril"),
            ProductCard("Cebolla", 5, "Verdura", "Viernes 16 de abril")
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
        val ingredient = arrProducts[posicion]
        println("posicion: ${ingredient}")
    }

    override fun onClickLeft(item: CustomViewModel, position: Int) {
        println("Pico a la izquierda $position")
    }

    override fun onClickRight(item: CustomViewModel, position: Int) {
        println("Pico a la derecha $position")
    }
}