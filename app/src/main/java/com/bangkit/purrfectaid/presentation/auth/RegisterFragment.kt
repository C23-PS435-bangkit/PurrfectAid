package com.bangkit.purrfectaid.presentation.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentRegisterBinding
import com.bangkit.purrfectaid.domain.model.RegisterRequest
import com.bangkit.purrfectaid.utils.Constants.BASE_URL
import com.bangkit.purrfectaid.utils.Result
import com.bangkit.purrfectaid.utils.getInputLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

    private fun TextInputEditText.checkError() {
        val inputPassword = binding.edRegisterPassword
        val inputConfirmPassword = binding.edRegisterConfirmPassword
        this.addTextChangedListener {
            val error =
                if (inputPassword.text.toString() != inputConfirmPassword.text.toString()) "Password tidak sama" else null
            inputPassword.getInputLayout().error = error
            inputConfirmPassword.getInputLayout().error = error

            if (inputPassword.getInputLayout().error == null && inputConfirmPassword.getInputLayout().error == null) {
                inputPassword.getInputLayout().isErrorEnabled = false
                inputConfirmPassword.getInputLayout().isErrorEnabled = false
            }
        }
    }

    private fun setupView() {
        binding.btnToLogin.setOnClickListener {
            val toLogin = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(toLogin)
        }

        val inputPassword = binding.edRegisterPassword
        val inputConfirmPassword = binding.edRegisterConfirmPassword

        inputPassword.checkError()
        inputConfirmPassword.checkError()

        binding.btnRegister.setOnClickListener {
            if (inputPassword.getInputLayout().error == null && inputConfirmPassword.getInputLayout().error == null) {
                val registerRequest = RegisterRequest(
                    email = binding.edRegisterEmail.text.toString(),
                    username = binding.edRegisterName.text.toString(),
                    password = inputPassword.text.toString()
                )
                register(registerRequest)
            } else {
                Toast.makeText(requireContext(), "Silahkan cek inputan anda", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegisterWithGoogle.setOnClickListener {
//            viewModel.registerOrLoginWithGoogle().observe(viewLifecycleOwner) {
//                when (it) {
//                    is Result.Success -> {
//                        Toast.makeText(requireContext(), it.data.msg, Toast.LENGTH_SHORT).show()
//                        val toHome =
//                            RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
//                        findNavController().navigate(toHome)
//                    }
//
//                    is Result.Loading -> {
//
//                    }
//
//                    is Result.Error -> {
//                        Log.e("Error Register Google", "Error: ${it.errorMessage}")
//                    }
//                }
//            }
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(BASE_URL + "users/google"))
            startActivity(browserIntent)
//            val toAuthGoogle = RegisterFragmentDirections.actionRegisterFragmentToAuthGoogleFragment()
//            findNavController().navigate(toAuthGoogle)
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