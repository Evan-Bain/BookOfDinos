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
            if(dinoBadge) return R.drawable.dino_badge_brontosaurus
            R.drawable.fb_brontosaurus
        }
        2 -> {
            if(dinoBadge) return R.drawable.dino_badge_dilophosaurus
            R.drawable.fb_dilophosaurus
        }
        3 -> {
            if(dinoBadge) return R.drawable.dino_badge_mosasaurus
            R.drawable.fb_mosasaurus
        }
        4 -> {
            if(dinoBadge) return R.drawable.dino_badge_pteranodon
            R.drawable.fb_pteranodon
        }
        5 -> {
            if(dinoBadge) return R.drawable.dino_badge_spinosaurus
            R.drawable.fb_spinosaurus
        }
        6 -> {
            if(dinoBadge) return R.drawable.dino_badge_spinosaurus
            R.drawable.fb_stegosaurus
        }
        7 -> {
            if(dinoBadge) return R.drawable.dino_badge_triceratops
            R.drawable.fb_triceratops
        }
        8 -> {
            if(dinoBadge) return R.drawable.dino_badge_trex
            R.drawable.fb_t_rex
        }
        9 -> {
            if(dinoBadge) return R.drawable.dino_badge_velociraptor
            R.drawable.fb_velociraptor
        }
        else -> 0
    }
}
