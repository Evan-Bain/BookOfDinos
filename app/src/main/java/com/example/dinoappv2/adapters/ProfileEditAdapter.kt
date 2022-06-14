package com.example.dinoappv2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databinding.ProfileEditRecyclerLayoutBinding

class ProfileEditAdapter(
    private val onBadgeClicked: (DinosaurEncyclopedia, Boolean) -> Unit
) : ListAdapter<DinosaurEncyclopedia, ProfileEditAdapter.ViewHolder>(AllDinosDiffCallback()) {

    private var lastClicked: ImageView? = null
    private var sameClicked: Boolean = false

    inner class ViewHolder(val binding: ProfileEditRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(data: DinosaurEncyclopedia) {
                with(binding.profileEditBadge) {
                    setImageResource(data.badge)
                    setOnClickListener {
                        onBadgeClicked(data, addCheck(binding.profileEditCheck))
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProfileEditRecyclerLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun addCheck(check: ImageView): Boolean {

        when(lastClicked) {
            null -> {
                 check.visibility = View.VISIBLE
            }
            check -> {
                check.visibility = if(sameClicked) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
                sameClicked = !sameClicked
            }
            else -> {
                if(sameClicked) {
                    sameClicked = false
                }
                lastClicked!!.visibility = View.GONE
                check.visibility = View.VISIBLE
            }
        }
        lastClicked = check
        return check.visibility != View.GONE
    }
}