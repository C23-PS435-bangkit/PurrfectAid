package com.bangkit.purrfectaid.presentation.diagnose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.purrfectaid.databinding.DiagnoseSheetLayoutBinding
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

        val diagnose = requireArguments().getString("diagnose")
        val treatment = requireArguments().getString("treatment")

        binding.tvDiagnoseTitle.text = diagnose
        binding.tvDiagnoseTreatment.text = treatment

        return binding.root
    }

}