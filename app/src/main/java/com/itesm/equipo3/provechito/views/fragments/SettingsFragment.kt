package com.itesm.equipo3.provechito.views.fragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.databinding.FragmentSettingsBinding
import com.itesm.equipo3.provechito.views.activities.OnboardingActivity
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener


class SettingsFragment : Fragment(){
    private lateinit var listener: HomeClickListener
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var preferences: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.btnAbout.setOnClickListener {
            listener.onAboutClicked()
        }

        binding.btnLogout.setOnClickListener {
            context?.let {
                var preferences: SharedPreferences = it.getSharedPreferences(it.getString(R.string.app_name), Context.MODE_PRIVATE)
                preferences.edit().clear().apply();
                val i = Intent(it, OnboardingActivity::class.java)
                val fm = this.activity?.supportFragmentManager
                if (fm != null) {
                    for (i in 0 until fm.backStackEntryCount) {
                        fm.popBackStack()
                    }
                }
                startActivity(i)
            }
        }
        return binding.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement SignUpListener.")
        }
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}