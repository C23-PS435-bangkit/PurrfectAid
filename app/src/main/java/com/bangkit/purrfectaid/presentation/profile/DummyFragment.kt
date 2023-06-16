package com.bangkit.purrfectaid.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentDummyBinding
import com.bangkit.purrfectaid.databinding.FragmentProfileBinding

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