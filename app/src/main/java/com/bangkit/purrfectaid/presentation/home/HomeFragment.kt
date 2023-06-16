package com.bangkit.purrfectaid.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.purrfectaid.MainViewModel
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentHomeBinding
import com.bangkit.purrfectaid.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvDiagnoseHistory.layoutManager = LinearLayoutManager(requireContext())
        val adapter = DiagnoseHistoryAdapter()

        sharedViewModel.getUser()

        sharedViewModel.user.observe(viewLifecycleOwner) {
            binding.tvGreeting.text = resources.getString(R.string.hello_with_name, it.user_name)
        }

        viewModel.getDiagnoseHistory().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (it.data.isEmpty()) {
                        showNoDataView()
                    } else {
                        binding.rvDiagnoseHistory.adapter = adapter
                        adapter.submitData(it.data)
                        hideNoDataView()
                    }
                }

                is Result.Loading -> {

                }

                is Result.Error -> {
                    showNoDataView()
                    Log.e("Diagnose History", "Error: ${it.errorMessage}")
                }
            }
        }

        return binding.root
    }

    private fun showNoDataView() {
        binding.tvNoData.visibility = View.VISIBLE
        binding.rvDiagnoseHistory.visibility = View.GONE
    }

    private fun hideNoDataView() {
        binding.tvNoData.visibility = View.GONE
        binding.rvDiagnoseHistory.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}