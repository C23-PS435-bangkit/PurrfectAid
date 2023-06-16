package com.bangkit.purrfectaid.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.purrfectaid.MainViewModel
import com.bangkit.purrfectaid.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        setupView()

        return binding.root
    }

    private fun setupView() {
        sharedViewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                tvName.text = it.user_name
                tvEmail.text = it.user_email
            }
        }

        binding.btnBackSetting.setOnClickListener {
            val toSettings = ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
            findNavController().navigate(toSettings)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}