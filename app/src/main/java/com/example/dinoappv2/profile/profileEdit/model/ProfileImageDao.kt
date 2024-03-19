package com.example.dinoappv2.profile.profileEdit.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileImageDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(image: ProfileImage)

    @Query("SELECT * FROM profile_image_table WHERE position = 0")
    fun getImage(): Flow<ProfileImage?>

}