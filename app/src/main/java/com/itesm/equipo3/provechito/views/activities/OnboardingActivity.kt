package com.itesm.equipo3.provechito.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.databinding.ActivityOnboardingBinding
import com.itesm.equipo3.provechito.models.ScreenItem
import com.itesm.equipo3.provechito.views.adapters.IntroViewPageAdapter

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var screenPager: ViewPager2
    private lateinit var screensArrayList: ArrayList<ScreenItem>
    private lateinit var tabBarIndicator: TabLayout
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        screensArrayList = getObData()
        tabBarIndicator = binding.tabLayout

        val introViewPageAdapter = IntroViewPageAdapter(this, screensArrayList)

        screenPager = binding.screenViewPager
        screenPager.adapter = introViewPageAdapter;
        TabLayoutMediator(tabBarIndicator, screenPager) {tab, position ->

        }.attach()

        binding.getStartedBtnOb.visibility = View.INVISIBLE
        binding.nextButtonOb.setOnClickListener {
            position = screenPager.currentItem
            screenPager.currentItem = ++position
        }

        tabBarIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == screensArrayList.size -1) {
                    loadGetStartedScreen()
                } else {
                    resetObScreenActions()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                println("Tab: ${tab?.position}")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                println("Tab: ${tab?.position}")
            }
        })

        binding.getStartedBtnOb.setOnClickListener {
            println("Go to Login")
            val i = Intent(baseContext, SigninActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun loadGetStartedScreen() {
        binding.getStartedBtnOb.visibility = View.VISIBLE
        binding.nextButtonOb.visibility = View.INVISIBLE
        binding.tabLayout.visibility = View.INVISIBLE
    }

    private fun resetObScreenActions() {
        binding.getStartedBtnOb.visibility = View.INVISIBLE
        if (binding.nextButtonOb.visibility == View.INVISIBLE)
            binding.nextButtonOb.visibility = View.VISIBLE
        if (binding.tabLayout.visibility == View.INVISIBLE)
            binding.tabLayout.visibility = View.VISIBLE
    }

    private fun getObData(): ArrayList<ScreenItem> {
        return arrayListOf(
            ScreenItem("Te damos la bienvenida a provechito", "No te preocupes m??s por los platillos, disfruta el proceso y ama la cocina", R.drawable.img_ob_1),
            ScreenItem("Encuentra tu siguiente receta favorita", "Contamos con un catalogo con m??s de 30 mil platillos de todo el mundo", R.drawable.img_ob_2),
            ScreenItem("No esperes m??s para sacar el cheff que llevas dentro", "Cualquiera puede cocinar, nosotros te guiaremos paso a paso, te tenemos cubierto", R.drawable.img_ob_3)
        )
    }

}