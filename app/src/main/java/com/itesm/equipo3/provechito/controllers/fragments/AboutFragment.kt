package com.itesm.equipo3.provechito.controllers.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.controllers.listeners.SettingsClickListener
import com.itesm.equipo3.provechito.databinding.FragmentAboutBinding
import com.itesm.equipo3.provechito.databinding.FragmentSettingsBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }
}