package com.bangkit.purrfectaid.presentation.vet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bangkit.purrfectaid.databinding.ItemCardDiagnoseBinding
import com.bumptech.glide.Glide

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
            val apiReference = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=${avatar}&key=AIzaSyAlXcP6HBdRO5z9uOBl5e5zUsz_VgLeFhM"
            binding.tvDiagnoseName.text = placeList

            Glide.with(itemView.context)
                .load(apiReference)
                .into(binding.ivDiagnose)
        }
    }

}