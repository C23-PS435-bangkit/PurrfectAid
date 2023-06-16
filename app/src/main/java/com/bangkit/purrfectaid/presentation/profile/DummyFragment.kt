package com.bangkit.purrfectaid.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.purrfectaid.databinding.FragmentDummyBinding

class DummyFragment : Fragment() {

    private var position = 1

    private var _binding: FragmentDummyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDummyBinding.inflate(inflater, container, false)

        return binding.root
    }

}