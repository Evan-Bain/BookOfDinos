package com.example.dinoappv2.databases

import androidx.room.Dao
import androidx.room.Query
import com.example.dinoappv2.dataClasses.DinosaurEncyclopediaTable
import kotlinx.coroutines.flow.Flow

@Dao
interface DinosaurEncyclopediaDao {

    //NOTE: for release set activated = 1 by default
    /** Updates value of the boolean variable "activated" 1 = True, 0 = False **/
    @Query("UPDATE dinosaur_encyclopedia_table SET activated = :activated WHERE position = :position")
    suspend fun updateActivated(position: Int, activated: Int)

    @Query("SELECT * FROM dinosaur_encyclopedia_table WHERE position = :position")
    fun get(position: Int): Flow<DinosaurEncyclopediaTable>

    @Query("SELECT * FROM dinosaur_encyclopedia_table")
    fun getAll(): Flow<List<DinosaurEncyclopediaTable>>

    @Query("DELETE FROM dinosaur_encyclopedia_table")
    suspend fun deleteAll()
}