package com.example.dinoappv2.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.dinoappv2.dataClasses.ProfileImage

@Dao
interface ProfileImageDao {

    @Insert(onConflict = IGNORE)
    suspend fun insertFirst(image: ProfileImage)

    @Insert(onConflict = REPLACE)
    suspend fun insert(image: ProfileImage)

    @Query("SELECT profileImage FROM profile_image_table WHERE position = 0")
    suspend fun getImage(): Int

}