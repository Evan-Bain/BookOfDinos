package com.example.dinoappv2.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_image_table")
data class ProfileImage(
    @PrimaryKey val position: Int,
    val profileImage: Int
)