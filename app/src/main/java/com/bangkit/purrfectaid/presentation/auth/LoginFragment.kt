package com.bangkit.purrfectaid.presentation.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentLoginBinding
import com.bangkit.purrfectaid.domain.model.LoginRequest
import com.bangkit.purrfectaid.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupView()

        return binding.root
    }

    private fun setupView() {
        binding.btnToRegister.setOnClickListener {
            val toRegister = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(toRegister)
        }

        binding.apply {
            btnLogin.setOnClickListener {
                val loginRequest = LoginRequest(
                    email = edLoginEmail.text.toString(),
                    password = edLoginPassword.text.toString()
                )

                viewModel.login(loginRequest).observe(viewLifecycleOwner) {
                    when (it) {
                        is Result.Success -> {
                            if(it.data.status == 200) {
                                val toHome = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                                findNavController().navigate(toHome)
                            }
                        }

                        is Result.Loading -> {

                        }

                        is Result.Error -> {
                            Log.e("Error Login", "Error: ${it.errorMessage}")
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}