package com.itesm.equipo3.provechito.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itesm.equipo3.provechito.databinding.FragmentSignInBinding
/*
* Autor: Zoe CD
 */

class SignInFragment : Fragment(){

    private var _binding: FragmentSignInBinding? = null
    private  val binding get() = _binding!!
    private lateinit var clickListener: SignInClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SignInClickListener) {
            clickListener = context
        } else {
            throw ClassCastException(context.toString() + " must implement SignInListener.")
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container,false)

        binding.tvSignUp.setOnClickListener {
            println("Go to sign up")
            clickListener.onSignUpFragClicked()
        }
        binding.btnSignIn.setOnClickListener{
            clickListener.onSignInButtonClicked()
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    companion object {
        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }


}