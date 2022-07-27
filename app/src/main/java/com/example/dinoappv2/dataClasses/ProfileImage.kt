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
        1 -> R.drawable.dino_badge_argentinosaurus
        2 -> R.drawable.dino_badge_brontosaurus
        3 -> R.drawable.dino_badge_dilophosaurus
        4 -> R.drawable.dino_badge_giganotosaurus
        5 -> R.drawable.dino_badge_leedsichthys
        6 -> R.drawable.dino_badge_mosasaurus
        7 -> R.drawable.dino_badge_pteranodon
        8 -> R.drawable.dino_badge_quetzalcoatlus
        9 -> R.drawable.dino_badge_spinosaurus
        10 -> R.drawable.dino_badge_stegosaurus
        11 -> R.drawable.dino_badge_therizinosaurus
        12 -> R.drawable.dino_badge_triceratops
        13 -> R.drawable.dino_badge_trex
        14 -> R.drawable.dino_badge_velociraptor
        else -> R.drawable.profile_icon
    }
}