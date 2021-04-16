package com.itesm.equipo3.provechito.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itesm.equipo3.provechito.databinding.FragmentDetailedStepBinding

class FragmentDetailedStep : Fragment() {
    private var _binding: FragmentDetailedStepBinding? =  null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            iinflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailedStepBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}