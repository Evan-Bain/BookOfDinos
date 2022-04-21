package com.example.dinoappv2

import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.dataClasses.getDinosaurEncyclopedia
import com.example.dinoappv2.databases.DinosaurEncyclopediaDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class BottomNavRepository(
    private val dinoDatabase: DinosaurEncyclopediaDao,
    private val ioDispatcher: CoroutineDispatcher) {

    /** returns full basic data on each dinosaur including name and images **/
    fun getDinosaurData(): Flow<List<DinosaurEncyclopedia>> {
        return dinoDatabase.getAll().getDinosaurEncyclopedia()
    }
}