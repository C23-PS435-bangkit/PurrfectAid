package com.bangkit.purrfectaid.presentation.diagnose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.purrfectaid.databinding.DiagnoseSheetLayoutBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Yosua on 15/06/2023
 */
class DiagnoseBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: DiagnoseSheetLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DiagnoseSheetLayoutBinding.inflate(inflater, container, false)

        val isFromDiagnoseHistory = requireArguments().getBoolean("isFromDiagnoseHistory")

        val diagnose = requireArguments().getString("diagnose")
        val treatment = requireArguments().getString("treatment")

        binding.tvDiagnoseTitle.text = diagnose
        binding.tvDiagnoseTreatment.text = treatment

        if (isFromDiagnoseHistory) {
            val image = requireArguments().getString("imageDiagnose")

            Glide
                .with(binding.root)
                .load(image)
                .into(binding.ivDiagnoseImage)

            binding.ivDiagnoseImage.visibility = View.VISIBLE
        } else {
            binding.ivDiagnoseImage.visibility = View.GONE
        }



        return binding.root
    }

}