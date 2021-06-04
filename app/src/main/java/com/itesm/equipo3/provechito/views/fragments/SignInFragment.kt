package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.itesm.equipo3.provechito.views.listeners.SignInClickListener
import com.itesm.equipo3.provechito.databinding.FragmentSignInBinding


/*
* Autor: Zoe CD
 */

class SignInFragment : Fragment(){

    private var _binding: FragmentSignInBinding? = null
    private  val binding get() = _binding!!
    private lateinit var clickListener: SignInClickListener


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SignInClickListener) {
            clickListener = context
        } else {
            throw ClassCastException("$context must implement SignInListener.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            val email: String = binding.editTextTextEmailAddress.text.toString()
            val pass: String = binding.textInputPasswordSignIn.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                clickListener.onSignInButtonClicked(email, pass)
            } else if (email.isEmpty()){
                Toast.makeText(context, "Ingresa un email primero", Toast.LENGTH_LONG).show()
            } else if (pass.isEmpty()) {
                Toast.makeText(context, "Ingresa tu contraseña", Toast.LENGTH_LONG).show()
            }

        }

        binding.btnSignInGoogle.setOnClickListener {
            clickListener.googleSingInClick()
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