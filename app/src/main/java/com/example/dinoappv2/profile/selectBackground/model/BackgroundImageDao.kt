package com.example.dinoappv2.profile.selectBackground.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface BackgroundImageDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(data: BackgroundImage)

    @Query("SELECT * FROM background_image_table WHERE position = 0")
    fun getBackground(): BackgroundImage?
}