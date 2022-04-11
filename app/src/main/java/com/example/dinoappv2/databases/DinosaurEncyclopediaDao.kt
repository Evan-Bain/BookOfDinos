package com.example.dinoappv2.databases

import androidx.room.Dao
import androidx.room.Query
import com.example.dinoappv2.dataClasses.DinosaurEncyclopediaTable

@Dao
interface DinosaurEncyclopediaDao {
    //updates value of the boolean variable "activated"
    @Query("UPDATE dinosaur_encyclopedia_table SET activated = :activated WHERE position = :position")
    suspend fun updateActivated(activated: Boolean, position: Int)

    @Query("SELECT * FROM dinosaur_encyclopedia_table WHERE position = :position")
    suspend fun get(position: Int): DinosaurEncyclopediaTable

    @Query("SELECT * FROM dinosaur_encyclopedia_table")
    suspend fun getAll(): List<DinosaurEncyclopediaTable>

    @Query("DELETE FROM dinosaur_encyclopedia_table")
    suspend fun deleteAll()
}