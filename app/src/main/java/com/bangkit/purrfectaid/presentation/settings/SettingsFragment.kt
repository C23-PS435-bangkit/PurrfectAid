package com.bangkit.purrfectaid.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bangkit.purrfectaid.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.btnLogoutUser.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.logout()) {
                    Toast.makeText(requireContext(), "Berhasil logout", Toast.LENGTH_SHORT).show()
                    val toOpening = SettingsFragmentDirections.actionSettingsFragmentToOpeningFirstFragment()
                    findNavController().navigate(toOpening)
                } else {
                    Toast.makeText(requireContext(), "Gagal logout", Toast.LENGTH_SHORT).show()

                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}