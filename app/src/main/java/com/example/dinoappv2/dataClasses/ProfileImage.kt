package com.example.dinoappv2.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dinoappv2.R

@Entity(tableName = "profile_image_table")
data class ProfileImage(
    val profileImage: Int = -1,
    @PrimaryKey val position: Int = 0
    )

/** converts position of ProfileImage to the corresponding badge **/
fun ProfileImage?.fromPosition(): Int {
    if(this == null) return R.drawable.profile_icon

    return when(this.profileImage) {
        0 -> R.drawable.dino_badge_ankylosaurus
        1 -> R.drawable.dino_badge_brontosaurus
        2 -> R.drawable.dino_badge_dilophosaurus
        3 -> R.drawable.dino_badge_mosasaurus
        4 -> R.drawable.dino_badge_pteranodon
        5 -> R.drawable.dino_badge_spinosaurus
        6 -> R.drawable.dino_badge_stegasaurus
        7 -> R.drawable.dino_badge_triceratops
        8 -> R.drawable.dino_badge_trex
        9 -> R.drawable.dino_badge_velociraptor
        else -> R.drawable.profile_icon
    }
}