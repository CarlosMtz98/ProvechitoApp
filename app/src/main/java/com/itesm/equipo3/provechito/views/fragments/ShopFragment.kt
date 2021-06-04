package com.itesm.equipo3.provechito.views.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity.END
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.views.listeners.ShopListener
import com.itesm.equipo3.provechito.databinding.AddItemBinding
import com.itesm.equipo3.provechito.databinding.FragmentShopBinding
import com.itesm.equipo3.provechito.interfaces.IProduct
import com.itesm.equipo3.provechito.pojo.Products.Product
import com.itesm.equipo3.provechito.pojo.Products.ProductListResponse
import com.itesm.equipo3.provechito.views.components.slideLinearLayout.CustomViewModel
import com.itesm.equipo3.provechito.views.adapters.ProductCardAdapter
import com.itesm.equipo3.provechito.presenters.ProductPresenter
import java.util.*


class ShopFragment : Fragment(), ShopListener, IProduct.View {
    private var _binding: FragmentShopBinding? = null
    private var _bindingIngredient: AddItemBinding? = null
    private val binding get() = _binding!!
    private val productPresenter = ProductPresenter(this)
    private var productsList = ArrayList<Product>()

    companion object {
        private val TAG : String = ShopFragment::class.java.getSimpleName()
        fun newIntent(context: Context) : Intent {
            val intent : Intent = Intent(context, ShopFragment::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    private fun setupProductsRV() {
        val layout = GridLayoutManager(requireContext(), 1)
        binding.rvProducts.layoutManager = layout
        val adapter = ProductCardAdapter(productsList)
        binding.rvProducts.adapter = adapter
        adapter.listener = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _bindingIngredient = null
    }

    override fun onCreateView(
            iinflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        context?.let { productPresenter.getProducts(it) }
        _binding = FragmentShopBinding.inflate(layoutInflater, container, false)
        _bindingIngredient = AddItemBinding.inflate(layoutInflater, container, false)
        binding.btnAddItem.setOnClickListener {
            addItem()
        }
        return binding.root
    }

    private fun addItem() {
        val inflater = LayoutInflater.from(this.context)
        val v = inflater.inflate(R.layout.add_item, null, false)
        val addDialog = AlertDialog.Builder(this.context)

        val ingredientName = v.findViewById<EditText>(R.id.tvIngredientName)
        val ingredientDescription = v.findViewById<EditText>(R.id.tvIngredientDescription)

        addDialog.setView(v)
        addDialog.setTitle("Ingrediente")
        addDialog.setPositiveButton("Agregar"){ dialog, _->
            val newProduct = Product(ingredientName.text.toString(), ingredientDescription.text.toString())
            context?.let { productPresenter.addProduct(it, newProduct) }
            Toast.makeText(this.context, "Ingrediente agregado", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancelar"){ dialog, _ ->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()
    }

    override fun ingredientClicked(position: Int) {
        var ingredient = productsList[position]
        val popup = PopupMenu(this.context, binding.rvProducts[position], END)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.popup_shopping, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_edit -> {
                    val v = LayoutInflater.from(this.context).inflate(R.layout.add_item, null, false)
                    var name = v.findViewById<EditText>(R.id.tvIngredientName)
                    var description = v.findViewById<EditText>(R.id.tvIngredientDescription)
                    name.setText(ingredient.name)
                    description.setText(ingredient.description)
                    AlertDialog.Builder(this.context)
                            .setTitle("Ingrediente")
                            .setView(v)
                            .setPositiveButton("Aceptar") { dialog, _ ->
                                ingredient.name = name.text.toString()
                                ingredient.description = description.text.toString()
                                context?.let { productPresenter.updateProduct(it, ingredient) }
                                Toast.makeText(this.context, "Ingrediente Actualizado", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancelar") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    true
                }
                R.id.item_delete -> {
                    AlertDialog.Builder(this.context)
                            .setTitle("Borrar")
                            .setMessage("¿Quieres borrar este ingrediente?")
                            .setPositiveButton("Aceptar") { dialog, _ ->
                                context?.let { productPresenter.removeProduct(it, ingredient) }
                                productsList.removeAt(position)
                                Toast.makeText(this.context, "Ingrediente eliminado", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancelar") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    true
                }
            }
            true
        }

        popup.show()
    }

    override fun onClickLeft(item: CustomViewModel, position: Int) {
        println("Pico a la izquierda $position")
    }

    override fun onClickRight(item: CustomViewModel, position: Int) {
        println("Pico a la derecha $position")
    }

    override fun showProducts(productsList: ProductListResponse) {
        productsList.products?.let {
            this.productsList.addAll(it)
            setupProductsRV()
        }
    }

    override fun showNewProduct(product: Product) {
        productsList.add(product)
        reloadProducts()
    }

    override fun reloadProducts() {
        val adapter = ProductCardAdapter(productsList)
        binding.rvProducts.adapter = adapter
        adapter.listener = this
    }
}