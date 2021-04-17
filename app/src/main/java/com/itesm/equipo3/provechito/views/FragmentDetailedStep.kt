package com.itesm.equipo3.provechito.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itesm.equipo3.provechito.databinding.FragmentDetailedStepBinding

class FragmentDetailedStep : Fragment() {
    private var _binding: FragmentDetailedStepBinding? =  null
    private val binding get() = _binding!!
    private lateinit var listener: HomeClcikListener

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
            iinflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailedStepBinding.inflate(layoutInflater, container, false)
        binding.btnNextStep.setOnClickListener {
            val reviewFragment = ReviewFragment()
            listener.onNextClicked()
        }
        return binding.root
    }

}