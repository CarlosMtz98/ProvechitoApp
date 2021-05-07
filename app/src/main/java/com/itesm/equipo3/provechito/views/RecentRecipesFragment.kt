package com.itesm.equipo3.provechito.views


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.databinding.FragmentHomeBinding
import com.itesm.equipo3.provechito.databinding.FragmentRecentRecipesBinding
import com.itesm.equipo3.provechito.models.RecentRecipeCard

class RecentRecipesFragment : Fragment(), ClickListener {
    private var _binding: FragmentRecentRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecentRecipeCard: ArrayList<RecentRecipeCard>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecentRecipesBinding.inflate(inflater, container, false)
        setupRVRecentRecipes()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRVRecentRecipes(){

        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvRecentRecipes.layoutManager = layout

        arrRecentRecipeCard = getRecentRecipes();
        val adaptador = RecentRecipeCardAdapter(arrRecentRecipeCard)
        binding.rvRecentRecipes.adapter = adaptador

        adaptador.listener = this
    }

    private fun getRecentRecipes(): ArrayList<RecentRecipeCard> {
        return arrayListOf(
                RecentRecipeCard("Pastel de chocolate", "Repostería", "https://images.unsplash.com/photo-1614786482494-7fc57abd0074?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "Fácil"),
                RecentRecipeCard("Enchiladas verdes", "mexicana", "https://cdn.kiwilimon.com/recetaimagen/26245/38984.jpg", "Medio"),
                RecentRecipeCard("Pizza napolitana", "italiana", "https://images.unsplash.com/photo-1589187151053-5ec8818e661b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "Difícil")
        )
    }

    //por que esta esto?
    companion object {
        fun newInstance() : RecentRecipesFragment {
            return RecentRecipesFragment()
        }
    }

    override fun clicked(posicion: Int) {
        println("Clicked $posicion")
    }
}