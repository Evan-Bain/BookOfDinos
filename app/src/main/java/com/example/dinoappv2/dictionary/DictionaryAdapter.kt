package com.example.dinoappv2.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dinoappv2.databinding.DictionaryRecyclerLayoutBinding
import com.example.dinoappv2.dictionary.model.DictionaryStrings

class DictionaryAdapter :
    ListAdapter<DictionaryStrings, DictionaryAdapter.ViewHolder>(DictionaryDiffCallback()) {

    inner class ViewHolder(val binding: DictionaryRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(data: DictionaryStrings, lastData: DictionaryStrings?) {
                binding.dictionaryRecyclerDefinition.text = data.definition
                binding.dictionaryRecyclerWordTitle.text = data.word

                //If the last word and current words first letter are different display
                //a header (the new letter)
                if(lastData != null) {
                    if(lastData.word[0] != data.word[0]) {
                        binding.letterTitle.text = data.word[0].toString()
                        binding.letterTitle.visibility = View.VISIBLE
                    } else {
                        binding.letterTitle.visibility = View.GONE
                    }
                } else {
                    binding.letterTitle.text = data.word[0].toString()
                    binding.letterTitle.visibility = View.VISIBLE
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //setting up view binding instead of findViewById
        val binding = DictionaryRecyclerLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //check if this is the first position or not
        try {
            holder.bind(getItem(position), getItem(position-1))
        } catch(e: IndexOutOfBoundsException) {
            holder.bind(getItem(position), null)
        }
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