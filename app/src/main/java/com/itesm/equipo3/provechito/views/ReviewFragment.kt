package com.itesm.equipo3.provechito.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.databinding.FragmentHomeBinding
import com.itesm.equipo3.provechito.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: HomeClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement HomeClickListner.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)

        binding.sendReviewButton.setOnClickListener {
            val homeFragment = HomeFragment()
            listener.onSendClicked()
        }
        return binding.root
    }
}