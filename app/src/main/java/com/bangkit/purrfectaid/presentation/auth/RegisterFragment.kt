package com.bangkit.purrfectaid.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentRegisterBinding
import com.bangkit.purrfectaid.domain.model.RegisterRequest
import com.bangkit.purrfectaid.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setupView()

        return binding.root
    }

    private fun setupView() {
        binding.btnToLogin.setOnClickListener {
            val toLogin = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(toLogin)
        }

        binding.btnRegister.setOnClickListener {
            val registerRequest = RegisterRequest(
                email = binding.edRegisterEmail.text.toString(),
                username = binding.edRegisterName.text.toString(),
                password = binding.edRegisterPassword.text.toString()
            )
            register(registerRequest)
        }

        binding.btnRegisterWithGoogle.setOnClickListener {
            viewModel.registerOrLoginWithGoogle().observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Success -> {
                        Toast.makeText(requireContext(), it.data.msg, Toast.LENGTH_SHORT).show()
                        val toHome =
                            RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
                        findNavController().navigate(toHome)
                    }

                    is Result.Loading -> {

                    }

                    is Result.Error -> {
                        Log.e("Error Register Google", "Error: ${it.errorMessage}")
                    }
                }
            }
        }

    }

    private fun register(registerRequest: RegisterRequest) {
        viewModel.register(registerRequest).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (it.data.status == 200) {
                        Toast.makeText(requireContext(), it.data.msg, Toast.LENGTH_SHORT).show()
                        val toLogin =
                            RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        findNavController().navigate(toLogin)
                    }
                }

                is Result.Loading -> {

                }

                is Result.Error -> {
                    Log.e("Error Register", "Error: ${it.errorMessage}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}