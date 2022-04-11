package com.example.dinoappv2.dataClasses

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dinoappv2.R
import java.text.FieldPosition

data class DinosaurEncyclopedia(
    val position: Int,
    val name: String,
    val badge: Int,
    val blackBadge: Int,
    val dinosaurFb: Int,
    val activated: Boolean? = null
)

/** returns names and images associated with each dino **/
fun getDinosaurEncyclopedia(table: List<DinosaurEncyclopediaTable>?): List<DinosaurEncyclopedia> {
    return listOf(
        //#1
        DinosaurEncyclopedia(
            0,
            "Ankylosaurus",
            R.drawable.dino_badge_ankylosaurus,
            R.drawable.dino_badge_ankylosaurus_black,
            R.drawable.fb_ankylosaurus,
            false
        ),
        //#2
        DinosaurEncyclopedia(
            1,
            "Brontosaurus",
            R.drawable.dino_badge_brontosaurus,
            R.drawable.dino_badge_brontosaurus_black,
            R.drawable.fb_ankylosaurus,
            false
        ),
        //#3
        DinosaurEncyclopedia(
            2,
            "Dilophosaurus",
            R.drawable.dino_badge_dilophosaurus,
            R.drawable.dino_badge_dilophosaurus_black,
            R.drawable.fb_ankylosaurus,
            false
        ),
        //#4
        DinosaurEncyclopedia(
            3,
            "Mosasaurus",
            R.drawable.dino_badge_mosasaurus,
            R.drawable.dino_badge_mosasaurus_black,
            R.drawable.fb_ankylosaurus,
            false
        ),
        //#5
        DinosaurEncyclopedia(
            4,
            "Pterodactyl",
            R.drawable.dino_badge_pterodactyl,
            R.drawable.dino_badge_pterodactyl_black,
            R.drawable.fb_ankylosaurus,
            false
        ),
        //#6
        DinosaurEncyclopedia(
            5,
            "Spinosaurus",
            R.drawable.dino_badge_spinosaurus,
            R.drawable.dino_badge_spinosaurus_black,
            R.drawable.fb_ankylosaurus,
            false
        ),
        //#7
        DinosaurEncyclopedia(
            6,
            "Stegosaurus",
            R.drawable.dino_badge_stegasaurus,
            R.drawable.dino_badge_stegasaurus_black,
            R.drawable.fb_ankylosaurus,
            false
        ),
        //#8
        DinosaurEncyclopedia(
            7,
            "Triceratops",
            R.drawable.dino_badge_triceratops,
            R.drawable.dino_badge_triceratops_black,
            R.drawable.fb_ankylosaurus,
            false
        ),
        //#9
        DinosaurEncyclopedia(
            8,
            "T-Rex",
            R.drawable.dino_badge_trex,
            R.drawable.dino_badge_trex_black,
            R.drawable.fb_t_rex,
            false
        ),
        //#10
        DinosaurEncyclopedia(
            9,
            "Velociraptor",
            R.drawable.dino_badge_raptor,
            R.drawable.dino_badge_raptor_black,
            R.drawable.fb_ankylosaurus,
            false
        )
    )
}

//Used to determine if a quiz has been completed or not
@Entity(tableName = "dinosaur_encyclopedia_table")
data class DinosaurEncyclopediaTable(
    @PrimaryKey val position: Int,
    val activated: Int
    )
