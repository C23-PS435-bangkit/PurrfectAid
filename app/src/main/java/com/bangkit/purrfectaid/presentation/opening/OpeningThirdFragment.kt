package com.bangkit.purrfectaid.presentation.opening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentOpeningFirstBinding
import com.bangkit.purrfectaid.databinding.FragmentOpeningThirdBinding

class OpeningThirdFragment : Fragment() {

    private var _binding: FragmentOpeningThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpeningThirdBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}