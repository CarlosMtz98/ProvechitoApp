package com.itesm.equipo3.provechito.views.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.itesm.equipo3.provechito.views.listeners.ClickListener
import com.itesm.equipo3.provechito.databinding.FragmentRecentRecipesBinding
import com.itesm.equipo3.provechito.pojo.Recipe.Recipe
import com.itesm.equipo3.provechito.views.adapters.RecipeCardFullAdapter
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener

class RecentRecipesFragment : Fragment(), ClickListener {
    private lateinit var listener: HomeClickListener
    private var _binding: FragmentRecentRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrRecentRecipeCard: ArrayList<Recipe>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement SignUpListener.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecentRecipesBinding.inflate(inflater, container, false)
        arrRecentRecipeCard = arguments!!.getSerializable("arrRecent") as ArrayList<Recipe>
        setupRVRecentRecipes()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRVRecentRecipes() {
        val layout = GridLayoutManager(requireContext(), 1)
        layout.orientation = GridLayoutManager.VERTICAL
        binding.rvRecentRecipes.layoutManager = layout

        val adaptador = RecipeCardFullAdapter(arrRecentRecipeCard)
        binding.rvRecentRecipes.adapter = adaptador

        adaptador.listener = this
    }

    companion object {
        fun newInstance() : RecentRecipesFragment {
            return RecentRecipesFragment()
        }
    }

    override fun recipeClicked(tarjeta: Recipe) {
        listener.onRecipeCardClicked(tarjeta)
    }

    override fun categoryClicked(position: Int) {
        println("Clicked $position")
    }
}