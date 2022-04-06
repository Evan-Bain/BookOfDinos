package com.example.dinoappv2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dinoappv2.dataClasses.DictionaryStrings
import com.example.dinoappv2.databinding.DictionaryRecyclerLayoutBinding

class DictionaryAdapter :
    ListAdapter<DictionaryStrings, DictionaryAdapter.ViewHolder>(DictionaryDiffCallback()) {

    inner class ViewHolder(val binding: DictionaryRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(data: DictionaryStrings) {
                binding.dictionaryRecyclerDefinition.text = data.definition
                binding.dictionaryRecyclerWordTitle.text = data.word
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //setting up view binding instead of findViewById
        val binding = DictionaryRecyclerLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DictionaryDiffCallback : DiffUtil.ItemCallback<DictionaryStrings>() {
    override fun areItemsTheSame(
        oldItem: DictionaryStrings,
        newItem: DictionaryStrings
    ): Boolean {
        return oldItem.word == newItem.word
    }

    override fun areContentsTheSame(
        oldItem: DictionaryStrings,
        newItem: DictionaryStrings
    ): Boolean {
        return oldItem == newItem
    }

}

