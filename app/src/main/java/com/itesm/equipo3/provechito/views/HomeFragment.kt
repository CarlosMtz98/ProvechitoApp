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
            CategoryCard("Comida Italiana", ""),
            CategoryCard("Comida Mexicana", ""),
            CategoryCard("Comida Argentina", "")
        )
    }

    private fun getHomeRecipe(): ArrayList<RecipeCard> {
        return arrayListOf(
            RecipeCard("Pasta arrabiata", "italiana", "https://shorturl.at/oFLOX", "15min"),
            RecipeCard("Pizza napolitana", "italiana", "https://shorturl.at/jyU27", "35min"),
            RecipeCard("Gelato", "italiana", "https://shorturl.at/uFKNO", "45min")
        )
    }

    private fun getRecentRecipes() : ArrayList<RecipeCard> {
        return arrayListOf(
                RecipeCard("Pastel de chocolate", "Repostería", "https://shorturl.at/oFLOX", "1h 15min")
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