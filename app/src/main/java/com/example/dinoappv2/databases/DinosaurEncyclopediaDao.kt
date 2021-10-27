package com.example.dinoappv2.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia

@Dao
interface DinosaurEncyclopediaDao {

    //if the same dinosaur is inserted it replaces the old one
    @Insert(onConflict = IGNORE)
    suspend fun insert(dinosaur: DinosaurEncyclopedia)

    //updates value of the boolean variable "activated"
    @Query("UPDATE dinosaur_encyclopedia_table SET activated = :activated WHERE position = :position")
    suspend fun updateActivated(activated: Boolean, position: Int)

    @Delete
    suspend fun delete(dinosaur: DinosaurEncyclopedia)

    @Query("SELECT * FROM dinosaur_encyclopedia_table WHERE position = :key")
    suspend fun get(key: Int): DinosaurEncyclopedia

    @Query("SELECT * FROM dinosaur_encyclopedia_table")
    suspend fun getAll(): List<DinosaurEncyclopedia>

    @Query("DELETE FROM dinosaur_encyclopedia_table")
    suspend fun deleteAll()
}