package com.example.dinoappv2.dataClasses

import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dinoappv2.R
import kotlinx.coroutines.flow.*
import kotlinx.parcelize.Parcelize
import java.text.FieldPosition

@Parcelize
data class DinosaurEncyclopedia(
    val position: Int,
    val name: String,
    val badge: Int,
    val blackBadge: Int,
    val dinosaurFb: Int,
    val activated: Boolean? = null
) : Parcelable

/** returns names and images associated with each dino **/
fun Flow<List<DinosaurEncyclopediaTable>>.getDinosaurEncyclopedia(): Flow<List<DinosaurEncyclopedia>> {
    Log.i("DinosaurEncyclopedia", this.toString())
    return this.map { value ->
        listOf(
            DinosaurEncyclopedia(
                0,
                "Ankylosaurus",
                R.drawable.dino_badge_ankylosaurus,
                R.drawable.dino_badge_ankylosaurus_black,
                R.drawable.fb_ankylosaurus,
                value[0].activated != 0
            ),
            //#2
            DinosaurEncyclopedia(
                1,
                "Brontosaurus",
                R.drawable.dino_badge_brontosaurus,
                R.drawable.dino_badge_brontosaurus_black,
                R.drawable.fb_ankylosaurus,
                value[1].activated != 0
            ),
            //#3
            DinosaurEncyclopedia(
                2,
                "Dilophosaurus",
                R.drawable.dino_badge_dilophosaurus,
                R.drawable.dino_badge_dilophosaurus_black,
                R.drawable.fb_ankylosaurus,
                value[2].activated != 0
            ),
            //#4
            DinosaurEncyclopedia(
                3,
                "Mosasaurus",
                R.drawable.dino_badge_mosasaurus,
                R.drawable.dino_badge_mosasaurus_black,
                R.drawable.fb_ankylosaurus,
                value[3].activated != 0
            ),
            //#5
            DinosaurEncyclopedia(
                4,
                "Pterodactyl",
                R.drawable.dino_badge_pterodactyl,
                R.drawable.dino_badge_pterodactyl_black,
                R.drawable.fb_ankylosaurus,
                value[4].activated != 0
            ),
            //#6
            DinosaurEncyclopedia(
                5,
                "Spinosaurus",
                R.drawable.dino_badge_spinosaurus,
                R.drawable.dino_badge_spinosaurus_black,
                R.drawable.fb_ankylosaurus,
                value[5].activated != 0
            ),
            //#7
            DinosaurEncyclopedia(
                6,
                "Stegosaurus",
                R.drawable.dino_badge_stegasaurus,
                R.drawable.dino_badge_stegasaurus_black,
                R.drawable.fb_ankylosaurus,
                value[6].activated != 0
            ),
            //#8
            DinosaurEncyclopedia(
                7,
                "Triceratops",
                R.drawable.dino_badge_triceratops,
                R.drawable.dino_badge_triceratops_black,
                R.drawable.fb_ankylosaurus,
                value[7].activated != 0
            ),
            //#9
            DinosaurEncyclopedia(
                8,
                "T-Rex",
                R.drawable.dino_badge_trex,
                R.drawable.dino_badge_trex_black,
                R.drawable.fb_t_rex,
                value[8].activated != 0
            ),
            //#10
            DinosaurEncyclopedia(
                9,
                "Velociraptor",
                R.drawable.dino_badge_raptor,
                R.drawable.dino_badge_raptor_black,
                R.drawable.fb_ankylosaurus,
                value[9].activated != 0
            )
        )
    }
}

//Used to determine if a quiz has been completed or not
@Entity(tableName = "dinosaur_encyclopedia_table")
data class DinosaurEncyclopediaTable(
    @PrimaryKey val position: Int,
    val activated: Int
    )
