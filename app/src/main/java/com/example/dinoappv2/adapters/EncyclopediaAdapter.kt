package com.example.dinoappv2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databinding.EncyclopediaRecyclerLayoutBinding

class EncyclopediaAdapter(
    private val onBadgeClicked: (DinosaurEncyclopedia) -> Unit) : ListAdapter<DinosaurEncyclopedia,
            EncyclopediaAdapter.ViewHolder>(AllDinosDiffCallback()) {

    inner class ViewHolder(val binding: EncyclopediaRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(data: DinosaurEncyclopedia) {
                binding.dinoBadge.setImageResource(data.badge)

                //add check next to dino image depending on if quiz is completed
                binding.dinoBadgeCheck.visibility = if(data.activated == true) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                binding.dinoName.text = data.name

                //sets click listener on the dinosaur badge
                binding.dinoBadge.setOnClickListener {
                    onBadgeClicked(data)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //setting up view binding instead of findViewById
         val binding = EncyclopediaRecyclerLayoutBinding
             .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class AllDinosDiffCallback : DiffUtil.ItemCallback<DinosaurEncyclopedia>() {
    override fun areItemsTheSame(
        oldItem: DinosaurEncyclopedia,
        newItem: DinosaurEncyclopedia
    ): Boolean {
        return oldItem.position == newItem.position
    }

    override fun areContentsTheSame(
        oldItem: DinosaurEncyclopedia,
        newItem: DinosaurEncyclopedia
    ): Boolean {
        return oldItem == newItem
    }

}