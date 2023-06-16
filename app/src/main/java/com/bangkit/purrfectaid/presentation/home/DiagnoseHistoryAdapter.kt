package com.bangkit.purrfectaid.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.purrfectaid.databinding.ItemCardDiagnoseBinding
import com.bangkit.purrfectaid.domain.model.Diagnose
import com.bangkit.purrfectaid.utils.parseDateRaw
import com.bumptech.glide.Glide

/**
 * Created by Yosua on 16/06/2023
 */
class DiagnoseHistoryAdapter : RecyclerView.Adapter<DiagnoseHistoryAdapter.DiagnoseViewHolder>() {
    private var listDiagnose = arrayListOf<Diagnose>()

    inner class DiagnoseViewHolder(var binding: ItemCardDiagnoseBinding) : RecyclerView.ViewHolder(binding.root)

    fun submitData(list: List<Diagnose>) {
        listDiagnose = list as ArrayList<Diagnose>
        notifyItemRangeChanged(0, list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiagnoseViewHolder {
        val binding = ItemCardDiagnoseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiagnoseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiagnoseViewHolder, position: Int) {
        val data = listDiagnose[position]

        holder.binding.apply {
            tvDiagnoseName.text = data.scanDiagnose
            tvDiagnoseDate.text = parseDateRaw(data.scanDate)

            Glide
                .with(holder.binding.root)
                .load(data.scanPict)
                .into(ivDiagnose)
        }
    }

    override fun getItemCount(): Int = listDiagnose.size
}