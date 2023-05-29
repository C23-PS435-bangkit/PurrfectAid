package com.bangkit.purrfectaid.presentation.opening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bangkit.purrfectaid.databinding.FragmentOpeningThirdBinding

class OpeningThirdFragment : Fragment() {

    private var _binding: FragmentOpeningThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpeningThirdBinding.inflate(inflater, container, false)

        binding.thirdOpeningButton.setOnClickListener {
            val toRegister = OpeningThirdFragmentDirections.actionOpeningThirdFragmentToRegisterFragment()
            findNavController().navigate(toRegister)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}