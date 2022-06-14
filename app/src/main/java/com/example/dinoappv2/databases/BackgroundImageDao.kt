package com.example.dinoappv2.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.dinoappv2.dataClasses.BackgroundImage
import kotlinx.coroutines.flow.Flow

@Dao
interface BackgroundImageDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(data: BackgroundImage)

    @Query("SELECT * FROM background_image_table WHERE position = 0")
    fun getBackground(): BackgroundImage?
}