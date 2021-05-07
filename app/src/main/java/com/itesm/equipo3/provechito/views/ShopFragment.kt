package com.itesm.equipo3.provechito.views

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Gravity.END
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.databinding.AddItemBinding
import com.itesm.equipo3.provechito.databinding.FragmentShopBinding
import com.itesm.equipo3.provechito.models.ProductCard
import java.text.SimpleDateFormat
import java.util.*


class ShopFragment : Fragment(),ClickListener, CustomListeners {
    private lateinit var arrProducts: ArrayList<ProductCard>
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private var _bindingIngredient:AddItemBinding? = null
    private val bindingAddItem get() = _bindingIngredient!!
    private lateinit var adapter : CustomAdapter
    private lateinit var itemList : MutableList<CustomViewModel>


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
        _bindingIngredient = null

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
        _bindingIngredient = AddItemBinding.inflate(layoutInflater, container, false)
        configurarRV()
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
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)

        addDialog.setView(v)
        addDialog.setTitle("Ingrediente")
        addDialog.setPositiveButton("Agregar"){ dialog, _->
            val name = ingredientName.text.toString()
            val description = ingredientDescription.text.toString()
            arrProducts.add(ProductCard(name, arrProducts.lastIndex + 1, description, formatedDate.toString()))//
            Toast.makeText(this.context, "Ingrediente agregado", Toast.LENGTH_SHORT).show()
            dialog.dismiss()

        }
        addDialog.setNegativeButton("Cancelar"){ dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this.context, "Cancel", Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()


    }

    override fun onStart() {
        super.onStart()
    }

    override fun recipeClicked(position: Int) {
        val ingredient = arrProducts[position]
        println("position: ${ingredient}")
        val popup = PopupMenu(this.context, binding.rvProducts[position], END)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.popup_shopping, popup.menu)
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_edit -> {
                    val v = LayoutInflater.from(this.context).inflate(R.layout.add_item, null, false)
                    val name = v.findViewById<EditText>(R.id.tvIngredientName)
                    val description = v.findViewById<EditText>(R.id.tvIngredientDescription)
                    name.setText(ingredient.name)
                    description.setText(ingredient.description)
                    AlertDialog.Builder(this.context)
                            .setTitle("Ingrediente")
                            .setView(v)
                            .setPositiveButton("Aceptar") { dialog, _ ->
                                ingredient.name = name.text.toString()
                                ingredient.description = description.text.toString()
                                ingredient.dateAdded = formatedDate.toString()
                                val adapter = ProductCardAdapter(arrProducts)
                                binding.rvProducts.adapter = adapter
                                adapter.listener = this
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
                                arrProducts.removeAt(position)
                                val adapter = ProductCardAdapter(arrProducts)
                                binding.rvProducts.adapter = adapter
                                adapter.listener = this
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
        })

        popup.show()
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }

    override fun onClickLeft(item: CustomViewModel, position: Int) {
        println("Pico a la izquierda $position")
    }

    override fun onClickRight(item: CustomViewModel, position: Int) {
        println("Pico a la derecha $position")
    }
}