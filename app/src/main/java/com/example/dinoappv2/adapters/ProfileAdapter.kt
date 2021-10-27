package com.example.dinoappv2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinoappv2.R
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia

class ProfileAdapter(
    private val dinoData: List<DinosaurEncyclopedia>
): RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.dino_badge_profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_recycler_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(dinoData[position].activated) {
            holder.imageView.setImageResource(dinoData[position].badge)
        } else {
            holder.imageView.setImageResource((dinoData[position].badgeBlack))
        }
    }

    override fun getItemCount() = dinoData.size
}