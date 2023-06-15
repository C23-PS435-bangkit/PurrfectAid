package com.bangkit.purrfectaid.presentation.opening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bangkit.purrfectaid.databinding.FragmentOpeningFirstBinding

class OpeningFirstFragment : Fragment() {

    private var _binding: FragmentOpeningFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpeningFirstBinding.inflate(inflater, container, false)

        binding.firstOpeningButton.setOnClickListener {
            val toSecondOpening = OpeningFirstFragmentDirections.actionOpeningFirstFragmentToOpeningSecondFragment()
            findNavController().navigate(toSecondOpening)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}