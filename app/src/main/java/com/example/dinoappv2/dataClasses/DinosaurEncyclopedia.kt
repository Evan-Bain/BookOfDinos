package com.example.dinoappv2.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.FieldPosition

//key on the dinosaur will be the same as the name
@Entity(tableName = "dinosaur_encyclopedia_table")
data class DinosaurEncyclopedia(
    @PrimaryKey val position: Int,
    val dinosaurKey: Int,
    val badge: Int,
    val badgeBlack: Int,
    val activated: Boolean
    )
