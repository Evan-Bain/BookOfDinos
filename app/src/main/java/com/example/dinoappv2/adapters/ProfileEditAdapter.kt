package com.example.dinoappv2.adapters

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dinoappv2.R
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databinding.ProfileEditRecyclerLayoutBinding

class ProfileEditAdapter(
    private val context: Context,
    private val onBadgeClicked: (DinosaurEncyclopedia, Boolean) -> Unit
) : ListAdapter<DinosaurEncyclopedia, ProfileEditAdapter.ViewHolder>(AllDinosDiffCallback()) {

    private var lastClicked: ImageView? = null
    private var sameClicked: Boolean = false

    inner class ViewHolder(val binding: ProfileEditRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(data: DinosaurEncyclopedia) {
                with(binding.profileEditBadge) {
                    setImageResource(data.badge)
                    contentDescription = if(data.position == -1) {
                        context.resources.getString(R.string.profile_default)
                    } else {
                        context.resources.getStringArray(
                            R.array.dinosaur_badge_names_array)[data.position]
                    }
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
        val visible: Boolean

        when(lastClicked) {
            null -> {
                fadeIn(check)
                visible = true
            }
            check -> {
                visible = if(sameClicked) {
                    fadeIn(check)
                    true
                } else {
                    fadeOut(check)
                    false
                }
                sameClicked = !sameClicked
            }
            else -> {
                if(sameClicked) {
                    sameClicked = false
                }
                fadeOut(lastClicked!!)
                fadeIn(check)
                visible = true
            }
        }
        lastClicked = check
        return visible
    }

    private fun fadeIn(check: ImageView) {
        ObjectAnimator.ofFloat(check, "alpha", 1f).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun fadeOut(check: ImageView) {
        ObjectAnimator.ofFloat(check, "alpha", 0f).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }
}