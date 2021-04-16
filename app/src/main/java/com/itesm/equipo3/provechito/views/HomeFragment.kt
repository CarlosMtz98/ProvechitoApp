package com.itesm.equipo3.provechito.views

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.equipo3.provechito.databinding.FragmentHomeBinding
import com.itesm.equipo3.provechito.models.CategoryCard
import com.itesm.equipo3.provechito.models.RecipeCard


class HomeFragment : Fragment(), ClickListener {
    private lateinit var listener: HomeClcikListener
    private lateinit var arrRecipeCard: ArrayList<RecipeCard>
    private lateinit var arrCategoryCard: ArrayList<CategoryCard>
    private lateinit var arrRecentRecipes: ArrayList<RecipeCard>

    private var _binding: FragmentHomeBinding? = null
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecipeCardRV()
        setupRecentRecipesRV()
        configureCategoryCardRV()

        binding.btnRecomendations.setOnClickListener {
            val recommendedRecipesFragment = RecomendedRecipesFragment()
            println("Go to recommended")
            listener.onRecommendedClicked()
        }

        binding.btnRecentRecipes.setOnClickListener {
            val recentRecipesFragment = RecentRecipesFragment()
            listener.onRecentClicked()
        }

        binding.buttonCategory.setOnClickListener {
            val categoriesFragment = CategoriesFragment()
            listener.onCategoryClicked()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRecipeCardRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL

        binding.rvRecipieCards.layoutManager = layout

        arrRecipeCard = getHomeRecipe()
        val adaptador = RecipeCardAdapter(arrRecipeCard)
        binding.rvRecipieCards.adapter = adaptador

        adaptador.listener = this
    }

    private fun setupRecentRecipesRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL

        binding.rvRecentRecipies.layoutManager = layout

        arrRecentRecipes = getRecentRecipes()
        val adaptador = RecipeCardAdapter(arrRecentRecipes)
        binding.rvRecentRecipies.adapter = adaptador

        adaptador.listener = this
    }

    private fun configureCategoryCardRV(){
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvCategoryCards.layoutManager = layout

        arrCategoryCard = getHomeCategory()
        val adaptador = CategoryCardAdapter(arrCategoryCard)
        binding.rvCategoryCards.adapter = adaptador

        adaptador.listener = this
    }

    private fun getHomeCategory(): ArrayList<CategoryCard>{
        return arrayListOf(
            CategoryCard("Comida Italiana", "https://images.unsplash.com/photo-1551183053-bf91a1d81141?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80"),
            CategoryCard("Comida Mexicana", "https://images.unsplash.com/photo-1582234372722-50d7ccc30ebd?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80"),
            CategoryCard("Comida Argentina", "https://images.unsplash.com/photo-1544025162-d76694265947?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=640&q=80")
        )
    }

    private fun getHomeRecipe(): ArrayList<RecipeCard> {
        return arrayListOf(
            RecipeCard("Pasta arrabiata", "italiana", "https://images.unsplash.com/photo-1607375658859-39f31567ce13?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=740&q=80", "15min"),
            RecipeCard("Pizza napolitana", "italiana", "https://images.unsplash.com/photo-1589187151053-5ec8818e661b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "35min"),
            RecipeCard("Gelato", "italiana", "https://images.unsplash.com/photo-1580915411954-282cb1b0d780?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "45min")
        )
    }

    private fun getRecentRecipes() : ArrayList<RecipeCard> {
        return arrayListOf(
                RecipeCard("Pastel de chocolate", "Repostería", "https://images.unsplash.com/photo-1614786482494-7fc57abd0074?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=640&q=80", "1h 15min")
        )
    }

    override fun clicked(posicion: Int) {
        val recipeCard = arrRecipeCard[posicion]
        println("posicion: ${recipeCard}")
    }

   /* override fun clicked(posicion: Int) {
        val tarjeta = arrTarjetas[posicion]
        val url = Uri.parse("https://www.google.com/search?q=${tarjeta.banco}+${tarjeta.banco}")
        val intBuscar = Intent(Intent.ACTION_VIEW, url)
        startActivity(intBuscar)
    }*/

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}