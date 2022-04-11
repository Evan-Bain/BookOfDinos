package com.example.dinoappv2

import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.dataClasses.getDinosaurEncyclopedia
import com.example.dinoappv2.databases.DinosaurEncyclopediaDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BottomNavRepository(
    private val dinoDatabase: DinosaurEncyclopediaDao,
    private val ioDispatcher: CoroutineDispatcher) {

    suspend fun getDinosaurData(): List<DinosaurEncyclopedia> {
        return withContext(ioDispatcher) {
            val dinoData = async {
                dinoDatabase.getAll()
            }
            getDinosaurEncyclopedia(dinoData.await())
        }
    }
}