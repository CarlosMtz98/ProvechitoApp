package com.itesm.equipo3.provechito.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itesm.equipo3.provechito.R
import com.itesm.equipo3.provechito.databinding.FragmentHomeBinding
import com.itesm.equipo3.provechito.databinding.FragmentRecentRecipesBinding

class RecentRecipesFragment : Fragment() {
    private var _binding: FragmentRecentRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() : RecentRecipesFragment {
            return RecentRecipesFragment()
        }
    }
}