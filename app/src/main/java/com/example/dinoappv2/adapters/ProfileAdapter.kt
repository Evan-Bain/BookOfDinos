package com.example.dinoappv2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databinding.ProfileRecyclerLayoutBinding

class ProfileAdapter :
    ListAdapter<DinosaurEncyclopedia, ProfileAdapter.ViewHolder>(AllDinosDiffCallback()) {

    inner class ViewHolder(val binding: ProfileRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DinosaurEncyclopedia) {

            //sets image to black and white if quiz is not completed
            if(data.activated == true) {
                binding.dinoBadgeProfile.setImageResource(data.badge)
            } else {
                binding.dinoBadgeProfile.setImageResource(data.blackBadge)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProfileRecyclerLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

