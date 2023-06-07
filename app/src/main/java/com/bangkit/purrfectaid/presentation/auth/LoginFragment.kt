package com.bangkit.purrfectaid.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnToRegister.setOnClickListener {
            val toRegister = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(toRegister)
        }

        binding.btnLogin.setOnClickListener {
            val toHome = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            findNavController().navigate(toHome)
        }

        return binding.root
    }

}