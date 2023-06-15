package com.bangkit.purrfectaid.presentation.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentCommunityBinding
import com.bangkit.purrfectaid.domain.model.InsertPostRequest
import com.bangkit.purrfectaid.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : Fragment() {

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CommunityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.btnCommunityPost.setOnClickListener {
            val description = binding.edPostContent.text.toString()
            val request = InsertPostRequest(
                title = "Cat",
                content = description
            )

            insertPost(request)
        }
    }

    private fun insertPost(request: InsertPostRequest) {
        viewModel.insertPost(request).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Log.d("Berhasil Post", "Berhasil ${it.data}")
                }

                is Result.Loading -> {

                }

                is Result.Error -> {
                    Log.e("GAGAL POST", "GAGAL: ${it.errorMessage}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}