package com.example.dinoappv2.dataClasses

import com.example.dinoappv2.R

data class DinosaurSubtitles(
    val story: Int,
    val habitat: Int,
    val evolution: Int,
    val fossil: Int
)

fun DinosaurEncyclopedia.getDinosaurSubtitle(section: Int): Int {

    return when(position) {

        //ankylosaurus
        0 -> {
            when(section) {
                0 -> R.raw.subtitle_ankylosaurus_story
                1 -> R.raw.subtitle_ankylosaurus_habitat
                2 -> R.raw.subtitle_ankylosaurus_evolution
                else -> R.raw.subtitle_ankylosaurus_fossil
            }
        }

        //argentinosaurus
        1 -> {
            when(section) {
                0 -> R.raw.subtitle_argentinosaurus_story
                1 -> R.raw.subtitle_argentinosaurus_habitat
                2 -> R.raw.subtitle_argentinosaurus_evolution
                else -> R.raw.subtitle_argentinosaurus_fossil
            }
        }

        //brontosaurus
        2 -> {
            when(section) {
                0 -> R.raw.subtitle_brontosaurus_story
                1 -> R.raw.subtitle_brontosaurus_habitat
                2 -> R.raw.subtitle_brontosaurus_evolution
                else -> R.raw.subtitle_brontosaurus_fossil
            }
        }

        //dilophosaurus
        3 -> {
            when(section) {
                0 -> R.raw.subtitle_dilophosaurus_story
                1 -> R.raw.subtitle_dilophosaurus_habitat
                2 -> R.raw.subtitle_dilophosaurus_evolution
                else -> R.raw.subtitle_dilophosaurus_fossil
            }
        }

        //giganotosaurus
        4 -> {
            when(section) {
                0 -> R.raw.subtitle_giganotosaurus_story
                1 -> R.raw.subtitle_giganotosaurus_habitat
                2 -> R.raw.subtitle_giganotosaurus_evolution
                else -> R.raw.subtitle_giganotosaurus_fossil
            }
        }

        //leedsichthys
        5 -> {
            when(section) {
                0 -> R.raw.subtitle_leedsichthys_story
                1 -> R.raw.subtitle_leedsichthys_habitat
                2 -> R.raw.subtitle_leedsichthys_evolution
                else -> R.raw.subtitle_leedsichthys_fossil
            }
        }

        //mosasaurus
        6 -> {
            when(section) {
                0 -> R.raw.subtitle_mosasaurus_story
                1 -> R.raw.subtitle_mosasaurus_habitat
                2 -> R.raw.subtitle_mosasaurus_evolution
                else -> R.raw.subtitle_mosasaurus_fossil
            }
        }

        //pteranodon
        7 -> {
            when(section) {
                0 -> R.raw.subtitle_pteranodon_story
                1 -> R.raw.subtitle_pteranodon_habitat
                2 -> R.raw.subtitle_pteranodon_evolution
                else -> R.raw.subtitle_pteranodon_fossil
            }
        }

        //quetzalcoatlus
        8 -> {
            when(section) {
                0 -> R.raw.subtitle_quetzalcoatlus_story
                1 -> R.raw.subtitle_quetzalcoatlus_habitat
                2 -> R.raw.subtitle_quetzalcoatlus_evolution
                else -> R.raw.subtitle_quetzalcoatlus_fossil
            }
        }

        //spinosaurus
        9 -> {
            when(section) {
                0 -> R.raw.subtitle_spinosaurus_story
                1 -> R.raw.subtitle_spinosaurus_habitat
                2 -> R.raw.subtitle_spinosaurus_evolution
                else -> R.raw.subtitle_spinosaurus_fossil
            }
        }

        //stegosaurus
        10 -> {
            when(section) {
                0 -> R.raw.subtitle_stegosaurus_story
                1 -> R.raw.subtitle_stegosaurus_habitat
                2 -> R.raw.subtitle_stegosaurus_evolution
                else -> R.raw.subtitle_stegosaurus_fossil
            }
        }

        //therizinosaurus
        11 -> {
            when(section) {
                0 -> R.raw.subtitle_therizinosaurus_story
                1 -> R.raw.subtitle_therizinosaurus_habitat
                2 -> R.raw.subtitle_therizinosaurus_evolution
                else -> R.raw.subtitle_therizinosaurus_fossil
            }
        }

        //triceratops
        12 -> {
            when(section) {
                0 -> R.raw.subtitle_triceratops_story
                1 -> R.raw.subtitle_triceratops_habitat
                2 -> R.raw.subtitle_triceratops_evolution
                else -> R.raw.subtitle_triceratops_fossil
            }
        }

        //tyrannosaurusrex
        1 -> {
            when(section) {
                0 -> R.raw.subtitle_tyrannosaurusrex_story
                1 -> R.raw.subtitle_tyrannosaurusrex_habitat
                2 -> R.raw.subtitle_tyrannosaurusrex_evolution
                else -> R.raw.subtitle_tyrannosaurusrex_fossil
            }
        }

        //velociraptor
        else -> {
            when(section) {
                0 -> R.raw.subtitle_velociraptor_story
                1 -> R.raw.subtitle_velociraptor_habitat
                2 -> R.raw.subtitle_velociraptor_evolution
                else -> R.raw.subtitle_velociraptor_fossil
            }
        }
    }
}