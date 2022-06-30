package com.example.dinoappv2.dataClasses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dinoappv2.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.parcelize.Parcelize

@Parcelize
data class DinosaurEncyclopedia(
    val position: Int,
    val name: String,
    val badge: Int,
    val blackBadge: Int,
    val dinosaurFb: Int,
    val blackDinosaurFb: Int,
    val activated: Boolean
) : Parcelable

/** returns names and images associated with each dino **/
fun Flow<List<DinosaurEncyclopediaTable>>.getDinosaurEncyclopedia(): Flow<List<DinosaurEncyclopedia>> {
    return this.map { value ->
        listOf(
            DinosaurEncyclopedia(
                0,
                "Ankylosaurus",
                R.drawable.dino_badge_ankylosaurus,
                R.drawable.dino_badge_ankylosaurus_black,
                R.drawable.fb_ankylosaurus,
                R.drawable.fb_ankylosaurus_black,
                value[0].activated != 0
            ),
            //#2
            DinosaurEncyclopedia(
                1,
                "Argentinosaurus",
                R.drawable.dino_badge_argentinosaurus,
                R.drawable.dino_badge_argentinosaurus_black,
                R.drawable.fb_argentinosaurus,
                R.drawable.fb_argentinosaurus_black,
                value[1].activated != 0
            ),
            //#3
            DinosaurEncyclopedia(
                2,
                "Brontosaurus",
                R.drawable.dino_badge_brontosaurus,
                R.drawable.dino_badge_brontosaurus_black,
                R.drawable.fb_brontosaurus,
                R.drawable.fb_brontosaurus_black,
                value[2].activated != 0
            ),
            //#4
            DinosaurEncyclopedia(
                3,
                "Dilophosaurus",
                R.drawable.dino_badge_dilophosaurus,
                R.drawable.dino_badge_dilophosaurus_black,
                R.drawable.fb_dilophosaurus,
                R.drawable.fb_dilophosarus_black,
                value[3].activated != 0
            ),
            //#5
            DinosaurEncyclopedia(
                4,
                "Giganotosaurus",
                R.drawable.dino_badge_giganotosaurus,
                R.drawable.dino_badge_giganotosaurus_black,
                R.drawable.fb_giganotosaurus,
                R.drawable.fb_giganotosaurus_black,
                value[4].activated != 0
            ),
            //#6
            DinosaurEncyclopedia(
                5,
                "Leedsichthys",
                R.drawable.dino_badge_leedsichthys,
                R.drawable.dino_badge_leedsichthys_black,
                R.drawable.fb_leedsichthys,
                R.drawable.fb_leedsichthys_black,
                value[5].activated != 0
            ),
            //#7
            DinosaurEncyclopedia(
                6,
                "Mosasaurus",
                R.drawable.dino_badge_mosasaurus,
                R.drawable.dino_badge_mosasaurus_black,
                R.drawable.fb_mosasaurus,
                R.drawable.fb_mosasaurus_black,
                value[6].activated != 0
            ),
            //#8
            DinosaurEncyclopedia(
                7,
                "Pteranodon",
                R.drawable.dino_badge_pteranodon,
                R.drawable.dino_badge_pteranodon_black,
                R.drawable.fb_pteranodon,
                R.drawable.fb_pteranodon_black,
                value[7].activated != 0
            ),
            //#9
            DinosaurEncyclopedia(
                8,
                "Quetzalcoatlus",
                R.drawable.dino_badge_quetzalcoatlus,
                R.drawable.dino_badge_quetzalcoatlus_black,
                R.drawable.fb_quetzalcoatlus,
                R.drawable.fb_quetzalcoatlus_black,
                value[8].activated != 0
            ),
            //#10
            DinosaurEncyclopedia(
                9,
                "Spinosaurus",
                R.drawable.dino_badge_spinosaurus,
                R.drawable.dino_badge_spinosaurus_black,
                R.drawable.fb_spinosaurus,
                R.drawable.fb_spinosaurus_black,
                value[9].activated != 0
            ),
            //#11
            DinosaurEncyclopedia(
                10,
                "Stegosaurus",
                R.drawable.dino_badge_stegasaurus,
                R.drawable.dino_badge_stegasaurus_black,
                R.drawable.fb_stegosaurus,
                R.drawable.fb_stegosaurus_black,
                value[10].activated != 0
            ),
            //#12
            DinosaurEncyclopedia(
                11,
                "Therizinosaurus",
                R.drawable.dino_badge_therizinosaurus,
                R.drawable.dino_badge_therizinosaurus_black,
                R.drawable.fb_therizinosaurus,
                R.drawable.fb_therizinosaurus_black,
                value[11].activated != 0
            ),
            //#13
            DinosaurEncyclopedia(
                12,
                "Triceratops",
                R.drawable.dino_badge_triceratops,
                R.drawable.dino_badge_triceratops_black,
                R.drawable.fb_triceratops,
                R.drawable.fb_triceratops_black,
                value[12].activated != 0
            ),
            //#14
            DinosaurEncyclopedia(
                13,
                "T-Rex",
                R.drawable.dino_badge_trex,
                R.drawable.dino_badge_trex_black,
                R.drawable.fb_t_rex,
                R.drawable.fb_t_rex_black,
                value[13].activated != 0
            ),
            //#15
            DinosaurEncyclopedia(
                14,
                "Velociraptor",
                R.drawable.dino_badge_velociraptor,
                R.drawable.dino_badge_velociraptor_black,
                R.drawable.fb_velociraptor,
                R.drawable.fb_velociraptor_black,
                value[14].activated != 0
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
