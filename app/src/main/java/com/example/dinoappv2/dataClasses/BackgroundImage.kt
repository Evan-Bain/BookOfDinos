package com.example.dinoappv2.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "background_image_table")
/**default background (-1); land background (0); water background (1); sky background (2)**/
data class BackgroundImage(
    val backgroundImage: Int = -1,
    @PrimaryKey val position: Int = 0
)