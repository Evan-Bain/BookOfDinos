package com.example.dinoappv2

import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.dataClasses.getDinosaurEncyclopedia
import com.example.dinoappv2.databases.DinosaurEncyclopediaDao
import kotlinx.coroutines.flow.Flow

class EncyclopediaRepository(
    private val dinoDatabase: DinosaurEncyclopediaDao) {

    /** returns full basic data on each dinosaur including name and images **/
    fun getDinosaurData(): Flow<List<DinosaurEncyclopedia>> {
        return dinoDatabase.getAll().getDinosaurEncyclopedia()
    }

    /** Unlocks/locks dinosaur data (1 for true; 0 for false)**/
    suspend fun updateActivated(position: Int, activated: Int) {
        dinoDatabase.updateActivated(position, activated)
    }
}