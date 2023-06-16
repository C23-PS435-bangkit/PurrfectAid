package com.bangkit.purrfectaid.presentation.vet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bangkit.purrfectaid.databinding.ItemCardDiagnoseBinding
import com.bumptech.glide.Glide

// Here the logic begin
class VetAdapter (private var placeList: ArrayList<String>, private val avatar:List<String>):RecyclerView.Adapter<VetAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardDiagnoseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(placeList[position], avatar[position])
    }

    override fun getItemCount(): Int {
        return  placeList.size
    }

    class ViewHolder(private val binding:ItemCardDiagnoseBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(placeList:String, avatar:String){
            binding.tvDiagnoseName.text = placeList

            Glide.with(itemView.context)
                .load(avatar)
                .into(binding.ivDiagnose)
        }
    }

}