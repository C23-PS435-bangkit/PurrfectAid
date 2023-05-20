package com.bangkit.purrfectaid.presentation.opening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentOpeningSecondBinding

class OpeningSecondFragment : Fragment() {

    private var _binding: FragmentOpeningSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpeningSecondBinding.inflate(inflater, container, false)

        binding.secondOpeningButton.setOnClickListener {
            val toThirdOpening = OpeningSecondFragmentDirections.actionOpeningSecondFragmentToOpeningThirdFragment()
            findNavController().navigate(toThirdOpening)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}