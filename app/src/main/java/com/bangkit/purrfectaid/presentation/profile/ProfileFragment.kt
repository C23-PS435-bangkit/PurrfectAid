package com.bangkit.purrfectaid.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bangkit.purrfectaid.MainViewModel
import com.bangkit.purrfectaid.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.logout()) {
                    Toast.makeText(requireContext(), "Berhasil logout", Toast.LENGTH_SHORT).show()
                    val toOpening = ProfileFragmentDirections.actionProfileFragmentToOpeningFirstFragment()
                    findNavController().navigate(toOpening)
                } else {
                    Toast.makeText(requireContext(), "Gagal logout", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val dataUser = sharedViewModel.user.value
        binding.apply {
            tvName.text = dataUser?.user_name
            tvEmail.text = dataUser?.user_email
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}