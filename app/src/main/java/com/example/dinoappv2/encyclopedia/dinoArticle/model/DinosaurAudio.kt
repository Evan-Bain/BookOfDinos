package com.example.dinoappv2.encyclopedia.dinoArticle.model

import com.example.dinoappv2.R
import com.example.dinoappv2.encyclopedia.model.DinosaurEncyclopedia

data class DinosaurAudio(
    val pronunciation: Int,
    val story: Int,
    val habitat: Int,
    val evolution: Int,
    val fossil: Int
)

fun DinosaurEncyclopedia.getDinosaurAudio(section: Int): Int {

    return when(position) {

        //ankylosaurus
        0 -> {
            when(section) {
                0 -> R.raw.audio_ankylosaurus_story
                1 -> R.raw.audio_ankylosaurus_habitat
                2 -> R.raw.audio_ankylosaurus_evolution
                3 -> R.raw.audio_ankylosaurus_fossil
                else -> R.raw.audio_ankylosaurus_pronunciation
            }
        }

        //argentinosaurus
        1 -> {
            when(section) {
                0 -> R.raw.audio_argentinosaurus_story
                1 -> R.raw.audio_argentinosaurus_habitat
                2 -> R.raw.audio_argentinosaurus_evolution
                3 -> R.raw.audio_argentinosaurus_fossil
                else -> R.raw.audio_argentinosaurus_pronunciation
            }
        }

        //brontosaurus
        2 -> {
            when(section) {
                0 -> R.raw.audio_brontosaurus_story
                1 -> R.raw.audio_brontosaurus_habitat
                2 -> R.raw.audio_brontosaurus_evolution
                3 -> R.raw.audio_brontosaurus_fossil
                else -> R.raw.audio_brontosaurus_pronunciation
            }
        }

        //dilophosaurus
        3 -> {
            when(section) {
                0 -> R.raw.audio_dilophosaurus_story
                1 -> R.raw.audio_dilophosaurus_habitat
                2 -> R.raw.audio_dilophosaurus_evolution
                3 -> R.raw.audio_dilophosaurus_fossil
                else -> R.raw.audio_dilophosaurus_pronunciation
            }
        }

        //giganotosaurus
        4 -> {
            when(section) {
                0 -> R.raw.audio_giganotosaurus_story
                1 -> R.raw.audio_giganotosaurus_habitat
                2 -> R.raw.audio_giganotosaurus_evolution
                3 -> R.raw.audio_giganotosaurus_fossil
                else -> R.raw.audio_giganotosaurus_pronunciation
            }
        }

        //leedsichthys
        5 -> {
            when(section) {
                0 -> R.raw.audio_leedsichthys_story
                1 -> R.raw.audio_leedsichthys_habitat
                2 -> R.raw.audio_leedsichthys_evolution
                3 -> R.raw.audio_leedsichthys_fossil
                else -> R.raw.audio_leedsichthys_pronunciation
            }
        }

        //mosasaurus
        6 -> {
            when(section) {
                0 -> R.raw.audio_mosasaurus_story
                1 -> R.raw.audio_mosasaurus_habitat
                2 -> R.raw.audio_mosasaurus_evolution
                3 -> R.raw.audio_mosasaurus_fossil
                else -> R.raw.audio_mosasaurus_pronunciation
            }
        }

        //pteranodon
        7 -> {
            when(section) {
                0 -> R.raw.audio_pteranodon_story
                1 -> R.raw.audio_pteranodon_habitat
                2 -> R.raw.audio_pteranodon_evolution
                3 -> R.raw.audio_pteranodon_fossil
                else -> R.raw.audio_pteranodon_pronunciation
            }
        }

        //quetzalcoatlus
        8 -> {
            when(section) {
                0 -> R.raw.audio_quetzalcoatlus_story
                1 -> R.raw.audio_quetzalcoatlus_habitat
                2 -> R.raw.audio_quetzalcoatlus_evolution
                3 -> R.raw.audio_quetzalcoatlus_fossil
                else -> R.raw.audio_quetzalcoatlus_pronunciation
            }
        }

        //spinosaurus
        9 -> {
            when(section) {
                0 -> R.raw.audio_spinosaurus_story
                1 -> R.raw.audio_spinosaurus_habitat
                2 -> R.raw.audio_spinosaurus_evolution
                3 -> R.raw.audio_spinosaurus_fossil
                else -> R.raw.audio_spinosaurus_pronunciation
            }
        }

        //stegosaurus
        10 -> {
            when(section) {
                0 -> R.raw.audio_stegosaurus_story
                1 -> R.raw.audio_stegosaurus_habitat
                2 -> R.raw.audio_stegosaurus_evolution
                3 -> R.raw.audio_stegosaurus_fossil
                else -> R.raw.audio_stegosaurus_pronunciation
            }
        }

        //therizinosaurus
        11 -> {
            when(section) {
                0 -> R.raw.audio_therizinosaurus_story
                1 -> R.raw.audio_therizinosaurus_habitat
                2 -> R.raw.audio_therizinosaurus_evolution
                3 -> R.raw.audio_therizinosaurus_fossil
                else -> R.raw.audio_therizinosaurus_pronunciation
            }
        }

        //triceratops
        12 -> {
            when(section) {
                0 -> R.raw.audio_triceratops_story
                1 -> R.raw.audio_triceratops_habitat
                2 -> R.raw.audio_triceratops_evolution
                3 -> R.raw.audio_triceratops_fossil
                else -> R.raw.audio_triceratops_pronunciation
            }
        }

        //tyrannosaurusrex
        13 -> {
            when(section) {
                0 -> R.raw.audio_tyrannosaurusrex_story
                1 -> R.raw.audio_tyrannosaurusrex_habitat
                2 -> R.raw.audio_tyrannosaurusrex_evolution
                3 -> R.raw.audio_tyrannosaurusrex_fossil
                else -> R.raw.audio_tyrannosaurusrex_pronunciation
            }
        }

        //velociraptor
        else -> {
            when(section) {
                0 -> R.raw.audio_velociraptor_story
                1 -> R.raw.audio_velociraptor_habitat
                2 -> R.raw.audio_velociraptor_evolution
                3 -> R.raw.audio_velociraptor_fossil
                else -> R.raw.audio_velociraptor_pronunciation
            }
        }
    }
}
