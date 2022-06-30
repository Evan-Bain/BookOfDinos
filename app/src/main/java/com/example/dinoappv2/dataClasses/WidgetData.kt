package com.example.dinoappv2.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dinoappv2.R

@Entity(tableName = "widget_data_table")
data class WidgetData(
    @PrimaryKey val widgetId: Int,
    val dinoBadge: Boolean,
    val image: Int
)

/** convert number to associated resource file (image) **/
fun Int.toResourceId(dinoBadge: Boolean): Int {
    return when(this) {
        0 -> {
            if(dinoBadge) return R.drawable.dino_badge_ankylosaurus
            R.drawable.fb_ankylosaurus
        }
        1 -> {
            if(dinoBadge) return R.drawable.dino_badge_argentinosaurus
            R.drawable.fb_argentinosaurus
        }
        2 -> {
            if(dinoBadge) return R.drawable.dino_badge_brontosaurus
            R.drawable.fb_brontosaurus
        }
        3 -> {
            if(dinoBadge) return R.drawable.dino_badge_dilophosaurus
            R.drawable.fb_dilophosaurus
        }
        4 -> {
            if(dinoBadge) return R.drawable.dino_badge_giganotosaurus
            R.drawable.fb_giganotosaurus
        }
        5 -> {
            if(dinoBadge) return R.drawable.dino_badge_leedsichthys
            R.drawable.fb_leedsichthys
        }
        6 -> {
            if(dinoBadge) return R.drawable.dino_badge_mosasaurus
            R.drawable.fb_mosasaurus
        }
        7 -> {
            if(dinoBadge) return R.drawable.dino_badge_pteranodon
            R.drawable.fb_pteranodon
        }
        8 -> {
            if(dinoBadge) return R.drawable.dino_badge_quetzalcoatlus
            R.drawable.fb_quetzalcoatlus
        }
        9 -> {
            if(dinoBadge) return R.drawable.dino_badge_spinosaurus
            R.drawable.fb_spinosaurus
        }
        10 -> {
            if(dinoBadge) return R.drawable.dino_badge_stegasaurus
            R.drawable.fb_stegosaurus
        }
        11 -> {
            if(dinoBadge) return R.drawable.dino_badge_therizinosaurus
            R.drawable.fb_therizinosaurus
        }
        12 -> {
            if(dinoBadge) return R.drawable.dino_badge_trex
            R.drawable.fb_t_rex
        }
        13 -> {
            if(dinoBadge) return R.drawable.dino_badge_triceratops
            R.drawable.fb_triceratops
        }
        14 -> {
            if(dinoBadge) return R.drawable.dino_badge_velociraptor
            R.drawable.fb_velociraptor
        }
        else -> 0
    }
}
